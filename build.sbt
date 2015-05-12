name := "PhraseDetection"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "edu.arizona.sista" %% "processors" % "5.2",
  "edu.arizona.sista" %% "processors" % "5.2" classifier "models",
  "org.apache.lucene" % "lucene-analyzers" % "3.6.2"
)
    