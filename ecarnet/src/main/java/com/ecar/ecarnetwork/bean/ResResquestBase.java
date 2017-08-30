package com.ecar.ecarnetwork.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 接口访问基类
 */
public class ResResquestBase implements Serializable {
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

    @SerializedName("seqno")
    public int seqno;

    @SerializedName(value = "code")
    public String code;// 业务编号：code（string）

    @SerializedName(value = "commRequest")
    public ResRequestComm commRequest;//


    /**
     * 通讯内容是否压缩；1 字节
     * 1 – 通讯内容经过压缩；
     * 0 – 通讯内容未压缩；
     */
    public String getCompressFlag() {
        return "0";
    }

    public String getContentJson(){
        return new Gson().toJson(this);
    }

    /**
     * 计算获取json长度：
     * 表示“通讯内容”部分的长度；4 字节
     * 矛盾点：1.协议规定长度格式：[0x00,0x04,0x00,0x00]表示1024；
     *         2.某个例子看到的内容：commLen=394&zipFlag=0&jsonContent=
     */
    public String getContentLen(String content) {
        return getLen1(content);
    }


    /**
     * 获取16进制 排序后（反序）长度
     * 1.协议规定长度格式：[0x00,0x04,0x00,0x00]表示1024；
     * --->16进制，靠左为低位字节
     * 思路：1.分别求出四个字节中 各字节的10进制值
     *       2.遍历按顺将10进制转换为16进制值并拼接
     * @return
     */
    private String getLen1(String content){
        int lenInt = getLenInt(content);
        //从低位开始取
        String lenStr = "";
        for(int i=0;i<4;i++){
            lenStr += Integer.toHexString(lenInt%256);//取余 求16进制。若int型数据太大采用new BigInteger(str,16).toString();
            lenInt = lenInt/256;
        }
        return lenStr;
    }


    /**
     * 获取10进制 长度
     * 2.某个例子看到的内容：commLen=394&zipFlag=0&jsonContent=
     * @return
     */
    private String getLen2(String content){
        return String.valueOf(getLenInt(content));
    }

    /**
     * 获取10进制 长度 int型
     * @param content
     * @return
     */
    private int getLenInt(String content){
        int result = 0;
        if(!TextUtils.isEmpty(content)){
            result = content.getBytes().length;
        }
        return result;
    }

}
