package com.siftlogic.apps

import org.apache.spark.sql.SparkSession

object LazyEvaluationExampleSparkApp {

  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder().appName("Lazy Evalulation").master("local").getOrCreate()
    import sparkSession.implicits._

    val words = sparkSession.createDataset(Seq("Spark", "apache spark", "spark", "word", "count")) //INPUT

    val output = words
      //STAGE 1
      .flatMap(_.split(" ")) //TRANSFORMATION NARROW
      .map(word => word.toLowerCase.trim) // TRANSFORMATION NARROW
      //STAGE 2
      .groupByKey(value => value) // TRANSFORMATION WIDE
      .count() //TRANSFORMATION NARROW

    // output.show()


  }

}
