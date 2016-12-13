package com.dshl365

import com.alibaba.fastjson.JSON
import kafka.serializer.StringDecoder
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by Administrator on 2016/11/2 0002.
 */

object OrderShipAnalyser {

  def main(args: Array[String]) {
    val sparkConf = new SparkConf()
      .setAppName("OrderShipAnalyser")
      .setMaster("local[2]")

    /** step1 create streamingContext object */
    val ssc = new StreamingContext(sparkConf, Seconds(20))

    /** step2 'ordercode,operation_date,status' */
    val kafkaDstream = kafkaInput(ssc)

    /** step3 get kafka data format
      *
    {"tableName": "logistics",
	    "data": {
        "OrderNo": "D184811848451",
        "LogisticInfo": "物流信息",
        "LogisticTime": "20161110 10:00"
       }
    }
      * */
    val events = kafkaDstream.flatMap(line => {
      val data = JSON.parseObject(line._2)
      Some(data)
    })

    events
      .foreachRDD(rdd => {

      rdd.foreachPartition(records => {
        /** hbase config and put data into it */
        val conf = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.quorum", "ds1:2181,ds2:2181,ds3:2181")
        val connection = ConnectionFactory.createConnection(conf)

        records.foreach(record => {

          val table = connection.getTable(TableName.valueOf(HbaseConstants.ORDER_SHIP_TABLE_NAME))

          /** 动态put所有的列 */

          val tableN = record.get("tableName")

          val info = JSON.parseObject(record.get("data").toString)
          val orderNO = info.getString("OrderNo")
          /** rowKey设计：orderNo的反转+"-"+时间戳 */
          val rowKey = Bytes.toBytes(reverse(orderNO)._1 + "-" + System.currentTimeMillis())

          val put = new Put(rowKey)
          var familyCloumn = ""
          for (infoKey <- info.keySet().toArray) {
            if (tableN == "logistics") {
              familyCloumn = HbaseConstants.ORDER_SHIP_FAMLIY_1
            } else if (tableN == "orderFlow") {
              familyCloumn = HbaseConstants.ORDER_SHIP_FAMLIY_2
            } else {
              familyCloumn = HbaseConstants.ORDER_SHIP_FAMLIY_3
            }

            /** put column and value to hbase */
           // println(Bytes.toString(rowKey) + "|"+ infoKey.toString + "|"+ info.get(infoKey))
            putColumn(put, familyCloumn, HbaseConstants.map(infoKey.toString), info.get(infoKey).toString)
          }

          table.put(put)


          if (null != table) {
            table.close()
          }

        })
        if (null != connection) {
          connection.close()
        }

      })
    })
    ssc.start()
    ssc.awaitTermination()

  }

  /** connect kafka config */
  def kafkaInput(ssc: StreamingContext): InputDStream[(String, String)] = {
    //创建kafka参数map
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "ds3:9092,ds4:9092,ds5:9092")
    val topics = Array("os")
    val topicsSet = topics.toSet

    //创建Kafka输入流消息
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)
    messages
  }

  /** reverse rowkey and return scan startKey ,endKey */
  def reverse(str: String): (String, String) = {
    val start = String.valueOf(str.charAt(1))
    val end = String.valueOf(Integer.valueOf(start) + 1)
    val replaceCode = str.replace(start, end)
    /* val replaceCode = str.replace("D2", "D3")*/
    val builder = new StringBuilder(str)
    val builder2 = new StringBuilder(replaceCode)
    val startRowKey = builder.reverse
    val endRowKey = builder2.reverse
    (startRowKey.toString(), endRowKey.toString())
  }

  def putColumn(put: Put, family: String, column: String, value: String): Object = {
    put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value))
    return this
  }

}
