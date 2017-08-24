package com.ecar.ecarnetfream.login.presenter;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.ecar.ecarnetfream.login.entity.ResLogin;
import com.ecar.ecarnetfream.login.interfaces.LoginContract;
import com.ecar.ecarnetfream.publics.network.Datacenter;
import com.ecar.ecarnetfream.publics.network.api.ApiService;
import com.ecar.ecarnetfream.publics.util.TagUtil;
import com.ecar.ecarnetwork.base.BaseSubscriber;
import com.ecar.ecarnetwork.bean.ResResponseBase;
import com.ecar.ecarnetwork.http.api.ApiBox;
import com.ecar.ecarnetwork.http.exception.CommonException;
import com.ecar.ecarnetwork.http.exception.InvalidException;
import com.ecar.ecarnetwork.http.exception.UserException;
import com.ecar.ecarnetwork.http.util.TagLibUtil;
import com.ecar.ecarnetwork.util.rx.RxUtils;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginPresenter extends LoginContract.Presenter {

    /**
     * 单元测试 采用依赖参数 构造时 一起注入，方便mockito
     *
     * @param context
     * @param view
     * @param model
     */
    public LoginPresenter(Activity context, LoginContract.View view, LoginContract.Model model) {
        super(context, view, model);
    }

    @Override
    public void login(String name, String pwd) {
        if (TextUtils.isEmpty(name) || name.length() < 11) {
            TagUtil.showToast(context, "账号或密码有误");
            return;
        }
        if (TextUtils.isEmpty(pwd) || name.length() < 6) {
            TagUtil.showToast(context, "账号或密码有误");
            return;
        }
        if (TextUtils.isEmpty(pwd) || name.length() < 6) return;

//        rxLogin1(name, pwd);
//        rxLogin3(name, pwd);
        test();
    }

    private void test() {
        ApiService apiService = ApiBox.getInstance().createService(ApiService.class,"http://10.50.50.8:8999/");
        String temp = "{\"code\":\"P011\",\"commRequest\": {\"clientId\":\"001\",\"tsn\":\"1234567890\",\"sim\":\"1234567890\",\"pasm\":\"1234567890\",\"sysVer\":\"wince5\",\"appVer\":\"pm1.0\"},\"uid\":\"010001\",\"pwd\":\"123456\",\"longi\":\"123.4567\",\"lati\":\"23.4567\",\"batchcode\":\"2011080101000101\",\"name\":\"华宁国际停车场\",\"address\":\"宣化路300号\",\"opentime\":\"24小时\",\"price\":\"10元每小时\"}";
//        temp = "commLen=345&zipFlag=0&jsonContent={\"code\":\"P011\",\"commRequest\": {\"clientId\":\"001\",\"tsn\":\"1234567890\",\"sim\":\"1234567890\",\"pasm\":\"1234567890\",\"sysVer\":\"wince5\",\"appVer\":\"pm1.0\"},\"uid\":\"010001\",\"pwd\":\"123456\",\"longi\":\"123.4567\",\"lati\":\"23.4567\",\"batchcode\":\"2011080101000101\",\"name\":\"华宁国际停车场\",\"address\":\"宣化路300号\",\"opentime\":\"24小时\",\"price\":\"10元每小时\"}";
        TagLibUtil.showLogDebug(temp);
        int length = temp.getBytes().length;
        int l1 = temp.length();
        Map<String,Object> map = new HashMap();
        map.put("code","01");
        map.put("uid","015001");
        map.put("pwd","015001");
        map.put("longi","116.314806");
        map.put("lati","39.884797");
        map.put("batchCode","2017082101500101");
        Map<String,String > map2 = new HashMap();
        map2.put("clientId","001");
        map2.put("tsn","1234567890");
        map2.put("sim","1234567890");
        map2.put("psam","1234567890");
        map2.put("sysVer","wince5");
        map2.put("appVer","pm1.0");
        map.put("commRequest",new Gson().toJson(map2));//test

        String s = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),s);

        int i = 0x400;
        int j = 400;
        BigInteger h = new BigInteger("00000400",16);
        String s1 = h.toString();
