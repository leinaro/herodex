import java.io.File

object JacocoCoverage {
  val exclusions = listOf<String>()
  val executionDataPath = arrayOf(
    "**/jacoco/*.exec",
  )
  val classDirectories = arrayOf(
    "**/classes/**/main/**",
    "**/tmp/kotlin-classes/debug/**",
  )
  val sourceDirectories = arrayOf("src/main/java")
  const val reportPath = "jacoco/reports/jacocoAndroidTestReport/jacocoAndroidTestReport.xml"
  const val coverageDataExtra = "coverageData"
}

data class Coverage(
  val instructions: Double = 0.0,
  val lines: Double = 0.0,
  val methods: Double = 0.0,
  val classes: Double = 0.0
)

data class CoverageTaskParam(
  val buildDirectory: File,
  val task: String = "testDebugUnitTest",
  val coverageData: Coverage = Coverage(
    instructions = 80.0,
    lines = 80.0,
    methods = 80.0,
    classes = 80.0
  ),
  val filesToInclude: List<String> = emptyList(),
  val filesToExclude: List<String> = emptyList(),
)