package com.ecar.ecarnetwork.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 接口应答
 * 
 * @author Administrator
 * 
 */
public class ResRequestComm implements Serializable{
//	业务编号（string code），通用请求字段（commRequest），业务数据字段；
//	字段命名：
//	业务编号：code（string）
//	通用请求字段：commRequest
//	1.3. 通用请求字段
//	字段命名：
//	客户编号：clientId（string）
//	终端序列号：tsn（string）
//	SIM 卡号：sim（string）
//	PSAM 卡号：psam（string）
//	系统版本号：sysVer（string）
//	应用版本号：appVer（string）

	@SerializedName("clientId")
	public String clientId;//客户编号

	@SerializedName(value = "tsn")
	public String tsn;// 终端序列号

	@SerializedName(value = "sim")
	public String sim;//SIM 卡号

	@SerializedName(value = "psam")
	public String psam;//PSAM 卡号

	@SerializedName(value = "sysVer")
	public String sysVer;//系统版本号

	@SerializedName(value = "appVer")
	public String appVer;//应用版本号
}
