package com.siftlogic.apps

import org.apache.spark.sql.SparkSession


object BroadcastVariableExampleSparkApp {


  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder().appName("Broadcast Example").master("local").getOrCreate()
    import sparkSession.implicits._

    val broadcastedWordsWeights = sparkSession.sparkContext.broadcast(Map("spark" -> 2.0,"apache" -> 1.5))

    val input = sparkSession.createDataset(Seq("spark", "apache spark", "spark", "word", "count"))

    val wordsCountWithWeights = input.flatMap(_.split(" "))
      .map(word => word.toLowerCase.trim)
      .groupByKey(value => value)
      .count()
    .map{ case(word, numbers) => (word, numbers * broadcastedWordsWeights.value.getOrElse(key = word, default = 1.0))}

    wordsCountWithWeights.show()

  }

}
