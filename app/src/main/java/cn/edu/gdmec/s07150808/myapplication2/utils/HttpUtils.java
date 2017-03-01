package cn.edu.gdmec.s07150808.myapplication2.utils;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import cn.edu.gdmec.s07150808.myapplication2.bean.ChatMessage;
import cn.edu.gdmec.s07150808.myapplication2.bean.CommonException;
import cn.edu.gdmec.s07150808.myapplication2.bean.Result;
import cn.edu.gdmec.s07150808.myapplication2.bean.ChatMessage.Type;

/**
 * Created by chen on 2017/2/27.
 * class name HttpUtils 工具类
 * name setParams 并接URL的功能了类
 * name doGet Get请求，返回数据源
 *@return 发出请求,得到回应
 */
public class HttpUtils {
    /*定义两个常量，APK_KEY 与 URL*/
    private static String API_KEy = "534dc342ad15885dffc10d7b5f813451";
    private  static String URL = "http://www.tuling123.com/openapi/api";


    public static String setParams(String msg) {
        try {
            msg = URLEncoder.encode(msg, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return URL + "?key=" + API_KEy + "&info=" + msg;
    }

    public static String doGet(String urlStr) {
        URL url = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        ByteArrayOutputStream baos = null;

        try {
         /*创建链接 调用openConnection 实现连接*/
            url = new URL(urlStr);

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
         /*链接的方式 */
            conn.setRequestMethod("GET");
         /*通过调用响应的返回状态，判断的请求结果*/
            if (conn.getResponseCode() == 200) {
             /*创建输出流*/
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();


            } else {
                throw new CommonException("服务器连接错误！ 404");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
   return   baos.toString();
    }

    /*定义一个方法，实现接收用户信息，并进行网络的请求*/
    public static ChatMessage sendMsg(String msg){
     ChatMessage message = new ChatMessage();
        String url = setParams(msg);
        String res = doGet(url);
        Gson gson = new Gson();
        Result result =gson.fromJson(res,Result.class);


        if (result.getCode() > 400000 || result.getText() == null
                || result.getText().trim().equals(""))
        {
            message.setMsg("该功能等待开发...");
        }else
        {
            message.setMsg(result.getText());
        }

        message.setType(Type.INPUR);
        message.setDate(new Date());

        return message;
    }


}
