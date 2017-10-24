package com.siftlogic.aggregations

import com.siftlogic.SparkSessionSpec
import com.siftlogic.model.{RawLog, RawLogOutputAggregation}


class RawLogsAggregatorSpec extends SparkSessionSpec {

  describe("RawLogAggregator"){
    it("should map to output"){

      val sqlContext = sparkSession.sqlContext
      import sqlContext.implicits._

      val input = sparkSession.createDataset(List(RawLog(id = "id_1")))

      val output = RawLogsAggregator.aggregate(input).collect().toList

      output should have size 1

      output.head should equal(RawLogOutputAggregation(id = "id_1"))

    }
  }
}