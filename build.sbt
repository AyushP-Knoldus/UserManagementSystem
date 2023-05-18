ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "UserManagementSystem"
  )
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.15" % "test"
val mockito = "org.mockito" % "mockito-scala-scalatest_2.13" % "1.17.12" % Test
val sqlConnector = "mysql" % "mysql-connector-java" % "8.0.32"
val typeSafe = "com.typesafe" % "config" % "1.4.2"
val logger = "org.slf4j" % "slf4j-simple" % "2.0.5"

libraryDependencies ++= Seq(scalaTest,mockito,sqlConnector,typeSafe,logger)


coverageExcludedPackages := "<empty>;App\\..*"

