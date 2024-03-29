import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val artifactId = "pgr2dbc"
    const val groupId = "com.github.daggerok"
    const val version = "1.0-SNAPSHOT"
  }

  val javaVersion = JavaVersion.VERSION_1_8
  const val vavrVersion = "0.10.0"
  const val kotlinVersion = "1.3.40"
  const val lombokVersion = "1.18.8"
  const val jacksonVersion = "2.9.9"
  const val junitVersion = "4.13-beta-3"
  const val junitJupiterVersion = "5.5.0-RC2"
  const val r2dbcPostgresVersion = "1.0.0.M7"
  const val r2dbcSpringVersion = "0.1.0.M1"
  const val springBootVersion = "2.2.0.M3" // "2.2.0.M4" is not compatible with r2dbc
  const val postgresVersion = "42.2.6"

  object Gradle {
    const val wrapperVersion = "5.5-rc-4"

    object Plugin {
      const val lombokVersion = "3.1.0"
      const val versionsVersion = "0.21.0"
      const val flywayVersion = "6.0.0-beta2"
      const val dependencyManagementVersion = "1.0.8.RELEASE"
    }
  }
}
