package com.codeboy

import scala.util.Random
import breeze.linalg.{Vector, DenseVector}

object LocalLR {
  val N = 10000 // Number of data points
  val D = 10 // Number of dimensions
  val R = 0.7 // Scaling factor
  val ITERATIONS = 5
  val rand = new Random(42)

  case class DataPoint(x: Vector[Double], y: Double)

  def generateData = {
    def generatePoint(i: Int) = {
      val y = if (i % 2 == 0) -1 else 1
      val x = DenseVector.fill(D) { rand.nextGaussian + y * R }
      DataPoint(x, y)
    }
    Array.tabulate(N)(generatePoint)
  }

  def showWarning() {
    System.err.println(
      """WARN: This is a naive implementation of Logistic Regression and is given as an example!
        |Please use the LogisticRegression method found in org.apache.spark.mllib.classification
        |for more conventional use.
      """.stripMargin)
  }

  def main(args: Array[String]) {

    showWarning()

    val data = generateData
    // Initialize w to a random value
    var w = DenseVector.fill(D) { 2 * rand.nextDouble - 1 }
    println("Initial w: " + w)

    for (i <- 1 to ITERATIONS) {
      println("On iteration " + i)
      var gradient = DenseVector.zeros[Double](D)
      for (p <- data) {
        val scale = (1 / (1 + math.exp(-p.y * (w.dot(p.x)))) - 1) * p.y
        gradient += p.x * scale
      }
      w -= gradient
    }

    println("Final w: " + w)
  }
}