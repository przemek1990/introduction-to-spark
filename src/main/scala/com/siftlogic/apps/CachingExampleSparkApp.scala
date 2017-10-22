package com.siftlogic.apps

import org.apache.spark.sql.SparkSession

object CachingExampleSparkApp {

  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder().appName("Word Count").master("local").getOrCreate()
    import sparkSession.implicits._

    val words = sparkSession.createDataset(Seq("spark", "apache spark", "spark", "word", "count"))
      .cache() //CACHED INPUT
      //.persist(StorageLevel.DISK_ONLY)
      //.persist(StorageLevel.MEMORY_AND_DISK)
      // CHECK StorageLevel


    val numberOfWordsWithSpark = words.filter(_.contains("spark")).count()
    val numberOfWordsWithoutSpark = words.filter(!_.contains("spark")).count()

    println(s"With: $numberOfWordsWithoutSpark Without: $numberOfWordsWithSpark")

  }

}
