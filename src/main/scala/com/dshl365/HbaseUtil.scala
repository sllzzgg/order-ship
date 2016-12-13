package com.dshl365

import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{TableName, HBaseConfiguration}
import org.apache.hadoop.hbase.client.{Put, ConnectionFactory}

/**
 * Created by Administrator on 2016/11/3 0003.
 */
object HbaseUtil {

  val conf = HBaseConfiguration.create();
  conf.set("hbase.zookeeper.quorum", "ds1:2181,ds2:2181,ds3:2181");
  val connection = ConnectionFactory.createConnection(conf);

  /**
   * 插入数据
   */
  def put(tableName: String, ordercode: String, columnFamily: String, qualifier: String, value: String): Unit = {
    val table = connection.getTable(TableName.valueOf(tableName))
    val rowKey = Bytes.toBytes(ordercode)
    val put = new Put(rowKey)

    put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), Bytes.toBytes(value))
    // put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("status"), Bytes.toBytes(status))
    table.put(put)

    table.close
    closeConnection
  }

  def closeConnection(): Unit = {
    if (null != connection) {
      connection.close()
    }
  }

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

  def main(args: Array[String]) {
  // print( reverse("D20161111093511"))
   for(i <- 1 to 20 ){
     val a =  Math.random()*3.toInt
     println(a)
   }
  }

}
