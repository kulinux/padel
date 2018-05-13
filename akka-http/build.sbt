lazy val akkaHttpVersion = "10.1.1"
lazy val akkaVersion    = "2.5.2"


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
    resolvers ++= Seq("reactivecouchbase-rs-snapshots" at "https://raw.github.com/ReactiveCouchbase/reactivecouchbase-rs-core/master/repository/snapshots",
      "reactivecouchbase-rs-releases" at "https://raw.github.com/ReactiveCouchbase/reactivecouchbase-rs-core/master/repository/releases"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
      "org.reactivecouchbase" %% "reactivecouchbase-rs-core" % "1.0.0",
      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.1"         % Test
    )
  )

