lazy val commonSettings = Seq(
  version := Version.benchmarks,
  scalaVersion := Version.scala,
  crossScalaVersions := Version.crossScala,
  description := "GeoTrellis benchmarking project",
  organization := "com.azavea.geotrellis",
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-Yinline-warnings",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:higherKinds",
    "-language:postfixOps",
    "-language:existentials",
    "-feature"),

  resolvers += Resolver.bintrayRepo("azavea", "maven"),
  libraryDependencies += "com.azavea" %% "scaliper" % "0.5.0-e9d4266",

  shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
) ++ net.virtualvoid.sbt.graph.Plugin.graphSettings

lazy val root = Project("root", file(".")).
  dependsOn(geotrellisRaster, geotrellisVector, geotrellis09)

lazy val geotrellisRaster = Project("geotrellis-raster-benchmark", file("geotrellis/raster")).
  settings(commonSettings: _*)

lazy val geotrellisVector = Project("geotrellis-vector-benchmark", file("geotrellis/vector")).
  settings(commonSettings: _*)

lazy val geotrellis09 = Project("geotrellis09-benchmark", file("geotrellis09")).
  settings(commonSettings: _*)
