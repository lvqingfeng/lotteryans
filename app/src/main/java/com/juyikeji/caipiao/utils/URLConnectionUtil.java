package com.juyikeji.caipiao.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.juyikeji.caipiao.app.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/*
 *网络请求封装类 
 */
public class URLConnectionUtil {
    public static String UL = "http://192.168.1.134:8080/lotteryservice/user/";
    public static String UL1 ="http://192.168.1.134:8080/lotteryservice/";
    public static int CACHE_TIME = 1000;//24个小时

    /**
     * 通过Post方式提交参数给服务器,也可以用来传送json或xml文件
     */
    public static String sendPostRequest(String urlPath,
                                         Map<String, String> params, String encoding, int type) throws Exception {
        String Url = "";
        if (type == 0) {
            Url = UL + urlPath;
        } else {
            Url = UL1 + urlPath;
        }

        StringBuilder sb = new StringBuilder();
        // 如果参数不为空
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // Post方式提交参数的话，不能省略内容类型与长度
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), encoding))
                        .append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        // 得到实体的二进制数据
        byte[] entitydata = sb.toString().getBytes();
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        // 如果通过post提交数据，必须设置允许对外输出数据
        conn.setDoOutput(true);
        // 这里只设置内容类型与内容长度的头字段
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        // conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Charset", encoding);
        conn.setRequestProperty("Content-Length",
                String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        // 把实体数据写入是输出流
        outStream.write(entitydata);
        // 内存中的数据刷入
        outStream.flush();
        outStream.close();
        // 如果请求响应码是200，则表示成功
        if (conn.getResponseCode() == 200) {
            // 获得服务器响应的数据
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), encoding));
            // 数据
            String retData = null;
            String responseData = "";
            while ((retData = in.readLine()) != null) {
                responseData += retData;
            }
            in.close();
            //Log.i("shuju", responseData);
            return responseData;
        }
        return "sendText error!";
    }

    /**
     * 判断网络是否可用
     *
     * @return
     */
    public static boolean isOpenNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 保存json数据到sd卡
     *
     * @param ser
     * @param file
     * @return
     */
    public static boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = MyApplication.getInstance().openFileOutput(file, MyApplication.getInstance().MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 从sd卡读取数据
     *
     * @param file
     * @return
     * @throws
     */
    public static Serializable readObject(String file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = MyApplication.getInstance().openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 判断SD卡的数据是否需要更新
     *
     * @param cachefile
     * @return
     */
    public static boolean isCacheDataFailure(String cachefile) {
        boolean failure = false;
        File data = MyApplication.getInstance().getFileStreamPath(cachefile);
        if (data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME) {
            failure = true;
        } else if (!data.exists()) {
            failure = true;
        }
        return failure;
    }
}
