package com.example.mmc.bookhouse.newnetwork.api;


import com.example.mmc.bookhouse.newnetwork.response.BasicResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wangjiao on 2019/10/24.
 * description: 项目接口
 */

public interface WzApi {
    @FormUrlEncoded
    @POST("app/book/number")
    Observable<BasicResponse<Object>> getBookNumber(@Field("type") String type);

    @FormUrlEncoded
    @POST("/app/user/closeChildProtected")
    Observable<BasicResponse<Object>> closeYounth(@Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("app/user/openChildProtected")
    Observable<BasicResponse<Object>> openYounth(@Field("pwd") String pwd);



    @FormUrlEncoded
    @POST("app/user/unlockChildProtected")
    Observable<BasicResponse<Object>> timoOutYounth(@Field("pwd") String pwd);

//    @Multipart      //文件上传类型头
//    @POST("app/user/uploadphoto")
//    Observable<BasicResponse<UploadBean>> uploadHeadPhoto(@Part("place") RequestBody key, @Part MultipartBody.Part file);
//
//    @Multipart      //文件上传类型头
//    @POST("app/user/uploadImg")
//    Observable<BasicResponse<UploadImgBean>> uploadImg(@Part MultipartBody.Part file);

}
