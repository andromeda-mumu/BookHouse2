package com.example.mmc.bookhouse.newnetwork.convert;

import android.util.Log;

import com.example.mmc.bookhouse.newnetwork.exception.ServerResponseException;
import com.example.mmc.bookhouse.utils.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private final Gson gson;
    private final TypeAdapter<T> mAdapter;
    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter){
        this.mAdapter = adapter;
        this.gson = gson;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
            //                      BasicResponse response = (BasicResponse) mAdapter.fromJson(value.charStream());
            int code = JsonUtil.getInt(response, "code");
            String info = JsonUtil.getString(response, "info");
            String dataJson = JsonUtil.getString(response,"data");
            Log.d("=mmc=","----xx----"+dataJson);
            if (code != 0) {
                //特定api的错误，在相应的defaultObsever的onError的方法中处理
                throw new ServerResponseException(code, info);
            } else {
                MediaType mediaType = value.contentType();
                Charset charset = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
                }
                String xx = response.getBytes().toString();
                Log.d("=mmc=","----xx----"+xx);
                InputStream inputStream = new ByteArrayInputStream(response.getBytes());
                JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
                T result = mAdapter.read(jsonReader);
                return result;
            }
        }finally {
            value.close();
        }
    }
}
