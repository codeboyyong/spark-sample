
export SPARK_JAR=./assembly/target/scala-2.10/spark-assembly-0.9.2-SNAPSHOT-hadoop2.3.0-cdh5.0.0.jar
export SPARK_EXAMPLE_JAR=./examples/target/scala-2.10/spark-examples-assembly-0.9.2-SNAPSHOT.jar 
javac -classpath ${SPARK_JAR} SparkPiTest.java
java -cp ${SPARK_JAR}:.: SparkPiTest
