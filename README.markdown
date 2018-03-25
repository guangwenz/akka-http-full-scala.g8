## Build Reactive Web Services with Akka!

# Introduction

Build reactive web service on top of Akka HTTP, Akka Cluster, Akka Persistence and Slick.
please see the [Akka document](https://akka.io/docs/) for a quick reference.
can be easily deployed to containerized environment like kuberetes

Prerequisites:
- JDK 8
- sbt 0.13.13 or higher

Open a console and run the following command to apply this template:
```
sbt new https://github.com/zgwmike/akka-http-full-scala.g8
```

This template will prompt for the following parameters. Press `Enter` if the default values suit you:
- `name`: Becomes the name of the project.
- `package`: Becomes the base package for the project
- `actorSystemName`: Becomes the default name of the actor
- `akkaManagementVersion`: Specifies the akka management version
- `slickProfile`: the slick profile to use, check [Slick document](http://slick.lightbend.com/doc/3.2.1/supported-databases.html) for supported databases.
- `scala_version`: Specifies the Scala version for this project.
- `akka_http_version`: Specifies which version of Akka HTTP should be used for this project.
- `akka_version`: Specifies which version of Akka should be used for this project.
- `organization`: Specifies the organization for this project.

The template comes with the following package layout:
* `application` -- application tier reusable components by actors.
* `common` -- domain common code, like enums, domain level shared components.
* `dao` -- database access table queries, entities and repositories, I created readonly repository and transaction repository, it's good practice to separate reading and writing operations for database access.
* `utils` -- some util classes.
* `Boot.scala` -- Main class for the system
* `Routes.scala` -- routes for the system, add your domain routes here.
* `AkkaConfig.scala` -- akka config utility object to initialize akka configs.

Once inside the project folder, configure the slick connections to use your databases inside `src/main/resources/akka-datastore.conf`, please refer to [HikariCP](http://brettwooldridge.github.io/HikariCP/) for the db connection and pool settings for details, the default is using MySQL

after setup datbase, use the following command to run the code:
```
sbt run
```

Template license
----------------
Written in 2018 by Guangwen Zhou <zgwmike@hotmail.com>

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
