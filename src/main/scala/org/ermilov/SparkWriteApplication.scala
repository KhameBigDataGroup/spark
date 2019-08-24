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

  val ssc = new StreamingContext(sc, Seconds(10))

  val brokers = "172.17.0.1:9092"

  val kafkaParams: Map[String, Object] = Map[String, Object](
    "bootstrap.servers" -> brokers,
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "group_id",
    "auto.offset.reset" -> "earliest",
    "enable.auto.commit" -> (false: java.lang.Boolean))

  ssc.checkpoint("/tmp/")

  //Kafka Stream
  val stream: DStream[String] = KafkaConsumerFactory.createKafkaMessageStream(Array("first_topic"), ssc).map(record => record.value())
  stream.foreachRDD(rdd => print(rdd))

  ssc.start()
  ssc.awaitTermination()
  ssc.stop()


  //  val numbersRdd = sc.parallelize((1 to 80000).toList)
  //  print(numbersRdd.count)
  //    numbersRdd.saveAsTextFile("hdfs://namenode:8020/tmp/nsadsdasad-umbers-as-text-4564")
}

