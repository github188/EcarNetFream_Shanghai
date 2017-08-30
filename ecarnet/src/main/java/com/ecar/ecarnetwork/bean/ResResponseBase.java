package com.ecar.ecarnetwork.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 接口应答基类
 */
public class ResResponseBase implements Serializable {
    //	流水号：seqno（int）
//	业务编号：code（string）
//	通用响应：commResponse
//	返回码（string），主机时间（yyyyMMddHHmmss），消息(string)；
//	字段命名：
//	返回码：result（string）
//	主机时间：systime（DateTime）
//	消息:msg（string）
    @SerializedName("seqno")
    public int seqno;

    @SerializedName(value = "code")
    public String code;// 业务编号：code（string）

    @SerializedName(value = "commResponse")
    public ResResponseComm commResponse;//通用响应字段

    public String getMsg() {
        if(commResponse!=null){
            return commResponse.getMsg();
        }
        return "";
    }
}
