package org.ermilov

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.dstream.DStream


object SparkWriteApplication extends App {
  val config = new SparkConf().setMaster("spark://spark-master:7077").setAppName("SparkWriteApplication")
    .set("spark.driver.memory", "2g")
    .set("spark.executor.memory", "2g")
    .set("spark.executor.memoryOverhead", "2g")
    .set("spark.executor.cores", "7")
    .set("spark.cores.max", "8")
    .set("spark.executor.instances", "1")


  val sc = new SparkContext(config)

  val ssc = new StreamingContext(sc, Seconds(2))

  ssc.checkpoint("/bitcoin/checkpoint")


  //Kafka Stream
  val stream: DStream[String] = KafkaConsumerFactory.createKafkaMessageStream(Array("bitcoin"), ssc).map(record => record.value())
  stream.saveAsTextFiles("hdfs://namenode:8020/bitcoin/topic")
  ssc.start()
  ssc.awaitTermination()
  ssc.stop()
}

