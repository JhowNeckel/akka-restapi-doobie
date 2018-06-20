name := "akka-restapi-doobie"
version := "1.0"

scalaVersion := "2.12.6"
scalacOptions := Seq(
  "-Ypartial-unification",
  "-unchecked",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val akkaVersion = "2.5.13"
lazy val akkaHttpVersion = "10.1.3"
lazy val doobieVersion = "0.5.3"
lazy val Json4sVersion = "3.2.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "org.json4s" %% "json4s-native" % Json4sVersion,
  "org.json4s" %% "json4s-ext" % Json4sVersion
)