package com.codeboy.simplesparkclient;

import java.io.File;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.spark.SparkConf;
import org.junit.Test;

/***
 * mvn -e -Dtest=com.codeboy.simplesparkclient.JavaYarnClientSparkPiTest test
 * @author zhaoyong
 *
 */
//in the env :SPARK_JAR= /home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-assembly_2.10-0.9.0-cdh5.0.0-hadoop2.3.0-cdh5.0.0.jar
public class JavaYarnClientSparkPiTest  {
//	String sparkPIJarFile ="/home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-examples_2.10-0.9.0-cdh5.0.0.jar"; 
//	String yarnLogDir = "/cdh5/data/1/yarn/logs";
	static String sparkPIJarFile =System.getenv().get("spark_example_jar");
	static String yarnLogDir = System.getenv().get("yarn_log_dir");
	 	
	static JavaYarnClient client  = new JavaYarnClient();
 
	@Test
	public void testSparkPISync() throws InterruptedException{

		System.out.println("sparkPIJarFile="+sparkPIJarFile);
		System.out.println("yarnLogDir="+yarnLogDir);
		
		JavaClientArguments arguments = creataArgumentForSparkPi();
		SparkConf sparkConf = client.createDefaultSparkConf() ;
		Configuration hadoopConfig = client.createLocalHadoopConfig();
		
		client.runSparkJobSync(sparkConf, hadoopConfig, arguments);
 
		 Thread.sleep(1000*60);//wait to 1 minuts
		 System.out.println("testSparkPISync finished");

	}

	//yarn-standalone is in 0.9
	//and now is  
	private static JavaClientArguments creataArgumentForSparkPi() {
		JavaClientArguments arguments = new JavaClientArguments(
				sparkPIJarFile,
				"org.apache.spark.examples.SparkPi",
				new String[] { "yarn-standalone"}, (String[]) null,
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
	
	public static void main(String args[]) {
		System.out.println(YarnConfiguration.YARN_APPLICATION_CLASSPATH);
		System.out.println("sparkPIJarFile="+sparkPIJarFile);
		System.out.println("yarnLogDir="+yarnLogDir);
		
		JavaClientArguments arguments = creataArgumentForSparkPi();
		SparkConf sparkConf = client.createDefaultSparkConf() ;
		Configuration hadoopConfig = client.createLocalHadoopConfig();
		
		client.runSparkJobSync(sparkConf, hadoopConfig, arguments);
		Assert.assertTrue(true);
 		 
		 try {
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}//wait to 1 minuts
		 System.out.println("testSparkPISync finished");
	} 
}
