package com.hlox.app.wan.net;

/**
 * 请求回调
 * @param <T>
 */
public interface HttpCallback<T> {
    /**
     * 请求成功回调
     * @param data 数据
     */
    void onSuccess(T data);

    /**
     * 请求失败回调
     * @param code 失败码
     * @param message 原因
     * @param exception 异常
     */
    void onError(int code,String message,Exception exception);
}
