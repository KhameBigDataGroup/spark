package org.ermilov

import org.apache.spark.{SparkConf, SparkContext}

object SparkWriteApplication extends App {
  val config = new SparkConf().setMaster("spark://172.17.0.1:7077").setAppName("SparkWriteApplication")
    .set("spark.driver.memory", "500m")
    .set("spark.executor.memory", "500m")
    .set("spark.executor.memoryOverhead", "100m")
    .set("spark.executor.cores", "4")
    .set("spark.cores.max", "4")
    .set("spark.executor.instances", "1")


  val sc = new SparkContext(config)

  val numbersRdd = sc.parallelize((1 to 80000).toList)
  print(numbersRdd.count)
    numbersRdd.saveAsTextFile("hdfs://localhost:8020/tmp/nsadsdasad-umbers-as-text-4564")
}

