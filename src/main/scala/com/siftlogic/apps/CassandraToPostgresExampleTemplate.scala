package com.siftlogic.apps

import com.siftlogic.aggregations.RawLogsAggregator
import com.siftlogic.model.{RawLog, RawLogOutputAggregation}
import org.apache.spark.sql.{Encoder, SparkSession}


object CassandraToPostgresExampleTemplate {

  def main(args: Array[String]): Unit = {
    implicit val sparkSession = SparkSession.builder()
      .appName("Cassandra To Postgres Example")
      .config("spark.cassandra.connection.host", "localhost")
      .master("local[*]")
      .getOrCreate()

    import sparkSession.implicits._
    import org.apache.spark.sql.cassandra._

    //Input
    val rawLogs = sparkSession
      .read
      .cassandraFormat(table = "TABLE", keyspace =  "KEYSPACE", cluster = "CLUSTER") //UPDATE table,keyspace na cluster
      .load()
      .as[RawLog](implicitly[Encoder[RawLog]])

    //Aggregation // This part should be extracted to new object for testing
    val output = RawLogsAggregator.aggregate(rawLogs)

    //Save to Postgres
    output
      .write
      .option("driver", "org.postgresql.Driver")
      .jdbc(url = "URL", table = "TABLE", connectionProperties = null) //connectionProperties add user and password

  }

}
