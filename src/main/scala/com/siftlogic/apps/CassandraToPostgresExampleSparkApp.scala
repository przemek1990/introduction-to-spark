package com.siftlogic.apps

import org.apache.spark.sql.{Encoder, SparkSession}

// add more field here. Fields should have the same name as cassandra columns
case class RawLog(id: String)
// add more field here. Fields should have the same name as postgres output table
case class OutputAggregation(id: String)

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
    val aggregation = rawLogs
      .map(log => OutputAggregation(id = log.id))

    //Save to Postgres
    aggregation
      .write
      .option("driver", "org.postgresql.Driver")
      .jdbc(url = "URL", table = "TABLE", connectionProperties = null) //connectionProperties add user and password

  }

}
