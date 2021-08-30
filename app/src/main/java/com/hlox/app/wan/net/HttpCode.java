package com.hlox.app.wan.net;

/**
 * 定义异常原因
 */
public interface HttpCode {
    /**
     * 网络读写错误
     */
    int NET_ERROR = 1000;
    /**
     * 服务器错误
     */
    int SERVER_ERROR = 2000;
    /**
     * 客户端错误
     */
    int CLIENT_ERROR = 3000;
    /**
     * 响应数据为空
     */
    int EMPTY_BODY = 4000;
    /**
     * 响应成功
     */
    int SUCCESS = 200;
}
