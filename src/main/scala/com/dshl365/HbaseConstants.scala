package com.dshl365

/**
 * Created by Administrator on 2016/11/10 0010.
 */
object HbaseConstants {
  /**订单物流**/
  val  ORDER_SHIP_TABLE_NAME = "os" //表名
  val  ORDER_SHIP_FAMLIY_1   = "f1" //列族1，物流信息
  val  ORDER_SHIP_FAMLIY_2   = "f2" //列族2，订单流转信息
  val  ORDER_SHIP_FAMLIY_3   = "f3" //列族3，订单交易信息

  /**物流信息字段*/
  val  L_OrderNo             = "lno" //订单号
  val  LogisticTime          = "lt" //物流时间
  val  LogisticInfo          = "li" //物流信息描述包含车辆，人员，电话等信息
  val  WayBillNo             = "wn" //物流单号
  val  Type                  = "t"  //物流类型 整向物流单/逆向物流单
  val  CreateTime            = "ct" //创建时间
  val  Carrier               = "cr" //承运人
  val  Status                = "s"  //物流信息状态
  val  DistriStationCode     = "dc" //配送站编号
  val  DistriStationName     = "dn" //配送站名称
  val  LineId                = "lid" //线路id
  val  LineName              = "ln" //线路名称
  val  CarNo                 = "cn" //车牌号
  val  OpraterPhone          = "op" //操作人手机
  val  OpraterName           = "on" //操作人姓名
  val  BeginTime             = "bt" //开始时间
  val  EndTime               = "et" //结束时间

  /**流转信息字段*/
  val  A_OrderNo             = "ano" //订单号
  val  ActionCode            = "ac" //操作的动作编号
  val  ActionInfo            = "ai" //操作信息主动记录
  val  ActionType            = "at" //动作类型 用户操作/管理员操作/客服人员操作/仓储物流人员反馈操作
  val  ActionUser            = "au" //操作人
  val  ActionTime            = "ati" //操作时间
  val  LogisticCode          = "lc" //物流信息编号如果是仓促物流反馈操作 需要物流日志编号
  val  Remark                = "re" //物流信息简要概述
  val  ReturnOrderNo         = "ron" //退换货单号,如果是退换货操作需要退换货单号
  val  ReturnRemark          = "rrk" //退换货简要概述
  val  TradeNo               = "to" //交易信息编号
  val  TradeRemark           = "tr" //交易简要概述

  /**交易信息字段**/
  val  UserId                = "ui" //用户id
  val  T_TradeNo             = "tn" //流水号
  val  T_OrderNo             = "tno" //订单号
  val  TradeTime             = "tt" //交易时间
  val  T_Type                = "tp" //交易类型
  val  Amount                = "am" //交易金额
  val  CurrencyType          = "ct" //货币类型
  val  GateWay               = "gw" //交易网关
  val  PayeeBank             = "pe" //收款人开户银行
  val  PayerBank             = "pr" //付款人开户银行
  val  PayerNo               = "pn" //付款账号
  val  PayeeNo               = "py" //收款人账号
  val  ReconStatus           = "rs" //对账状态
  val  T_Remark              = "tre" //备注信息
  val  T_Status              = "ts" //交易状态


  var map = Map(
    "OrderNo"  -> "lno",
    "LogisticTime" -> "lt" ,
    "LogisticInfo" -> "li" ,
    "WayBillNo"  -> "wn" ,
    "Type"       -> "t" ,
    "CreateTime" -> "ct" ,
    "Carrier"    -> "cr" ,
    "Status"     -> "s" ,
    "DistriStationCode" -> "dc" ,
    "DistriStationName" -> "dn" ,
    "LineId"     -> "lid",
    "LineName"   -> "ln" ,
    "CarNo"      -> "cn" ,
    "OpraterPhone" -> "op" ,
    "OpraterName"-> "on" ,
    "BeginTime"  -> "bt" ,
    "EndTime"    -> "et" ,
    "A_OrderNo"  -> "ano",
    "ActionCode" -> "ac" ,
    "ActionInfo" -> "ai" ,
    "ActionType" -> "at" ,
    "ActionUser"-> "au" ,
    "ActionTime" -> "ati",
    "LogisticCode" -> "lc",
    "Remark"     -> "re",
    "ReturnOrderNo" -> "ron",
    "ReturnRemark"  -> "rrk",
    "TradeNo"    -> "to" ,
    "TradeRemark"-> "tr" ,
    "UserId"     -> "ui" ,
    "TradeTime"  -> "tt" ,
    "T_Type"     -> "tp" ,
    "Amount"     -> "am" ,
    "CurrencyType" -> "ct" ,
    "GateWay"    -> "gw" ,
    "PayeeBank"  -> "pe" ,
    "PayerBank"  -> "pr" ,
    "PayerNo"    -> "pn" ,
    "PayeeNo"    -> "py" ,
    "ReconStatus"-> "rs" ,
    "T_Remark" -> "tre",
    "T_Status"  -> "ts"
  )
}
