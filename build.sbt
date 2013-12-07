name := "Peopoly"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

val appDependencies = Seq(
  javaJdbc,
  javaEbean
)

play.Project.playJavaSettings
