package com.siftlogic.apps

import org.apache.spark.sql.SparkSession

object WordsCountSparkApp {

  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder().appName("Word Count").master("local").getOrCreate()
    import sparkSession.implicits._

    val words = sparkSession.createDataset(Seq("Spark", "apache spark", "spark", "word", "count")) //INPUT

    val output = words
      //STAGE 1
      .flatMap(_.split(" ")) //TRANSFORMATION NARROW
      .map(word => word.toLowerCase.trim) // TRANSFORMATION NARROW
      //STAGE 2
      .groupByKey(value => value) // TRANSFORMATION WIDE
      .count() //TRANSFORMATION NARROW

/*    output.show()

    output
      .write
      .mode(SaveMode.Append)
      .jdbc(url = "URL_TO_DATABSE", table = "TABLE_NAME", connectionProperties = null  )*/

  }

}
