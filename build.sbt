import sbt.Keys.version

lazy val commonSettings = Seq(
  name := "introduction-to-spark",
  version := "1.0",
  scalaVersion := "2.11.7",
  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "2.2.0",
    "org.apache.spark" %% "spark-sql" % "2.2.0"
  )
)

val `introduction-to-spark` = project
  .in(file("."))
  .settings(commonSettings: _*)
