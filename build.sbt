import Dependencies._
import sbt.Keys.libraryDependencies

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.ermilov",
      scalaVersion := "2.11.12",
      version := "1.0.0-SNAPSHOT"
    )),
    name := "SparkWriteApplication",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += spark,
    // https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
    libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.1.0" % "provided",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.2",
    libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.3.3",
      libraryDependencies += ("org.apache.kafka" % "kafka-streams" % "0.10.2.2").excludeAll(
      ExclusionRule(organization = "com.sun.jmx"),
      ExclusionRule(organization = "com.sun.jdmk"),
      ExclusionRule(organization = "javax.jms")
    ),
    mainClass in assembly := Some("org.ermilov.SparkWriteApplication"),
    test in assembly := {}
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}