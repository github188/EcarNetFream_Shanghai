package com.ecar.ecarnetfream.login.entity;

import com.ecar.ecarnetwork.bean.ResResquestBase;


/**
 * 登录请求：业务Bean
 * 注：字段名不可更改，必须更接口定义的保持一致。（可改用Map方式组装json避免这种限制）
 */
public class ResLoginRequest extends ResResquestBase{

	public String uid;
	public String pwd;
	public String longi;
	public String lati;
	public String batchCode;
}
