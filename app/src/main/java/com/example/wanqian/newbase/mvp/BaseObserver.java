package com.example.wanqian.newbase.mvp;

import com.example.wanqian.newbase.BaseContent;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 *描述：数据处理基类
 *作者：Created by zhuzhen
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {

    public static final String CODE = BaseContent.basecode;

    protected BaseView view;
    /**
     * 网络连接失败  无网
     */
    public static final int NETWORK_ERROR = 100000;
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 1008;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 1007;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 1006;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1005;

    /**
     * 未登录  与服务器约定返回的值   这里未登录只是一个案例
     */
    public static final int CONNECT_NOT_LOGIN = 1001;
    /**
     * 其他code  提示
     */
    public static final int OTHER_MESSAGE = 20000;


    public BaseObserver(BaseView view) {
        this.view = view;
    }


    @Override
    protected void onStart() {

    }

    @Override
    public void onNext(T o) {
        try {
            BaseModel model = (BaseModel) o;
            if (model.getCode().equals(CODE)) {
                onSuccess(model);
            } else if (model.getCode().equals("301") || model.getCode().equals("302") || model.getCode().equals("303")) {
                onCode(model.getCode());
            } else {
                onError(model.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e.toString());
        }
    }

    /**
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK, "");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR, "");
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT, "");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(PARSE_ERROR, "");
            e.printStackTrace();

        }  else {
            if (e != null) {
                onError(e.toString());
            } else {
                onError("未知错误");
            }
        }

    }

    /**
     * 中间拦截一步  判断是否有网络  这步判断相对准确  此步去除也可以
     *
     * @param unknownError
     * @param message
     */
    private void onException(int unknownError, String message) {
       String strUnknownError = Integer.toString(unknownError);
        BaseModel model = new BaseModel(message, strUnknownError);
        onExceptions(model.getCode(), model.getMsg());
        if (view != null) {
            view.onErrorCode(model);
        }
    }

    private void onExceptions(String unknownError, String message) {
        int intUnknownError = Integer.parseInt(unknownError);
        switch (intUnknownError) {
            case CONNECT_ERROR:
                onError("连接错误");
                break;
            case CONNECT_TIMEOUT:
                onError("连接超时");
                break;
            case BAD_NETWORK:
                onError("网络超时");
                break;
            case PARSE_ERROR:
                onError("数据解析失败");
                break;
            //未登录
            case CONNECT_NOT_LOGIN:
//                onError("未登录");
                break;
            //正常执行  提示信息
            case OTHER_MESSAGE:
                onError(message);
                break;
            //网络不可用
            case NETWORK_ERROR:
                onError("网络不可用，请检查网络连接！");
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(BaseModel o);

    public abstract void onError(String msg);

    public abstract void onCode(String  code);

}
