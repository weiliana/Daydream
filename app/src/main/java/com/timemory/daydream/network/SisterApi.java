package com.timemory.daydream.network;

import android.util.Log;

import com.timemory.daydream.bean.entity.Sister;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Description：网络请求处理相关类
 * @author AUSTER on 19.6.12.
 */
public class SisterApi {
    private static final String TAG = "Network";
    private static final String BASE_URL = "https://gank.io/api/data/福利/";

    /**查询妹子信息*/
    public ArrayList<Sister> fetchSister(int count, int page){
        String fetchUrl = BASE_URL + count + "/" + page;
        ArrayList<Sister> sisters = new ArrayList<>();
        try {
            URL url = new URL(fetchUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            Log.v(TAG, "Server response: " + code);
            if (code == 200){
                InputStream inputStream = connection.getInputStream();
                byte[] data = this.readFromStream(inputStream);
                String result = new String(data, "UTF-8");
                sisters = this.parseSister(result);
            }else {
                Log.e(TAG, "请求失败: " + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sisters;
    }

    /**读取流中数据的方法*/
    public byte[] readFromStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

    /**解析返回的Json数据的方法*/
    public ArrayList<Sister> parseSister(String content) throws Exception{
        ArrayList<Sister> sisters = new ArrayList<>();
        JSONObject object = new JSONObject(content);
        /**获取的Json下results的数据数组*/
        JSONArray array = object.getJSONArray("results");
        for (int i = 0; i < array.length(); i++){
            JSONObject result = (JSONObject)array.get(i);
            Sister sister = new Sister();
            sister.set_id(result.getString("_id"));
            sister.setCreatedAt(result.getString("createdAt"));
            sister.setDesc(result.getString("desc"));
            sister.setPublishedAt(result.getString("publishedAt"));
            sister.setSource(result.getString("source"));
            sister.setType(result.getString("type"));
            sister.setUrl(result.getString("url"));
            sister.setUsed(result.getBoolean("used"));
            sister.setWho(result.getString("who"));
            sisters.add(sister);
        }
        return sisters;
    }
}
