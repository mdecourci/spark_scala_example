
name := "spark_scala_example"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"

resolvers += Resolver.mavenLocal

libraryDependencies += "io.spray" %%  "spray-can" % "1.3.3"

libraryDependencies += "io.spray" %%  "spray-httpx" % "1.3.3"

libraryDependencies += "io.spray" %%  "spray-http" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-client" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-routing" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-testkit" % "1.3.3"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.2"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.9"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.9"

libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.3.9"

libraryDependencies += "com.h2database" % "h2" % "1.4.187"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % "test"

libraryDependencies += "org.threeten" % "threetenbp" % "1.2"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.2.1"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"

libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.4.0-M3"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.4.1"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.10"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.1.0"

libraryDependencies += "com.typesafe.slick" % "slick-testkit_2.11" % "3.1.0"

libraryDependencies += "commons-dbcp" % "commons-dbcp" % "1.4"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.37"
