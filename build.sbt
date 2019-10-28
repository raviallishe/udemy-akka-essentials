name := "udemy-akka-essentials"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
  "org.scala-lang" % "scala-library" % "2.13.0-M4-pre-20d3c21",
  "com.typesafe.akka" %% "akka-actor" % "2.5.26",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.26" % Test,
  "org.scalactic" %% "scalactic" % "3.0.8"
)