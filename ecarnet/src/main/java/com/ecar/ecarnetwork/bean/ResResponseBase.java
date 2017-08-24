package com.ecar.ecarnetwork.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 接口应答
 * 
 * @author Administrator
 * 
 */
public class ResResponseBase implements Serializable{
	@SerializedName("state")
	public int state;// 成功标志位（1成功，0失败）默认为0

	@SerializedName(value = "message",alternate="resultmsg")//接口所有msg 改成message  系统级错误 0x02 0x04 重新登录
	public String msg;// 错误信息

	@SerializedName(value = "resultcode",alternate="code")
	public String code;// 返回的结果code


	@SerializedName("ngis")
	public String sign;//

}
