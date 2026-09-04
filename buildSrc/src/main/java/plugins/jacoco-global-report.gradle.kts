package plugins

import CoverageTaskParam
import JacocoCoverage
import Versions

apply(plugin = "jacoco")

the<JacocoPluginExtension>().apply {
  toolVersion = Versions.jacoco
  reportsDirectory.set(layout.buildDirectory.dir("$buildDir/jacoco/reports"))
}

task("aggregateCoverage", JacocoReport::class) {
  evaluationDependsOnChildren()

  group = "Reporting"
  description = "Generate aggregated Jacoco coverage report"

  reports {
    xml.required.set(true)
    csv.required.set(false)
    html.required.set(true)
    html.outputLocation.set(File("${project.buildDir.absolutePath}/reports/coverage"))
  }

  val kotlinClasses = subprojects.filter {
    it.extra.has(JacocoCoverage.coverageDataExtra)
  }
    .map { project ->
      JacocoCoverage.classDirectories.map { path ->
        val (_, _, _, filesToInclude, filesToExclude) = project.extra.get(JacocoCoverage.coverageDataExtra) as CoverageTaskParam

        fileTree(project.buildDir.absolutePath) {
          include(path)
          include(filesToInclude)
          exclude(JacocoCoverage.exclusions)
          exclude(filesToExclude)
        }
      }
    }.flatten()
  classDirectories.setFrom(kotlinClasses)

  val sources = subprojects.filter { it.extra.has(JacocoCoverage.coverageDataExtra) }
    .map { project ->
      JacocoCoverage.sourceDirectories.map { "${project.projectDir}/$it" }
    }.flatten()
  sourceDirectories.setFrom(files(sources))

  val executions = subprojects.filter { it.extra.has(JacocoCoverage.coverageDataExtra) }
    .map { project ->
      fileTree("${project.buildDir}") {
        include(
          *JacocoCoverage.executionDataPath
        )
      }
    }
  executionData.setFrom(executions)
}
