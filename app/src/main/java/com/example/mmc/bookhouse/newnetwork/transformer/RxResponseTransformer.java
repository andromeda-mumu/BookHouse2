package com.example.mmc.bookhouse.newnetwork.transformer;


import com.example.mmc.bookhouse.newnetwork.exception.ServerResponseException;
import com.example.mmc.bookhouse.newnetwork.response.BasicResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by wangjiao on 2019/10/25.
 * description:
 */

public class RxResponseTransformer {
    public static final <T>ObservableTransformer<BasicResponse<T>,T> handleResult(){
        return new ObservableTransformer<BasicResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BasicResponse<T>> upstream) {
                return upstream.flatMap(new Function<BasicResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(BasicResponse<T> response) throws Exception {
                        if(response.getCode()==0){
                            return createData(response.getData());
                        }else{
                            return Observable.error(new ServerResponseException(response.getCode(),response.getInfo()));
                        }
                    }
                });
            }
        };
    }

    public static <T> Observable<T> createData(final T data){
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subsciber) throws Exception {
                try{
                    subsciber.onNext(data);
                    subsciber.onComplete();
                }catch (Exception ex){
                    subsciber.onError(ex);
                }
            }
        });
    }
}
