import org.apache.spark.sql.SparkSession

object WordsCountSparkJob {

  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder().appName("Word Count").master("local").getOrCreate()
    import sparkSession.implicits._

    val words = sparkSession.createDataset(Seq("Spark", "apache spark", "spark", "word", "count"))

    val counter = words
      .flatMap(_.split(" "))
      .map(word => word.toLowerCase.trim)
      .groupByKey(value => value)
      .count()

    counter.show(10)

  }

}
