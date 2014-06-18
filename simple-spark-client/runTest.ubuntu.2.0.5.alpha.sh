export SPARK_JAR=./assembly/target/scala-2.10/spark-assembly-0.9.2-SNAPSHOT-hadoop2.0.5-alpha.jar
 
export spark_example_jar=./examples/target/scala-2.10/spark-examples-assembly-0.9.2-SNAPSHOT.jar 
 
mvn -e -Dtest=com.codeboy.simplesparkclient.JavaYarnClientSparkPiTest test
