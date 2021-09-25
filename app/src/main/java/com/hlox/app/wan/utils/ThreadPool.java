package com.hlox.app.wan.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ThreadPool sPool;
    private ExecutorService executors;
    private ThreadPool(){
        executors = Executors.newFixedThreadPool(4);
    }
    public static ThreadPool getInstance(){
        if(sPool == null){
            synchronized (ThreadPool.class){
                if(sPool == null){
                    sPool = new ThreadPool();
                }
            }
        }
        return sPool;
    }

    public void execute(Runnable runnable){
        executors.execute(runnable);
    }
}
