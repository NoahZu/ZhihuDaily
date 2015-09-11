package com.zu.jinhao.zhihuribao.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zu.jinhao.zhihuribao.model.LauncherImageJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 异步任务，根据url获取该url对应的字符串并回调一个接口处理获取到的字符串，用于json字符串的获取
 * author:jinhao.zu
 */
public class StringFromHttpLoader extends AsyncTask<String,Void,String>{

    private static final String TAG = "StringFromHttpLoader";
    private GetHttpStringListener getHttpStringListener;

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            String jsonString = response.body().string();
//            Log.d(TAG,"====json"+jsonString);
//            return jsonString;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer("");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineString;
            while((lineString = reader.readLine())!=null){
                stringBuffer.append(lineString);
            }
            return stringBuffer.toString();
        }
        catch (IOException e){
            return null;
        }
        finally {
        }
    }
    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        this.getHttpStringListener.onGetHttpString(jsonString);
    }
    public void setGetHttpStringListener(GetHttpStringListener listener){
        this.getHttpStringListener = listener;
    }
    public interface GetHttpStringListener{
        void onGetHttpString(String jsonString);
    }
}
