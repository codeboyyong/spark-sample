package com.codeboy.simplesparkclient;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.junit.Test;

 
//in the env :SPARK_JAR= /home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-assembly_2.10-0.9.0-cdh5.0.0-hadoop2.3.0-cdh5.0.0.jar

public class JavaYarnClientLoRTest {
	 
	String lorJarFile ="/home/john/dev/adl_master/AlpineSparkAnalytics/target/alpinesparkanalytics-15ce215a0.jar"; 
	String yarnLogDir = "/cdh5/data/1/yarn/logs";
	 
	@Test
	public void testSparkPISync(){
	JavaYarnClient client  = new JavaYarnClient();

	/*
	 * //hadoopConfig.set("hadoop.security.authentication=simple,
	 * //hadoopConfig.
	 * set("dfs.namenode.name.dir=file://${hadoop.tmp.dir}/dfs/name,
	 * //hadoopConfig.set("mapreduce.job.hdfs-servers=hdfs://localhost:9000,
	 * 
	 * private static void runAlpineLoR() {
	 * 
	 * // Set an env variable indicating we are running in YARN mode. // Note:
	 * anything env variable with SPARK_ prefix gets propagated to all (remote)
	 * processes - // see Client#setupLaunchEnv().
	 * System.setProperty("SPARK_YARN_MODE", "true");
	 * 
	 * SparkConf sparkConf = new SparkConf(); Configuration hadoopConfig = new
	 * Configuration();
	 * hadoopConfig.set("yarn.resourcemanager.scheduler.address"
	 * ,"localhost:8030"); hadoopConfig.set("mapreduce.framework.name","yarn");
	 * 
	 * hadoopConfig.set("yarn.resourcemanager.address","localhost:8032");
	 * 
	 * hadoopConfig.set("fs.default.name","hdfs://localhost:9000");
	 * hadoopConfig.set("yarn.application.classpath",
	 * "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$USS_HOME/*,$USS_CONF,$HADOOP_HOME/lib/hadoop/client/*, $HADOOP_HOME/lib/hadoop-hdfs/lib/*, $HADOOP_HOME/lib/hadoop-mapreduce/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,  $HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"
	 * );
	 * 
	 * String [] argStrings = new String[]{ "--jar",
	 * "/home/john/dev/adl_master/AlpineSparkAnalytics/target/alpinesparkanalytics-15ce215a0.jar"
	 * , "--addJars", //"--files",
	 * "file:///home/john/.ivy2/cache/com.google.code.gson/gson/jars/gson-1.7.1.jar,"
	 * +
	 * "file:///home/john/dev/adl_master/AlpineMinerSDK/target/alpineminersdk-15ce215a0.jar,"
	 * +
	 * "file:///home/john/dev/adl_master/AlpineDataAnalysis/target/alpinedataanalysis-15ce215a0.jar,"
	 * +
	 * "file:///home/john/dev/adl_master/AlpineHadoopAnalytics/target/alpinehadoopanalytics-15ce215a0.jar"
	 * , "--class",
	 * "com.alpine.spark.algorithm.classification.logisticregression.SparkLogisticRegressionTrainJob"
	 * , "--args",//this is db's gson string for the build...
	 * "eyJhbHBpbmUuZGVsaW1pdGVyIjoiLCIsImFscGluZS5sYW1iZGEiOiIwIiwiYWxwaW5lLmRlcGVuZGVudENvbHVtbk5hbWUiOiJwbGF5Iiwic3BhcmsuZGVwbG95TW9kZSI6Inlhcm4tc3RhbmRhbG9uZSIsInNwYXJrLndvcmtlckNvcmVzIjoiMSIsImFscGluZS5zZWxlY3RlZENvbHVtbk5hbWVMaXN0IjoiW1widGVtcGVyYXR1cmVcIixcImh1bWlkaXR5XCJdIiwiYWxwaW5lLm9wdGltaXplciI6Ik5ld3RvbiIsImFscGluZS5pbnB1dEZpbGUiOiJoZGZzOi8vbG9jYWxob3N0OjkwMDAvY3N2L2dvbGZuZXcuY3N2Iiwic3BhcmsuYWRkSmFycyI6Ii9ob21lL2pvaG4vLml2eTIvY2FjaGUvY29tLmdvb2dsZS5jb2RlLmdzb24vZ3Nvbi9qYXJzL2dzb24tMS43LjEuamFyLC9ob21lL2pvaG4vZGV2L2FkbF9tYXN0ZXIvQWxwaW5lTWluZXJTREsvdGFyZ2V0L2FscGluZW1pbmVyc2RrLTE1Y2UyMTVhMC5qYXIsL2hvbWUvam9obi9kZXYvYWRsX21hc3Rlci9BbHBpbmVEYXRhQW5hbHlzaXMvdGFyZ2V0L2FscGluZWRhdGFhbmFseXNpcy0xNWNlMjE1YTAuamFyLC9ob21lL2pvaG4vZGV2L2FkbF9tYXN0ZXIvQWxwaW5lSGFkb29wQW5hbHl0aWNzL3RhcmdldC9hbHBpbmVoYWRvb3BhbmFseXRpY3MtMTVjZTIxNWEwLmphciIsInNwYXJrLmphciI6Ii9ob21lL2pvaG4vZGV2L2FkbF9tYXN0ZXIvQWxwaW5lU3BhcmtBbmFseXRpY3MvdGFyZ2V0L2FscGluZXNwYXJrYW5hbHl0aWNzLTE1Y2UyMTVhMC5qYXIiLCJzcGFyay5udW1Xb3JrZXJzIjoiMSIsImFscGluZS5jb2x1bW5OYW1lTGlzdCI6IltcIm91dGxvb2tcIixcInRlbXBlcmF0dXJlXCIsXCJodW1pZGl0eVwiLFwid2luZFwiLFwicGxheVwiXSIsImFscGluZS5jb2x1bW5UeXBlTWFwIjoie1wicGxheVwiOlwiY2hhcmFycmF5XCIsXCJ3aW5kXCI6XCJjaGFyYXJyYXlcIixcImh1bWlkaXR5XCI6XCJsb25nXCIsXCJvdXRsb29rXCI6XCJjaGFyYXJyYXlcIixcInRlbXBlcmF0dXJlXCI6XCJsb25nXCJ9Iiwic3BhcmsubWFpbkNsYXNzIjoiY29tLmFscGluZS5zcGFyay5hbGdvcml0aG0uY2xhc3NpZmljYXRpb24ubG9naXN0aWNyZWdyZXNzaW9uLlNwYXJrTG9naXN0aWNSZWdyZXNzaW9uVHJhaW5Kb2IiLCJzcGFyay53b3JrZXJNZW1vcnkiOiI1MTJtIiwiYWxwaW5lLm1vZGVsT3V0cHV0IjoiL3RtcC9hbHBpbmVfb3V0L2Nob3J1c2FkbWluLzAwX3NwYXJrX2NkaDUvODQxZTlhOGYtOTNiMC00YzA2LWJmMmUtYjcxZmM3Zjc4YTMwLTEzOTg5NjYwMjA4MzkvTUxPUk1vZGVsLmpzb24iLCJzcGFyay5tYXN0ZXJNZW1vcnkiOiIxZyIsImFscGluZS5lcHNpbG9uIjoiMC4wMDAxIiwiYWxwaW5lLmludGVyYWN0aW9uUGFyYW1ldGVyTGlzdCI6IltdIiwiYWxwaW5lLm1heEl0ZXIiOiIxIiwic3BhcmsuYXBwTmFtZSI6IlNwYXJrX0xvZ2lzdGljX1JlZ3Jlc3Npb25fVHJhaW5fSm9iIiwiYWxwaW5lLmlzRmlyc3RMaW5lSGVhZGVyIjoidHJ1ZSJ9"
	 * , // "yarn-standalone", "--num-workers", "1", "--master-memory", "1g",
	 * "--worker-memory", "512m", "--worker-cores", "1"};
	 * 
	 * new org.apache.spark.deploy.yarn.Client( new ClientArguments(argStrings,
	 * sparkConf), hadoopConfig,sparkConf).run();
	 * 
	 * }
	 */
 

		JavaClientArguments arguments = new JavaClientArguments(
				lorJarFile,
				"com.alpine.spark.algorithm.classification.logisticregression.SparkLogisticRegressionTrainJob",
				new String[] { "eyJhbHBpbmUuZGVsaW1pdGVyIjoiLCIsImFscGluZS5sYW1iZGEiOiIwIiwiYWxwaW5lLmRlcGVuZGVudENvbHVtbk5hbWUiOiJwbGF5Iiwic3BhcmsuZGVwbG95TW9kZSI6Inlhcm4tc3RhbmRhbG9uZSIsInNwYXJrLndvcmtlckNvcmVzIjoiMSIsImFscGluZS5zZWxlY3RlZENvbHVtbk5hbWVMaXN0IjoiW1widGVtcGVyYXR1cmVcIixcImh1bWlkaXR5XCJdIiwiYWxwaW5lLm9wdGltaXplciI6Ik5ld3RvbiIsImFscGluZS5pbnB1dEZpbGUiOiJoZGZzOi8vbG9jYWxob3N0OjkwMDAvY3N2L2dvbGZuZXcuY3N2Iiwic3BhcmsuYWRkSmFycyI6Ii9ob21lL2pvaG4vLml2eTIvY2FjaGUvY29tLmdvb2dsZS5jb2RlLmdzb24vZ3Nvbi9qYXJzL2dzb24tMS43LjEuamFyLC9ob21lL2pvaG4vZGV2L2FkbF9tYXN0ZXIvQWxwaW5lTWluZXJTREsvdGFyZ2V0L2FscGluZW1pbmVyc2RrLTE1Y2UyMTVhMC5qYXIsL2hvbWUvam9obi9kZXYvYWRsX21hc3Rlci9BbHBpbmVEYXRhQW5hbHlzaXMvdGFyZ2V0L2FscGluZWRhdGFhbmFseXNpcy0xNWNlMjE1YTAuamFyLC9ob21lL2pvaG4vZGV2L2FkbF9tYXN0ZXIvQWxwaW5lSGFkb29wQW5hbHl0aWNzL3RhcmdldC9hbHBpbmVoYWRvb3BhbmFseXRpY3MtMTVjZTIxNWEwLmphciIsInNwYXJrLmphciI6Ii9ob21lL2pvaG4vZGV2L2FkbF9tYXN0ZXIvQWxwaW5lU3BhcmtBbmFseXRpY3MvdGFyZ2V0L2FscGluZXNwYXJrYW5hbHl0aWNzLTE1Y2UyMTVhMC5qYXIiLCJzcGFyay5udW1Xb3JrZXJzIjoiMSIsImFscGluZS5jb2x1bW5OYW1lTGlzdCI6IltcIm91dGxvb2tcIixcInRlbXBlcmF0dXJlXCIsXCJodW1pZGl0eVwiLFwid2luZFwiLFwicGxheVwiXSIsImFscGluZS5jb2x1bW5UeXBlTWFwIjoie1wicGxheVwiOlwiY2hhcmFycmF5XCIsXCJ3aW5kXCI6XCJjaGFyYXJyYXlcIixcImh1bWlkaXR5XCI6XCJsb25nXCIsXCJvdXRsb29rXCI6XCJjaGFyYXJyYXlcIixcInRlbXBlcmF0dXJlXCI6XCJsb25nXCJ9Iiwic3BhcmsubWFpbkNsYXNzIjoiY29tLmFscGluZS5zcGFyay5hbGdvcml0aG0uY2xhc3NpZmljYXRpb24ubG9naXN0aWNyZWdyZXNzaW9uLlNwYXJrTG9naXN0aWNSZWdyZXNzaW9uVHJhaW5Kb2IiLCJzcGFyay53b3JrZXJNZW1vcnkiOiI1MTJtIiwiYWxwaW5lLm1vZGVsT3V0cHV0IjoiL3RtcC9hbHBpbmVfb3V0L2Nob3J1c2FkbWluLzAwX3NwYXJrX2NkaDUvODQxZTlhOGYtOTNiMC00YzA2LWJmMmUtYjcxZmM3Zjc4YTMwLTEzOTg5NjYwMjA4MzkvTUxPUk1vZGVsLmpzb24iLCJzcGFyay5tYXN0ZXJNZW1vcnkiOiIxZyIsImFscGluZS5lcHNpbG9uIjoiMC4wMDAxIiwiYWxwaW5lLmludGVyYWN0aW9uUGFyYW1ldGVyTGlzdCI6IltdIiwiYWxwaW5lLm1heEl0ZXIiOiIxIiwic3BhcmsuYXBwTmFtZSI6IlNwYXJrX0xvZ2lzdGljX1JlZ3Jlc3Npb25fVHJhaW5fSm9iIiwiYWxwaW5lLmlzRmlyc3RMaW5lSGVhZGVyIjoidHJ1ZSJ9" }, 
				new  String[]{ "file:///home/john/.ivy2/cache/com.google.code.gson/gson/jars/gson-1.7.1.jar",
						 "file:///home/john/dev/adl_master/AlpineMinerSDK/target/alpineminersdk-15ce215a0.jar",
						  "file:///home/john/dev/adl_master/AlpineDataAnalysis/target/alpinedataanalysis-15ce215a0.jar",
						  "file:///home/john/dev/adl_master/AlpineHadoopAnalytics/target/alpinehadoopanalytics-15ce215a0.jar"
						},
				(String[]) null, 1, "1g", "512m", 1);
		SparkConf sparkConf = client.createDefaultSparkConf() ;
		Configuration hadoopConfig = client.createLocalHadoopConfig();
		
		client.runSparkJobSync(sparkConf, hadoopConfig, arguments);

	}

	@Test
	public void testSparkPIAsync(){
		
	}
}
