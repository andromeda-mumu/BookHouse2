package com.example.mmc.bookhouse.newnetwork.utils;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public class ProgressUtils {
//    public static <T> ObservableTransformer<T,T> applyProgressBar(Activity activity, String msg){
//        final WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(activity);
//        final LoadProgressDialog loadProgressDialog = LoadProgressDialog.LoadBuilder.build(activityWeakReference.get()).text(msg).cancle(false).create();
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> upstream) {
//                return upstream.doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//
//                    }
//                }).doOnTerminate(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Activity context;
//                        if((context = activityWeakReference.get())!=null && !context.isFinishing()){
//                            loadProgressDialog.dismiss();
//                        }
//                    }
//                }).doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//
//                    }
//                });
//            }
//        };
//    }
//
//    public static <T> ObservableTransformer<T,T> applyProgressBar(Activity activity){
//        return applyProgressBar(activity,"");
//    }
}
