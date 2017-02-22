name := "Xmpanzee"

version := "0.0.1"

scalaVersion := "2.12.1"

mainClass in (Compile, run) := Some("Xmpanzee")

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.scalikejdbc" %% "scalikejdbc" % "2.5.0",
  "org.postgresql" % "postgresql" % "42.0.0",
  "ch.qos.logback" %  "logback-classic" % "1.1.7"
)
