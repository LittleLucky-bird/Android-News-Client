package com.ihandy.a2013010230;

/**
 * Created by tangpeng on 16/8/29.
 */


import java.io.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.*;

public class JsonString {

    public static String sendHttpRequest(String address) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line+"\n");// 一行行的读取内容并追加到builder中去
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();//连接不为空就关闭连接
            }
        }
    }
}