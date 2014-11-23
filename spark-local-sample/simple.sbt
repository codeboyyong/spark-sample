name := "spark-local-sample"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
								"org.apache.spark" %% "spark-core" % "1.1.0",
            					"org.scalanlp" %% "breeze" % "0.8.1" ,
            					"org.scalanlp" %% "breeze-natives" % "0.8.1"
            					) 
            					
 resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
