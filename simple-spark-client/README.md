simple-spark-client
===============
**build the assembly jar first
**

Now export the spark_jar

	export SPARK_JAR=/home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-assembly_2.10-0.9.0-cdh5.0.0-hadoop2.3.0-cdh5.0.0.jar

Then run this command to test it 

	mvn -e -Dtest=com.codeboy.simplesparkclient.JavaYarnClientParkPiTest test

Note:you need have a local cdh5 running and 

