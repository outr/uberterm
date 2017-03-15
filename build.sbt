name := "uberterm"
organization in ThisBuild := "com.outr"
version in ThisBuild := "1.0.0"
scalaVersion in ThisBuild := "2.12.1"
crossScalaVersions in ThisBuild := List("2.12.1", "2.11.8")
sbtVersion in ThisBuild := "0.13.13"
resolvers in ThisBuild ++= Seq(
  Resolver.typesafeRepo("releases"),
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)
scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

val youiVersion = "0.2.3-SNAPSHOT"

lazy val root = project.in(file("."))
  .aggregate(apiJVM, apiJS, terminalJVM, terminalJS)
  .settings(
    publishArtifact := false
  )

lazy val api = crossProject.in(file("api"))
  .settings(
    name := "uberterm-api",
    libraryDependencies ++= Seq(
      "io.youi" %%% "youi-app" % youiVersion
    )
  )

lazy val apiJVM = api.jvm
lazy val apiJS = api.js

lazy val terminal = crossProject.in(file("terminal"))
  .settings(
    name := "uberterm"
  )
  .jsSettings(
    crossTarget in fastOptJS := baseDirectory.value / ".." / "jvm" / "src" / "main" / "resources" / "app",
    crossTarget in fullOptJS := baseDirectory.value / ".." / "jvm" / "src" / "main" / "resources" / "app"
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "io.youi" %% "youi-server-undertow" % youiVersion
    )
  )
  .dependsOn(api)

lazy val terminalJVM = terminal.jvm
lazy val terminalJS = terminal.js
