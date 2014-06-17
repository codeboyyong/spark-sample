import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object SimpleSampleApp {
  def main(args: Array[String]) {
    val myFile = "/etc/hosts" // Should be some file on your system
    val sc = new SparkContext("local", "Simple App", "YOUR_SPARK_HOME",
      List("target/scala-2.9.3/simple-project_2.9.3-1.0.jar"))
    val myData = sc.textFile(myFile, 2).cache()
    val numAs = myData.filter(line => line.contains("a")).count()
    val numBs = myData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
  }
}
