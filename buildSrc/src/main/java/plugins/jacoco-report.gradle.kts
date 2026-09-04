package plugins

import CoverageTaskParam
import JacocoCoverage
import Versions
import tasks.CheckCoverage

the<JacocoPluginExtension>().apply {
  toolVersion = Versions.jacoco
  reportsDirectory.set(layout.buildDirectory.dir("$buildDir/jacoco/reports"))
}

afterEvaluate {
  if (!extra.has(JacocoCoverage.coverageDataExtra)) {
    logger.quiet("${this.displayName} does not have coverage data")
    return@afterEvaluate
  }
  val (buildDirectory, taskName, coverageData, filesToInclude, filesToExclude) = extra.get(
    JacocoCoverage.coverageDataExtra
  ) as CoverageTaskParam

  task("checkCoverage", CheckCoverage::class) {
    dependsOn("jacocoAndroidTestReport")
    group = "verification"
    coverage = coverageData
    coverageFilePath = "${buildDirectory.absolutePath}/${JacocoCoverage.reportPath}"
    doFirst {
      println("Code coverage file: ${buildDirectory.absolutePath}/reports/coverage/index.html")
    }
  }

  task("jacocoAndroidTestReport", JacocoReport::class) {
    dependsOn(taskName)
    group = "verification"
    description = "Code coverage report for unit tests"
    sourceDirectories.setFrom(getSourceDirectoriesTree())
    classDirectories.setFrom(
      getClassDirectoriesTree(
        buildDirectory.absolutePath, filesToInclude,
        filesToExclude
      )
    )
    executionData.setFrom(getExecutionDataTree(buildDirectory.absolutePath, taskName))

    reports {
      xml.required.set(true)
      csv.required.set(false)
      html.required.set(true)
      html.outputLocation.set(File("${buildDirectory.absolutePath}/reports/coverage"))
    }
  }
}

fun getSourceDirectoriesTree(): List<String> {
  return listOf("src/main/java")
}

fun getExecutionDataTree(buildDir: String, executionTask: String): ConfigurableFileTree {
  return fileTree("${project.buildDir}") {
    include(
      "jacoco/$executionTask.exec",
      *JacocoCoverage.executionDataPath
    )
  }
}

fun getClassDirectoriesTree(
  buildDir: String,
  filesToInclude: List<String>,
  filesToExclude: List<String>
): ConfigurableFileTree {
  return fileTree(buildDir) {
    include(
      "**/classes/**/main/**",
      "**/tmp/kotlin-classes/debug/**"
    )
    include(filesToInclude)
    exclude(JacocoCoverage.exclusions)
    exclude(filesToExclude)
  }
}
