package com.example.mmc.bookhouse.newnetwork.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.example.mmc.bookhouse.newnetwork.api.NetParams;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 *
 */

public class HttpCommParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HashMap<String,String> params = new HashMap<>();
        RequestBody newRequestBody =null;
        if("POST".equals(request.method())) {
            params.put(NetParams.KEY_TS, "ts");
            params.put(NetParams.KEY_CLIENTINFO,"clientInfo");
            params.put(NetParams.KEY_UHEX,"uhex");

            //传统表单
            if (request.body() instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();//新建请求body
                FormBody formBody = (FormBody) request.body();
                if (formBody.size() > 0) {
                    for (int i = 0; i < formBody.size(); i++) {
                        params.put(formBody.name(i), formBody.value(i));
                        builder.add(formBody.name(i), formBody.value(i));
                    }
                }
                newRequestBody = builder
                        .addEncoded(NetParams.KEY_TS, "getTs()")
                        .addEncoded(NetParams.KEY_CLIENTINFO, "getClientInfo()")
                        .addEncoded(NetParams.KEY_UHEX, "getUhex()")
                        .addEncoded(NetParams.KEY_TOKEN, "obtainToen()")
                        .build();
            }else if(request.body() instanceof MultipartBody){//文件
                MultipartBody multipartBody = (MultipartBody)request.body();
                MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
                multiBuilder.setType(MultipartBody.FORM);
                if(multipartBody!=null){
                    for(int i=0;i<multipartBody.size();i++){
                        MultipartBody.Part part = multipartBody.part(i);


                        MediaType mediaType = part.body().contentType();
                        if (mediaType != null) {
                            String normalParamKey;
                            String normalParamValue;
                            try {
                                normalParamValue = getParamContent(multipartBody.part(i).body());
                                Headers headers = part.headers();
                                if (!TextUtils.isEmpty(normalParamValue) && headers != null) {
                                    for (String name : headers.names()) {
                                        String headerContent = headers.get(name);
                                        if (!TextUtils.isEmpty(headerContent)) {
                                            String[] normalParamKeyContainer = headerContent.split("name=\"");
                                            if (normalParamKeyContainer.length == 2) {
                                                normalParamKey = normalParamKeyContainer[1].split("\"")[0];
                                                params.put(normalParamKey, normalParamValue);
                                                multiBuilder.addFormDataPart(normalParamKey,normalParamValue);
                                                Log.d("=mmc=","----ddd----"+normalParamKey+"--"+normalParamValue);
                                                break;
                                            }else{
                                                multiBuilder.addPart(multipartBody.part(i));
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                newRequestBody = multiBuilder
                        .addFormDataPart(NetParams.KEY_TS, "getTs()")
                        .addFormDataPart(NetParams.KEY_CLIENTINFO, "getClientInfo()")
                        .addFormDataPart(NetParams.KEY_UHEX, "getUhex()")
                        .addFormDataPart(NetParams.KEY_TOKEN, "obtainToen()")
                        .build();
            }

            request = request.newBuilder()
                    .method(request.method(),newRequestBody)
                    .build();
        }
        return chain.proceed(request);
    }


    /**
     * 获取常规post请求参数
     */
    private String getParamContent(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }
}
