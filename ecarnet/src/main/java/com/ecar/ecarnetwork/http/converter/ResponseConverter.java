package com.ecar.ecarnetwork.http.converter;

import android.text.TextUtils;
import android.util.Log;

import com.ecar.ecarnetwork.bean.ResResponseBase;
import com.ecar.ecarnetwork.db.SettingPreferences;
import com.ecar.ecarnetwork.http.api.ApiBox;
import com.ecar.ecarnetwork.http.exception.InvalidException;
import com.ecar.ecarnetwork.http.util.ConstantsLib;
import com.ecar.ecarnetwork.http.util.InvalidUtil;
import com.ecar.ecarnetwork.http.util.TagLibUtil;
import com.ecar.ecarnetwork.util.uvtKeys;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义响应体的转换器
 */
public class ResponseConverter<T> implements Converter<ResponseBody, T> {

    /**
     * 1.是否分出 ResponseBody value =null 时
     * 选择1.1 抛出自定义NetException。异常判断 ，判断instanceof NetException
     * 选择1.2 判断Http 异常。根据retrofit自动抛出的（疑问，是解析抛出的？还是异常直接抛出？）
     * <p>
     * 2.1 策略1：直接返回json解析。订阅者subscriber 中判断state 自处理
     * <p>
     * 2.2 策略2：根据泛型解析json后 (已采用)
     * 1.1 判断state 成功/失败 （失败抛ResultException，订阅者在OnError中自处理）
     * 成功-->返回json解析（直接返回反序列话后bean 或者二次解析，看需求）
     * 失败-->抛失败异常（1.state--目测一体化没什么用，2.msg）
     * <p>
     * --------->2*2 四种情况
     */
    public static final String TAG = "ResponseConverter";

    private final Gson gson;
    private final Type type;

    ResponseConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(final ResponseBody value) throws IOException {
        ResResponseBase base = null;
        String response = null;
        try {
            response = value.string();
            TagLibUtil.showToast(ApiBox.getInstance().application,"收到服务器响应："+response);
        } catch (IOException e) {
            e.printStackTrace();
            TagLibUtil.showLogDebug("请求成功，获取返回值失败");
            return null;
        }
        Log.i("thread",Thread.currentThread().getName()) ;
        try {
            base = gson.fromJson(response, type);
            /**
             * 保存utv
             */
            saveUTV(base);
            if (base != null && base.commResponse!=null) {//非成功
                if ("5".equals(base.commResponse.result)) {//终端被别的用户登录
                    TagLibUtil.showLogDebug("终端被别的用户登录");
                    //重新登录
                    throw new InvalidException(InvalidException.FLAG_ERROR_RELOGIN,base.getMsg(),base);
                } else {//失败 -- 订阅者 自己在onNext做处理逻辑
//                    if(base==null&&!TextUtils.isEmpty(response)){
//                        base.jsonStr=response;
//                    }
//                    throw new UserException(base.code, base.msg,base);
                }
            }
        } finally {
            value.close();
        }
//        if(base!=null&&!TextUtils.isEmpty(response)){
//            base.jsonStr=response;
//        }
        return (T) base;
    }

    /**
     * 保存 u 、v 、t 到sp
     *
     * @param resBase
     */
    private void saveUTV(ResResponseBase resBase) {
    }


}
