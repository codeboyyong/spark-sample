package com.codeboy.simplesparkclient;

import java.io.File;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.spark.SparkConf;
import org.junit.Test;

//in the env :SPARK_JAR= /home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-assembly_2.10-0.9.0-cdh5.0.0-hadoop2.3.0-cdh5.0.0.jar
public class JavaYarnClientParkPiTest  {
	String sparkPIJarFile ="/home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-examples_2.10-0.9.0-cdh5.0.0.jar"; 
	String yarnLogDir = "/cdh5/data/1/yarn/logs";
	
	JavaYarnClient client  = new JavaYarnClient();
//	//@Test
//	public void testSparkPISync(){
//
//		JavaClientArguments arguments = creataArgumentForSparkPi();
//		SparkConf sparkConf = client.createDefaultSparkConf() ;
//		Configuration hadoopConfig = client.createLocalHadoopConfig();
//		
//		client.runSparkJobSync(sparkConf, hadoopConfig, arguments);
//		Assert.assertTrue(true);
//
//	}

	private JavaClientArguments creataArgumentForSparkPi() {
		JavaClientArguments arguments = new JavaClientArguments(
				sparkPIJarFile,
				"org.apache.spark.examples.SparkPi",
				new String[] { "yarn-standalone" }, (String[]) null,
				(String[]) null, 1, "1g", "512m", 1);
		return arguments;
	}

	@Test
	public void testSparkPIAsync() throws Exception{
		JavaClientArguments arguments = creataArgumentForSparkPi();
		SparkConf sparkConf = client.createDefaultSparkConf() ;
		Configuration hadoopConfig = client.createLocalHadoopConfig();
		
		ApplicationId appID = client.runSparkJobAsync(sparkConf, hadoopConfig, arguments);
		System.out.println(System.currentTimeMillis() +": start runApp :"+appID.getId());
		int waitingTime=2*1000*60; //wait to 2 minuts
		int time= 0;
		String aid = Integer.toString(10000+appID.getId()).substring(1);
		while (time<waitingTime){
		
			try {
				Thread.sleep(1000*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String wholeALLId = "application_"+appID.getClusterTimestamp()+"_"+aid;
			String path = yarnLogDir+File.separator+wholeALLId;
			String[] files = new File(path).list();
			
  		    if(files.length>0){
  		    	System.out.println("Async run finished:");
  		    	for(int i =0;i<files.length;i++){
  		    		String stdOut = path+File.separator+files[i] +File.separator+"stdout";
  		    		System.out.println("Output "+i+" =" +FileUtils.readFileToString(new File(stdOut)));
  		    	}
  		    	Assert.assertTrue(true);

  		    	return;
  		    } 
		}
		
	}
}
