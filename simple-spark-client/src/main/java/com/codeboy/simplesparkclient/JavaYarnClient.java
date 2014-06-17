package com.codeboy.simplesparkclient;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.spark.SparkConf;
import org.apache.spark.deploy.yarn.ClientArguments;

//in the env :SPARK_JAR= /home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-assembly_2.10-0.9.0-cdh5.0.0-hadoop2.3.0-cdh5.0.0.jar
public class JavaYarnClient {
	

	public SparkConf createDefaultSparkConf() {
		return new SparkConf();
	}

	public ApplicationId runSparkJobAsync(SparkConf sparkConf,
			Configuration hadoopConfig, JavaClientArguments arguments) throws IOException {
		System.setProperty("SPARK_YARN_MODE", "true");
		ApplicationId appID = new org.apache.spark.deploy.yarn.Client(new ClientArguments(
				arguments.toArgumentStringArray(), sparkConf), hadoopConfig,
				sparkConf).runApp();
		return appID;
 		
	}
	public void runSparkJobSync(SparkConf sparkConf,
			Configuration hadoopConfig, JavaClientArguments arguments) {
		System.setProperty("SPARK_YARN_MODE", "true");
		System.out.println(System.currentTimeMillis() +": start runApp");

		 new org.apache.spark.deploy.yarn.Client(new ClientArguments(
				arguments.toArgumentStringArray(), sparkConf), hadoopConfig,
				sparkConf).run ();
		
		 
		 //Note:this line can not be executed. since in the run method it will call system.exit() finally 
		System.out.println(System.currentTimeMillis() +": finish runApp");
		
	}

	public Configuration createLocalHadoopConfig() {
		Configuration hadoopConfig = new Configuration();
		hadoopConfig.set("yarn.resourcemanager.scheduler.address",
				"localhost:8030");
		hadoopConfig.set("mapreduce.framework.name", "yarn");
		hadoopConfig.set("yarn.resourcemanager.address", "localhost:8032");
		hadoopConfig.set("fs.default.name", "hdfs://localhost:9000");
		hadoopConfig
				.set("yarn.application.classpath",
						"$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$USS_HOME/*,$USS_CONF,$HADOOP_HOME/lib/hadoop/client/*, $HADOOP_HOME/lib/hadoop-hdfs/lib/*, $HADOOP_HOME/lib/hadoop-mapreduce/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,  $HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*");
		return hadoopConfig;
	}

}
