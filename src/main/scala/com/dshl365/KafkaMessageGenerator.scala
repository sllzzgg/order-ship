package com.dshl365

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import org.json.JSONObject

import scala.util.Random

/**
 * Created by Administrator on 2016/11/3 0003.
 */
object KafkaMessageGenerator {
  private val random = new Random()
  private var pointer = -1
  private val os_type = Array(
    "Android", "IPhone OS",
    "None", "Windows Phone")

  def click(): Double = {
    random.nextInt(10)
  }

  def getOsType(): String = {
    pointer = pointer + 1
    if (pointer >= os_type.length) {
      pointer = 0
      os_type(pointer)
    } else {
      os_type(pointer)
    }
  }

  def main(args: Array[String]): Unit = {
    val topic = "os"
    //本地虚拟机ZK地址
    val brokers = "ds3:9092,ds4:9092,ds5:9092"
    val props = new Properties()
    props.put("metadata.broker.list", brokers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")

    val kafkaConfig = new ProducerConfig(props)
    val producer = new Producer[String, String](kafkaConfig)

   while (true) {
      // prepare event data
      val event = new JSONObject()
      val ordercode = System.currentTimeMillis() + Random.nextInt(10)
      //10035036130116120D:10035036130116120Y:10035036130116120J
      //D20161103163053001:Y20161103163053001:j20161103163053001
      val orderRow = "10035036130116120D"+"-" + System.currentTimeMillis
      val order_code = resver("D"+getTime)
      val ship_code =  resver("S"+getTime)
      val pay_code =   resver("P"+getTime)

     // val row = order_code+":" + ship_code +":" + pay_code
    /*  event
        .put("OrderNo", System.currentTimeMillis()+"00") //随机生成用户id
        .put("LogisticTime", System.currentTimeMillis.toString) //记录时间发生时间
        .put("LogisticInfo", "hehe") //点击次数
        .put("WayBillNo", "WayBillNo") //点击次数
        .put("Type", "Type") //点击次数
        .put("CreateTime", "CreateTime") //点击次数
        .put("Carrier", "Carrier") //点击次数
        .put("Status", "Status") //点击次数
        .put("DistriStationCode", "DistriStationCode") //点击次数
        .put("DistriStationName", "DistriStationName") //点击次数
        .put("LineId", "LineId") //点击次数
        .put("LineName", "LineName") //点击次数
        .put("CarNo", "CarNo") //点击次数
        .put("OpraterPhone", "OpraterPhone") //点击次数
        */
       /* .put("OpraterName", "OpraterName") //点击次数
        .put("BeginTime", "BeginTime") //点击次数
        .put("EndTime", "EndTime") //点击次数*/


      event
        .put("OrderNo", System.currentTimeMillis()+"00") //随机生成用户id
        .put("ActionCode", System.currentTimeMillis.toString) //记录时间发生时间
        .put("ActionInfo", "ActionInfo") //点击次数
        .put("ActionType", "ActionType") //点击次数
        .put("ActionUser", "ActionUser") //点击次数
        .put("ActionTime", "ActionTime") //点击次数
        .put("LogisticCode", "LogisticCode") //点击次数
        .put("Remark", "Remark") //点击次数
        .put("ReturnOrderNo", "ReturnOrderNo") //点击次数
        .put("ReturnRemark", "ReturnRemark") //点击次数
        .put("TradeNo", "TradeNo") //点击次数
        .put("TradeRemark", "TradeRemark") //点击次数


    /*  event
        .put("OrderNo", System.currentTimeMillis()+"00") //随机生成用户id
        .put("UserId", 110) //记录时间发生时间
        .put("TradeNo", "TradeNo") //点击次数
        .put("TradeTime", "TradeTime") //点击次数
        .put("Type", "Type") //点击次数
        .put("Amount", "Amount") //点击次数
        .put("CurrencyType", "CurrencyType") //点击次数
        .put("GateWay", "GateWay") //点击次数
        .put("PayeeBank", "PayeeBank") //点击次数
        .put("PayerBank", "PayerBank") //点击次数
        .put("PayerNo", "PayerNo") //点击次数
        .put("PayeeNo", "PayeeNo") //点击次数
        .put("ReconStatus", "ReconStatus") //点击次数
        .put("Remark", "Remark") //点击次数
        .put("Status", "Status") //点击次数
 */

var od = "D"+getTime
      val mesg = "{\"tableName\": \"logistics\",\"data\": {\"OrderNo\": \""+od+"\",\"LogisticInfo\": \"物流信息\",\"LogisticTime\": \"20161110 10:00\"}}"

      val mesg2="{\"tableName\": \"orderFlow\",\"data\": {\"OrderNo\": \""+od+"\",\"ActionCode\": \"12564\",\"ActionInfo\": \"用户操作\",\"ActionType\": 1,\"ActionTime\": \"20161110 10:00\" }}"

      val mess3 = "{\"tableName\": \"tradeInfo\",\"data\": {\"OrderNo\": \""+od+"\",\"TradeNo\": \"5451651\",\"UserId\": 100000}}"
      // produce event message
    val value =   Math.random()*3.toInt
     if(value >0 && value <1){
       producer.send(new KeyedMessage[String, String](topic, mesg))
       println("Message sent: " + mesg)
     }else if(value>1 && value<2){
       producer.send(new KeyedMessage[String, String](topic, mesg2))
       println("Message sent: " + mesg2)
     }else{
       producer.send(new KeyedMessage[String, String](topic, mess3))
       println("Message sent: " + mess3)
     }



     // println("Message sent: " + mesg)


      Thread.sleep(500)
    }
  }

  def getTime: String = {
    val date = new Date()
    val sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    sdf.format(date)
  }

  def resver(str: String): String = {
    val builder = new StringBuilder(str)
    builder.reverse.toString()
  }
}