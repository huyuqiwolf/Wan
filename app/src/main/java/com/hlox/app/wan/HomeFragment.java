package com.hlox.app.wan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hlox.app.wan.bean.Banner;
import com.hlox.app.wan.net.HttpCallback;
import com.hlox.app.wan.net.HttpClient;

import java.util.List;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(){
            @Override
            public void run() {
                HttpClient.get("https://www.wanandroid.com/banner/json", null, null, new HttpCallback<Banner>() {
                    @Override
                    public void onSuccess(Banner data) {

                    }

                    @Override
                    public void onError(int code, String message, Exception exception) {

                    }
                },Banner.class);
            }
        }.start();
    }
}