//        apiService.getResult(map)
                apiService.getResult3("345",temp)
        .compose(RxUtils.getScheduler(true, view)).subscribe(new BaseSubscriber<ResResponseBase>(context,view) {
            @Override
            protected void onUserSuccess(ResResponseBase resBase) {

            }
        });
    }


    private void rxLogin1(String name, String pwd) {

        //1.订阅者 泛型：最终想要获取的数据类型
        //一般弹toast的失败处理已处理，若需改写重写 onUserError 并去掉super(xx).
        BaseSubscriber<ResResponseBase> subscriber = new BaseSubscriber<ResResponseBase>(context, view) {

            @Override
            protected void onUserSuccess(ResResponseBase resBase) {
                view.showMsg("单个请求" + resBase.msg);
            }

            /**
             * 第三方 返回校验失败
             * @param context
             * @param commonException
             */
            @Override
            protected void onCheckNgisFailed(Context context, InvalidException commonException) {
                super.onCheckNgisFailed(context, commonException);
                if (commonException.getResObj() != null) {
                    ResResponseBase resBase = commonException.getResObj();
                }
            }

            @Override
            protected void onUnifiedError(CommonException ex) {
                super.onUnifiedError(ex);
////                try {
//                    int i = 1 / 0;
////                }catch (Exception e){
//                    ex.printStackTrace();
            }
    }

    ;

    //一个请求（登录）
    Subscription subscribe = Datacenter.get().login(name, pwd).compose(RxUtils.getScheduler(true, view)).subscribe(subscriber);
        rxManage.add(subscribe);//添加到订阅集合中


}

    private void rxLogin3(String name, String pwd) {
        //1.订阅者 泛型：最终想要获取的数据类型
        BaseSubscriber<ResResponseBase> subscriber = new BaseSubscriber<ResResponseBase>(context, view) {
            @Override
            protected void onUserSuccess(ResResponseBase resBase) {
                view.showMsg(resBase.msg);
            }
        };


        //链式请求（登录成功后上传图片）
        //2.此处有两步操作，登录成功后切换线程 在主线程显示成功提示（观察的线程可以有多次切换，订阅线程只能指定一次）
        Subscription subscribe1 = Datacenter.get().login(name, pwd).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Action1<ResLogin>() {//登录成功后回调
            @Override
            public void call(ResLogin resLogin) {
                /**
                 * 此处会出错，还在子线程中. 执行前需要先指定观察的线程位置,即login(xx,xx)后面的observeOn
                 */
                view.showMsg("链式请求第一个响应" + resLogin.msg);
//                view.loginSuccess(resLogin);//使用 当前获得的数据。区别于map 改变数据的操作
            }
        }).flatMap(new Func1<ResLogin, Observable<ResResponseBase>>() {
            @Override
            public Observable<ResResponseBase> call(ResLogin resLogin) {
                Observable<ResResponseBase> result = null;
                try {
                    //登录成功后请求上传（写死的一张 酷派手机上的图）
                    result = Datacenter.get().uploadPic("").compose(RxUtils.getScheduler(false, view));
                } catch (UserException e) {
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        }).delay(4000, TimeUnit.MILLISECONDS).compose(RxUtils.getScheduler(true, view)).subscribe(subscriber);

        rxManage.add(subscribe1);//添加到订阅集合中
    }


    private void rxLogin2(String name, String pwd) {
        //        // 异步网络请求User数据，并在onNext(Res1Base)返回 ---测试单元测试使用 例子
        Scheduler scheduler = AndroidSchedulers.mainThread();
        Subscription subscribe1 = Datacenter.get().login(name, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(new Action1<ResLogin>() {
                    @Override
                    public void call(ResLogin resLogin) {
                        view.loginSuccess(resLogin);
                    }
                });
    }


    @Override
    public void sign(String name, String pass) {
    }


}
