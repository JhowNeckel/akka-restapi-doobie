name := "akka-restapi-doobie"
version := "1.0"
scalaVersion := "2.12.6"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

lazy val akkaVersion = "2.5.13"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http"   % "10.1.3",
  "com.github.etaty" %% "rediscala" % "1.7.0",
  "org.scalatest"     %% "scalatest" % "2.2.6" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.4.2" % "test"
)