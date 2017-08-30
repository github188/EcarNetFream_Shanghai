package com.ecar.ecarnetwork.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 接口应答
 * 
 * @author Administrator
 * 
 */
public class ResResponseComm implements Serializable{
//	流水号：seqno（int）
//	业务编号：code（string）
//	通用响应：commResponse
//	返回码（string），主机时间（yyyyMMddHHmmss），消息(string)；
//	字段命名：
//	返回码：result（string）
//	主机时间：systime（DateTime）
//	消息:msg（string）

	@SerializedName("result")
	public String result;//返回码：result（string）

	@SerializedName(value = "systime")
	public String systime;// 返回的结果code

	@SerializedName(value = "msg")
	public String msg;//通用响应字段

	public String getMsg() {
		return msg;
	}
}
