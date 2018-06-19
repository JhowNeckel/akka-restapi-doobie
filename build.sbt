name := "akka-restapi-doobie"
version := "1.0"
//scalaVersion := "2.12.6"
scalaVersion := "2.11.8"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

lazy val akkaVersion = "2.4.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
  "com.github.etaty" %% "rediscala" % "1.7.0",
  "org.scalatest"     %% "scalatest" % "2.2.6" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.4.2" % "test"
)