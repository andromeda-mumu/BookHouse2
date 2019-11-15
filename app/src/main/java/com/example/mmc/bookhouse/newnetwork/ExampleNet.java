package com.example.mmc.bookhouse.newnetwork;

import com.example.mmc.bookhouse.newnetwork.observer.DefaultObserver;
import com.example.mmc.bookhouse.newnetwork.transformer.RxResponseTransformer;
import com.example.mmc.bookhouse.newnetwork.transformer.RxSchedulersTransformer;
import com.example.mmc.bookhouse.utils.Toast;

import io.reactivex.functions.Function;

/**
 * Created by wangjiao on 2019/11/15.
 * description:
 */

public class ExampleNet{
    public void getUser(){
        //没有返回值的，一些指令操作
        RetrofitHelper.getWzApi().getBookNumber("science")
                .compose(RxResponseTransformer.handleResult())
                .compose(RxSchedulersTransformer.io_main())
                .map(new Function<Object, String>() {

                    @Override
                    public String apply(Object object) throws Exception {
                        return object==null?"1":"2";
                    }
                })
                .subscribe(new DefaultObserver<String>() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.show("请求成功");
                    }
                });
    }
}
