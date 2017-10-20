import org.apache.spark.sql.SparkSession

object WordsCountSparkJob {

  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder().appName("Word Count").master("local").getOrCreate()
    import sparkSession.implicits._

    val words = sparkSession.createDataset(Seq("Spark", "asda spark", "spark", "word", "count"))

    words.printSchema()

    val counter = words
      .flatMap(_.split(","))
      .map(word => word.toLowerCase)
      .groupByKey(value => value)
      .count()

    counter.write.option("header","true")csv("/Users/przemekpioro/siftlogic/introduction-to-spark/csv")

  }

}
