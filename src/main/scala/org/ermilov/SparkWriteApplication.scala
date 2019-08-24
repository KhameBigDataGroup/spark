package org.ermilov

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.DStream


object SparkWriteApplication extends App {
  val config = new SparkConf().setMaster("spark://spark-master:7077").setAppName("SparkWriteApplication")
    .set("spark.driver.memory", "500m")
    .set("spark.executor.memory", "500m")
    .set("spark.executor.memoryOverhead", "100m")
    .set("spark.executor.cores", "4")
    .set("spark.cores.max", "4")
    .set("spark.executor.instances", "1")


  val sc = new SparkContext(config)

  val ssc = new StreamingContext(sc, Seconds(5))

  ssc.checkpoint("/tmp/")


  //Kafka Stream
  print("hello")
  val stream: DStream[String] = KafkaConsumerFactory.createKafkaMessageStream(Array("first_topic"), ssc).map(record => record.value())
  print("salaam")
  stream.foreachRDD(rdd => print(rdd.toString()))
  print("Byeeee")
  ssc.start()
  ssc.awaitTermination()
  ssc.stop()


  //  val numbersRdd = sc.parallelize((1 to 80000).toList)
  //  print(numbersRdd.count)
  //    numbersRdd.saveAsTextFile("hdfs://namenode:8020/tmp/nsadsdasad-umbers-as-text-4564")
}

