package com.example.mmc.bookhouse.newnetwork.observer;

import com.example.mmc.bookhouse.newnetwork.exception.ServerResponseException;
import com.example.mmc.bookhouse.utils.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public abstract class DefaultObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
//     d.dispose();   如果想手动取消
    }

    @Override
    public void onNext(T response) {
       onSuccess(response);
       onFinish();
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof HttpException){
            onException(ExceptionReason.BAD_NETWORK);
        }else if(e instanceof ConnectException || e instanceof UnknownHostException){
            onException(ExceptionReason.CONNECT_ERROR);
        }else if( e instanceof InterruptedIOException){
            onException(ExceptionReason.CONNECT_TIMEOUT);
        }else if(e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException){
            onException(ExceptionReason.PARSE_ERROR);
        }else if (e instanceof ServerResponseException){
            onFail(((ServerResponseException) e).getCode(),e.getMessage());
        }else {
            onException(ExceptionReason.UNKNOW_ERROR);
        }
        onFinish();
    }

    @Override
    public void onComplete() {

    }

    //请求成功
    abstract public void onSuccess(T response);

    public void onFail(int code ,String msg){
        ToastUtils.show(msg);
    }

    public void onFinish(){}

    public void onException(ExceptionReason reason){
        switch(reason){
//            case CONNECT_ERROR:
//                ToastUtils.show(R.string.connect_error);
//                break;
//            case CONNECT_TIMEOUT:
//                ToastUtils.show(R.string.connect_timeout);
//                break;
//            case BAD_NETWORK:
//                ToastUtils.show(R.string.bad_network);
//                break;
//            case PARSE_ERROR:
//                ToastUtils.show(R.string.parse_error);
//                break;
//            case UNKNOW_ERROR:
//            default:
//                ToastUtils.show(R.string.unknow_error);
//                break;
        }
    }
    /**
     * 请求网络失败的原因
     */
    public enum ExceptionReason{
        PARSE_ERROR,//解析数据出错
        BAD_NETWORK,//网络问题
        CONNECT_ERROR,//链接出错
        CONNECT_TIMEOUT,//连接超时
        UNKNOW_ERROR,//未知错误
    }

}
