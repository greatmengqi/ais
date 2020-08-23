name := "ais"

version := "0.1"

scalaVersion := "2.11.12"


resolvers ++= Seq(
  //阿里云
  "aiPublic" at "https://maven.aliyun.com/repository/public",
  "CHD" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  //repo2
  "repo2" at "https://repo2.maven.org/maven2/",
  "repo1" at "https://repo1.maven.org/maven2/",
  //        typesafe
  "typesafe" at "https://repo.typesafe.com/typesafe/ivy-releases/",
  //        Apache SnapShot
  "Apache SnapShot" at "https://repository.apache.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.4.2",
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "ch.qos.logback" % "logback-access" % "1.2.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.1.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",

  /**
   * 1.排除jara包
   * 2.升级 javax.servlet" % "javax.servlet-api" % "3.0.1"
   */
).map(
  _.excludeAll(
    ExclusionRule("org.eclipse.jetty.orbit", "javax.servlet"),
    ExclusionRule("org.mortbay.jetty", "jsp-2.1"),
    ExclusionRule("org.mortbay.jetty", "jsp-api-2.1"),
    ExclusionRule("org.mortbay.jetty", "servlet-api-2.5"),
    ExclusionRule("javax.servlet", "jsp-api"),
    ExclusionRule("javax.servlet.jsp", "jsp-api"),
    ExclusionRule("javax.servlet", "servlet-api")
  )
)

assembly / test := {}
assembly / assemblyMergeStrategy := {
  case PathList("javax", "xml", xs @ _*) => MergeStrategy.last
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "el", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last

  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps @ _*)
    if ((ps.last startsWith "route_") && (ps.last endsWith ".csv")) || (ps.last endsWith ".xls") =>
    MergeStrategy.discard
  case PathList(ps @ _*) if ps.last endsWith "io.netty.versions.properties" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith ".so" => MergeStrategy.first

  case "about.html" => MergeStrategy.rename
  case "META-INF/native/libnetty-transport-native-epoll.so" => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "mapred-default.xml" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case "VariableMapper.class" => MergeStrategy.last
  case "git.properties" => MergeStrategy.discard
  case "scala-parser-combinators.properties" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
