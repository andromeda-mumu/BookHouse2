package com.example.mmc.bookhouse.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class JsonUtil {


    public static String getJson2String(String jsonData, String key) {

        if (null == key)
            return null;

        JSONObject obj;
        try {
            obj = new JSONObject(jsonData.trim());

            String value = obj.get(key).toString();
            return value;
        } catch (JSONException e) {
        }
        return null;
    }

    /**
     * 将java对象转换成json对象
     *
     * @param obj
     * @return
     */
    public static String parseObj2Json(Object obj) {

        if (null == obj)
            return null;

        Gson gson = new Gson();
        String objstr = gson.toJson(obj);
        return objstr;
    }

    /**
     * 将java对象的属性转换成json的key
     *
     * @param obj
     * @return
     */
    public static String parseObj2JsonOnField(Object obj) {

        if (null == obj)
            return null;

        Gson gson = new GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
        String objstr = gson.toJson(obj);
        return objstr;
    }


    public static <T> T parseJsonObj(String jsonData, Class<T> c)
            throws NullPointerException {

        if (null == jsonData)
            return null;

        Gson gson = new Gson();
        T obj = null;
        try {
            obj = gson.fromJson(jsonData.trim(), c);
        } catch (Exception e) {
            return null;
        }

        return obj;
    }

    /**
     * 将json对象转换成数组对象
     *
     * @param <T>
     * @param jsonData
     * @param c
     * @return
     * @throws JSONException
     */
    public static <T> ArrayList<T> parseJson2List(String jsonData, Class<T> c) {

        if (null == jsonData || "".equals(jsonData))
            return null;


        Gson gson = new Gson();
        ArrayList<T> list = new ArrayList<T>();
        JSONObject objItem = null;
        T objT = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData.trim());
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {

                objItem = (JSONObject) jsonArray.get(i);
                if (null != objItem) {
                    objT = gson.fromJson(objItem.toString(), c);
                    list.add(objT);
                }
            }
        } catch (Exception e) {
            return null;
        }

        return list;
    }


    public static BaseModel parseJson2Model(@Nullable String jsonData, TypeToken token) {

        if (TextUtils.isEmpty(jsonData))
            return null;

        Type type = token.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonData, type);
    }

    public static String getString(JSONObject jsonObject, String key,
                                   String defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {

            return defaultValue;
        }
    }

    public static String getString(String jsonData, String key){
        return getString(jsonData,key,"");
    }
    public static String getString(String jsonData, String key,
                                   String defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public static Integer getInt(String jsonData, String key){
        return getInt(jsonData,key,0);
    }
    public static Integer getInt(String jsonData, String key,
                                 Integer defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        } catch (JSONException e) {

            return defaultValue;
        }
    }

    public static Integer getInt(JSONObject jsonObject, String key,
                                 Integer defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public static JSONArray getJSONArrayByIndex(String json, int index) {
        JSONArray jsonArray = null;
        JSONArray indexArray = null;
        if (TextUtils.isEmpty(json)) {
            try {
                jsonArray = new JSONArray(json);
                indexArray = (JSONArray) jsonArray.get(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return indexArray;
    }




}