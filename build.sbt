

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "tomliddle",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
    libraryDependencies += "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.7.2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % Test

  )
