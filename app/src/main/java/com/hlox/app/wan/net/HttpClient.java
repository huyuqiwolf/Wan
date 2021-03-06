package com.hlox.app.wan.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.hlox.app.wan.utils.ThreadPool;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 网络请求工具类
 */
public class HttpClient {
    private static final String TAG = "HttpClient";
    private static final int READ_TIMEOUT = 5 * 1000;
    private static final int WRITE_TIMEOUT = 30 * 1000;
    private static final int CONNECT_TIMEOUT = 10 * 1000;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final int BUFFER_SIZE = 1024 * 8;


    public static <T> void postForm(String requestUrl, Map<String, String> headers, Map<String, String> params, HttpCallback<T> callback, Class<T> clazz) {
        ThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = makeConnection(requestUrl, POST, headers);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    OutputStream outputStream = connection.getOutputStream();
                    if (params != null && !params.isEmpty()) {
                        StringBuilder builder = new StringBuilder();
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            builder.append(entry.getKey()).append("=").append(entry.getValue())
                                    .append("&");
                        }
                        builder.setLength(builder.length() - 1);
                        outputStream.write(builder.toString().getBytes());
                    }
                    doRequest(callback, connection, clazz);
                } catch (IOException e) {
                    if (callback != null) {
                        callback.onError(HttpCode.NET_ERROR, "网络异常", e);
                    }
                }
            }
        });
    }

    public static <T> void post(String requestUrl, Map<String, String> headers, Map<String, String> params, HttpCallback<T> callback, Class<T> clazz) {
        ThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = makeConnection(requestUrl, POST, headers);
                    connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    if (params != null && !params.isEmpty()) {
                        OutputStream outputStream = connection.getOutputStream();
                        String data = GsonUtils.GsonString(params);
                        outputStream.write(data.getBytes("UTF-8"));
                    }
                    doRequest(callback, connection, clazz);
                } catch (IOException e) {
                    if (callback != null) {
                        callback.onError(HttpCode.NET_ERROR, "网络异常", e);
                    }
                }
            }
        });
    }

    public static <T> void get(String requestUrl, Map<String, String> headers, Map<String, String> params, HttpCallback<T> callback, Class<T> clazz) {
        ThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder builder = new StringBuilder(requestUrl);
                    if (params != null && !params.isEmpty()) {
                        builder.append("?");
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                        }
                        builder.setLength(builder.length() - 1);
                    }
                    HttpURLConnection connection = makeConnection(builder.toString(), GET, headers);
                    doRequest(callback, connection, clazz);
                } catch (IOException e) {
                    if (callback != null) {
                        callback.onError(HttpCode.NET_ERROR, "网络异常", e);
                    }
                }
            }
        });
    }

    public static <T> void downloadImage(String requestUrl, Map<String, String> headers, Map<String, String> params, HttpCallback<T> callback) {
        ThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder builder = new StringBuilder(requestUrl);
                    if (params != null && !params.isEmpty()) {
                        builder.append("?");
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                        }
                        builder.setLength(builder.length() - 1);
                    }
                    HttpURLConnection connection = makeConnection(builder.toString(), GET, headers);

                    doDownloadImage(callback, connection);
                } catch (IOException e) {
                    if (callback != null) {
                        callback.onError(HttpCode.NET_ERROR, "网络异常", e);
                    }
                }
            }
        });
    }

    private static void doDownloadImage(HttpCallback callback, HttpURLConnection connection) throws IOException {
        connection.connect();
        int code = connection.getResponseCode();
        if (code == HttpCode.SUCCESS) {
            byte[] result = readResponse(connection.getInputStream());
            Bitmap b = BitmapFactory.decodeByteArray(result, 0, result.length);
            if (callback != null) {
                callback.onSuccess(b);
            }
        } else {
            if (callback != null) {
                callback.onError(HttpCode.SERVER_ERROR, "code: " + code, null);
            }
        }
        connection.disconnect();
    }


    private static <T> void doRequest(HttpCallback<T> callback, HttpURLConnection connection, Class<T> clazz) throws IOException {
        connection.connect();
        int code = connection.getResponseCode();
        if (code == HttpCode.SUCCESS) {
            byte[] bytes = readResponse(connection.getInputStream());
            if (bytes == null) {
                if (callback != null) {
                    callback.onError(HttpCode.SERVER_ERROR, "code:" + code, null);
                }
            } else {
                String result = new String(bytes);
                T data = parseData(result, clazz);
                if (callback != null) {
                    callback.onSuccess(data);
                }
            }
        } else {
            if (callback != null) {
                callback.onError(HttpCode.SERVER_ERROR, "code:" + code, null);
            }
        }
        connection.disconnect();
    }

    private static <T> T parseData(String result, Class<T> clazz) {
        return GsonUtils.GsonToBean(result, clazz);
    }

    private static byte[] readResponse(InputStream inputStream) {
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream baos = null;
        BufferedInputStream bufferedInputStream = null;
        byte[] result = null;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            baos = new ByteArrayOutputStream();
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            result = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static HttpURLConnection makeConnection(String requestUrl, String method, Map<String, String> headers) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection;
        if (requestUrl.startsWith("http://") || requestUrl.startsWith("https://")) {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
        } else {
            throw new IllegalArgumentException("only support http or https.");
        }
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        //connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        return connection;
    }
}
