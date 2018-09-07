
name := "spark_scala_example"

version := "1.0"

scalaVersion := "2.11.7"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

val akkaVersion = "2.5.3"

mainClass in(Compile, packageBin) := Some("com.netpod.application.SalesWriter")

//resolvers += "spray repo" at "http://repo.spray.io"
//
//resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"

//resolvers += Resolver.mavenLocal

libraryDependencies += "io.spray" %%  "spray-can" % "1.3.3"

libraryDependencies += "io.spray" %%  "spray-httpx" % "1.3.3"

libraryDependencies += "io.spray" %%  "spray-http" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-client" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-routing" % "1.3.3"

libraryDependencies += "io.spray" %% "spray-testkit" % "1.3.3"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.3"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % akkaVersion

libraryDependencies += "com.h2database" % "h2" % "1.4.187"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test"

libraryDependencies += "org.threeten" % "threetenbp" % "1.2"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.2.0"
//excludeAll(ExclusionRule(organization = "javax.servlet"), ExclusionRule(organization = "com.sun.jersey"))

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"

//libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.11" % "1.4.0-M3"

libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.11" % "2.0.2"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.0" excludeAll(ExclusionRule(organization = "com.sun.jersey"), ExclusionRule(organization = "com.fasterxml.jackson.core"), ExclusionRule(organization = "org.apache.hadoop", artifact = "hadoop-client"))
//excludeAll(ExclusionRule(organization = "javax.servlet"), ExclusionRule(organization = "com.sun.jersey"))

// org.glassfish.jersey.containers:jersey-container-servlet-core

// https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
//libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"

libraryDependencies += "org.glassfish.jersey.core" % "jersey-client" % "2.25.1"

libraryDependencies += "org.glassfish.jersey.core" % "jersey-server" % "2.25.1"

libraryDependencies += "org.glassfish.jersey.core" % "jersey-common" % "2.25.1"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.1"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.1"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.9.1"

libraryDependencies += "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-base" % "2.9.1"

libraryDependencies += "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-json-provider" % "2.9.1"

libraryDependencies += "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-providers" % "2.9.1" pomOnly()

libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.9.0"

// https://mvnrepository.com/artifact/org.glassfish/javax.servlet
//libraryDependencies += "org.glassfish" % "javax.servlet" % "3.1"

// https://mvnrepository.com/artifact/org.glassfish.jersey.containers/jersey-container-servlet
//libraryDependencies += "org.glassfish.jersey.containers" % "jersey-container-servlet" % "2.25.1"

// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.0.0-alpha4" excludeAll(ExclusionRule(organization = "com.sun.jersey"), ExclusionRule(organization = "com.fasterxml.jackson.core"))

//excludeAll(ExclusionRule(organization = "javax.servlet"), ExclusionRule(organization = "com.sun.jersey"))

libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % "3.0.0-alpha4" excludeAll(ExclusionRule(organization = "com.sun.jersey"), ExclusionRule(organization = "com.fasterxml.jackson.core"))
//excludeAll(ExclusionRule(organization = "javax.servlet"), ExclusionRule(organization = "com.sun.jersey"))

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.0.0-alpha4" excludeAll(ExclusionRule(organization = "com.sun.jersey"), ExclusionRule(organization = "com.fasterxml.jackson.core"))

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.16"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

//libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.1"

//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.0"

libraryDependencies += "com.typesafe.slick" % "slick-testkit_2.11" % "3.2.0"

libraryDependencies += "commons-dbcp" % "commons-dbcp" % "1.4"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.37"

// https://mvnrepository.com/artifact/io.snappydata/snappydata-cluster_2.11
libraryDependencies += "io.snappydata" % "snappydata-cluster_2.11" % "1.0.0"
