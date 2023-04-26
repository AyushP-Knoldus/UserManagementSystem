ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "UserManagementSystem"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"

libraryDependencies += "org.mockito" % "mockito-scala-scalatest_2.13" % "1.17.12" % Test
