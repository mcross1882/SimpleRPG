import AssemblyKeys._

name := "SimpleRPG"

version := "0.0.1"

scalaVersion := "2.11.6"

jarName in assembly := "simplerpg.jar"

mainClass in assembly := Some("simplerpg.Application")

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "com.owlike"          %   "genson"        % "1.3",
    "com.owlike"          %   "genson-scala_2.11" % "1.3"
  )
}

Revolver.settings
