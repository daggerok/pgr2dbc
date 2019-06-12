# pgr2dbc [![Build Status](https://travis-ci.org/daggerok/pgr2dbc.svg?branch=master)](https://travis-ci.org/daggerok/pgr2dbc)
R2DBC Postgres

## getting started

```bash
./gradlew up
./gradlew ps

./gradlew flywayMigrate
#./gradlew flywayClean

./gradlew rm
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_

<!--
_update versions_

```bash
./mvnw versions:display-property-updates
./gradlew dependencyUpdates -Drevision=release
```
-->

_resources_

* [DZone: Postgres R2DBC](https://dzone.com/articles/introduction-to-reactive-apis-with-postgres-r2dbc)
* [Migrate using Gradle flyway plugin](https://flywaydb.org/documentation/gradle/migrate#errorOverrides)
* [Gradle flyway plugin usage](https://flywaydb.org/documentation/gradle/)
* [Flyway postgres](https://flywaydb.org/documentation/database/postgresql)
