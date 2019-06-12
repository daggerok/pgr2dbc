import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  dependencies {
    classpath("org.postgresql:postgresql:${Globals.postgresVersion}")
  }
}

plugins {
  java
  kotlin("jvm") version Globals.kotlinVersion
  kotlin("plugin.spring") version Globals.kotlinVersion
  id("org.springframework.boot") version Globals.springBootVersion
  id("org.flywaydb.flyway") version Globals.Gradle.Plugin.flywayVersion
  id("io.franzbecker.gradle-lombok") version Globals.Gradle.Plugin.lombokVersion
  id("io.spring.dependency-management") version Globals.Gradle.Plugin.dependencyManagementVersion
  id("com.github.ben-manes.versions") version Globals.Gradle.Plugin.versionsVersion
  // gradle dependencyUpdates -Drevision=release
}

group = Globals.Project.groupId
version = Globals.Project.version

extra["junit.version"] = Globals.junitVersion
extra["lombok.version"] = Globals.lombokVersion
extra["junit-jupiter.version"] = Globals.junitJupiterVersion

extra["flyway.user"] = "postgres"
extra["flyway.password"] = "postgres"
extra["flyway.url"] = "jdbc:postgresql://127.0.0.1:5432/postgres"
//extra["flyway.configFiles"] = "${rootProject.projectDir.absolutePath}/src/main/resources/db/migration"
//extra["flyway.schemas"] = "schema1,schema2,schema3,public"
//extra["flyway.placeholders.otherplaceholder"] = "value123"
//extra["flyway.placeholders.keyABC"] = "valueXYZ"

flyway {
//  locations = arrayOf(
//      ""
//  )
}

java {
  sourceCompatibility = Globals.javaVersion
  targetCompatibility = Globals.javaVersion
}

repositories {
  mavenCentral()
  // maven { url = uri("https://repo.spring.io/snapshot/") }
  maven { url = uri("https://repo.spring.io/milestone/") }
}

lombok {
  version = Globals.lombokVersion
}

sourceSets {
  main {
    java.srcDir("src/main/kotlin")
  }
  test {
    java.srcDir("src/test/kotlin")
  }
}

val flywayMigration = configurations.create("flywayMigration")

dependencies {
//  flywayMigration("org.postgresql:postgresql:${Globals.postgresVersion}")

  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect"))
  testImplementation(kotlin("test-junit"))
  testImplementation(kotlin("test-junit5"))

  implementation(platform("org.springframework.boot:spring-boot-dependencies:${Globals.springBootVersion}"))
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")

  implementation(platform("org.springframework.boot.experimental:spring-boot-dependencies-r2dbc:${Globals.r2dbcSpringVersion}"))
  implementation("org.springframework.boot.experimental:spring-boot-starter-data-r2dbc")
  implementation("io.r2dbc:r2dbc-postgresql:${Globals.r2dbcPostgresVersion}")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("io.vavr:vavr:${Globals.vavrVersion}")
  annotationProcessor("org.projectlombok:lombok")

  testImplementation(platform("org.junit:junit-bom:${Globals.junitJupiterVersion}"))
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
  withType(Wrapper::class.java) {
    gradleVersion = Globals.Gradle.wrapperVersion
    distributionType = Wrapper.DistributionType.BIN
  }

  withType<KotlinCompile>().configureEach {
    kotlinOptions {
      freeCompilerArgs += "-Xjsr305=strict"
      jvmTarget = "${Globals.javaVersion}"
    }
  }

  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showExceptions = true
      showStandardStreams = true
      events(PASSED, SKIPPED, FAILED)
    }
  }

  create<Zip>("sources") {
    dependsOn("clean")
    shouldRunAfter("clean", "assemble")
    description = "Archives sources in a zip file"
    group = "Archive"
    from("buildSrc") {
      into("buildSrc")
    }
    from("src") {
      into("src")
    }
    from(".gitignore")
    from(".java-version")
    from(".travis.yml")
    from("build.gradle.kts")
    from("pom.xml")
    from("README.md")
    from("settings.gradle.kts")
    archiveFileName.set("${project.buildDir}/sources-${project.version}.zip")
  }

  named("clean") {
    doLast {
      delete(
          project.buildDir,
          "${project.projectDir}/out"
      )
    }
  }

  register<Exec>("up") {
    commandLine("docker", "run", "--rm", "-d", "--name", "pg", "-p", "5432:5432", "postgres:alpine")
  }

  register<Exec>("ps") {
    commandLine("docker", "ps")
  }

  register<Exec>("rm") {
    commandLine("docker", "rm", "-f", "-v", "pg")
  }
}

defaultTasks("clean", "sources", "build")
