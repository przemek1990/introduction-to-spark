import sbt.Keys.version

lazy val commonSettings = Seq(
  name := "introduction-to-spark",
  version := "1.0",
  scalaVersion := "2.11.7",
  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "2.2.0",
    "org.apache.spark" %% "spark-sql" % "2.2.0",
    "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.3",
    "org.scalatest" %% "scalatest" % "2.2.6" % Test
  ),
  assemblyJarName in assembly := "introduction-to-spark.jar",
  assemblyMergeStrategy in assembly := {
    case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.discard
    case PathList("com", xs@_*) => MergeStrategy.first
    case PathList("scala", xs@_*) => MergeStrategy.first
    case PathList("javax", xs@_*) => MergeStrategy.first
    case PathList("org", xs@_*) => MergeStrategy.first
    case PathList("net", xs@_*) => MergeStrategy.first
    case PathList("joptsimple", xs@_*) => MergeStrategy.first
    case "log4j.properties" => MergeStrategy.first
    case "mime.types" => MergeStrategy.first
    case "application.conf" => MergeStrategy.concat
    case "plugin.properties" => MergeStrategy.concat
    case "unwanted.txt" => MergeStrategy.discard
    case PathList("META-INF", xs@_*) => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

val `introduction-to-spark` = project
  .in(file("."))
  .settings(commonSettings: _*)
