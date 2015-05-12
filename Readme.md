To install Cortical IO java client :
    1. Clone the Git code -
        $ git clone https://github.com/cortical-io/java-client-sdk
    2. Build Java client and models.
        $ cd retina-service-java-api-client
        $ mvn clean install

        $ cd retina-service-rest-model/
        $ mvn clean install
    3. Copy the created jar in target folder in project library.


Add Scala Stanford CoreNLP Dependency in build.sbt file
    libraryDependencies ++= Seq(
      "edu.arizona.sista" %% "processors" % "5.2",
      "edu.arizona.sista" %% "processors" % "5.2" classifier "models"
    )