package com.codeboy
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/**
 * only need sbt 0.13 and scala 2.10.4
 * no need to install any spark
 */
object WordCount {
  def main(args: Array[String]) {
    val myFile = "/etc/passwd" // Should be some file on your system
    val sc = new SparkContext("local", "Simple App")
    val in = sc.textFile(myFile)
    val words = in.map(l => l.split(":")(0))
    	.filter(word => word.startsWith("_") == false)
    	.filter(word => word.startsWith("#") == false)
     
    val count = words.map(w => (w, 1)).reduceByKey(_ + _)
    println("Word Count Result:============");

    count.foreach(touple => (println(touple._1 + ":" + touple._2)))

    sc.stop()
  }
}
