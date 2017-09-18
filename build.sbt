

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "tomliddle",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % Test
  )
