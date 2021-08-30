package com.hlox.app.wan;

import com.hlox.app.wan.net.HttpClient;

import org.junit.Test;

public class HttpTest {
    @Test
    public void testPost(){
        String url = "https://www.wanandroid.com/lg/uncollect_originId/2333/json";
        HttpClient.post(url,null,null,null,null);
    }
}
