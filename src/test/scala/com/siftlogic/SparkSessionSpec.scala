package com.siftlogic

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}


trait SparkSessionSpec extends FunSpec with Matchers with BeforeAndAfter {

  protected implicit var sparkSession: SparkSession = _

  before {
    sparkSession = SparkSession.builder()
      .appName("Spark session for spec")
      .master("local[*]")
      .getOrCreate()
  }

  after {
    if (sparkSession != null) {
      sparkSession.stop()
    }
  }
}