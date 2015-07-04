name := """play-java-intro"""

version := "1.0-SNAPSHOT"

//for mongodb
resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/"


lazy val root = (project in file(".")).enablePlugins(PlayJava)



libraryDependencies ++= Seq(
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final",
  
  //http://mongodb.github.io/mongo-java-driver/2.13/getting-started/quick-tour/
  "org.mongodb" % "mongo-java-driver" % "2.13.2",
  
  //http://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
  "com.google.code.gson" % "gson" % "2.3.1"
)     

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true