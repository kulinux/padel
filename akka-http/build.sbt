lazy val akkaHttpVersion = "10.1.1"
lazy val akkaVersion    = "2.5.12"


PB.protoSources in Compile := Seq(new java.io.File("../protobuffers"))
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.padel.http",
      scalaVersion    := "2.12.5"
    )),
    name := "akka-http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
      "com.sandinh" %% "couchbase-scala" % "7.4.1",

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.1"         % Test
    )
  )

