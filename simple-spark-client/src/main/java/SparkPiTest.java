

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.deploy.yarn.ClientArguments;

public class SparkPiTest {
	
	static public String[] getDefaultArguments() {
		List<String> results = new ArrayList<String>();
		results.add("--jar");
		results.add(System.getenv("SPARK_EXAMPLE_JAR"));
 
		results.add("--class");
		results.add("org.apache.spark.examples.SparkPi");

		results.add("--args");
		results.add("yarn-standalone");

		results.add("--num-workers");
		results.add("1");
		results.add("--master-memory");
		results.add("521m");
		results.add("--worker-memory");
		results.add("521m");
		results.add("--worker-cores");
		results.add("1");

		return results.toArray(new String[results.size()]);
	}
	
	static public Configuration createLocalHadoopConfig() {
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

	public static void main(String args[]) {
 
 		SparkConf sparkConf = new SparkConf() ;
		Configuration hadoopConfig = createLocalHadoopConfig();
		
		System.out.println(System.currentTimeMillis() +": start runApp");

		 new org.apache.spark.deploy.yarn.Client(new ClientArguments(
				 getDefaultArguments(), sparkConf), hadoopConfig,
				sparkConf).run ();
		
		 
		 //Note:this line can not be executed. since in the run method it will call system.exit() finally 
		System.out.println(new Date().toString()+": finish runApp");
 		 
		 try {
			Thread.sleep(1000*9);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//wait to 9 seconds
		
		 System.out.println(new Date().toString()+": finish main method");
	} 
}
