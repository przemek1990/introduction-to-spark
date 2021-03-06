package com.siftlogic.apps

import org.apache.spark.sql.SparkSession

object AccumulatorsExampleSparkApp {

  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder().appName("Accumulators Example").master("local[*]").getOrCreate()
    import sparkSession.implicits._

    val input = sparkSession.createDataset(Seq("spark", "apache spark", "spark", "word", "count"))

    val accumulator = sparkSession.sparkContext.longAccumulator

    val wordsCountWithWeights = input
      .flatMap(expression => expression.split(" "))
      .map(word => {
        accumulator.add(word.length.toLong)
        word.toLowerCase.trim
      })
      .groupByKey(identity)
      .count()

    wordsCountWithWeights.show()
    println(s"Accumulator: ${accumulator.value}")


  }

}
