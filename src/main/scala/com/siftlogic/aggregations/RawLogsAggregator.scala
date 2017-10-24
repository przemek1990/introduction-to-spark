package com.siftlogic.aggregations

import com.siftlogic.model.{RawLog, RawLogOutputAggregation}
import org.apache.spark.sql.{Dataset, SparkSession}


object RawLogsAggregator {

  def aggregate(logs: Dataset[RawLog])(implicit sparkSession: SparkSession): Dataset[RawLogOutputAggregation] = {
    import sparkSession.implicits._
    logs.map(log => RawLogOutputAggregation(id = log.id))
  }
}