package com.codeboy
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/**
 * only nned sbt 0.13 and scala 2.10.4
 * no need to install any spark
 */
object SimpleSampleApp2 {
  val YOUR_SPARK_HOME="/Users/zhaoyong/hadoop_soft/spark/spark-1.1.0-bin-hadoop2.3"
  def main(args: Array[String]) {
    val myFile = "/etc/hosts" // Should be some file on your system
    val sc = new SparkContext("local"
         , "Simple App"
         //, "YOUR_SPARK_HOME"
       // ,List("target/scala-2.10/spark-local-sample_2.10-1.0.jar")
      )
    val myData = sc.textFile(myFile, 2).cache()
    val numAs = myData.filter(line => line.contains("a")).count()
    val numBs = myData.filter(line => line.contains("b")).count()
    println("Lines with c: %s, Lines with d: %s".format(numAs, numBs))
    
  }
}
