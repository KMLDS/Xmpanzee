name := "Xmpanzee"

version := "0.0.1"

scalaVersion := "2.12.1"

mainClass in (Compile, run) := Some("Xmpanzee")

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
