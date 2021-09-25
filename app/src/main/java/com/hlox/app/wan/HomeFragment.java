package com.hlox.app.wan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hlox.app.wan.bean.Banner;
import com.hlox.app.wan.net.HttpCallback;
import com.hlox.app.wan.net.HttpClient;
import com.hlox.app.wan.net.NetConstants;
import com.hlox.app.wan.widget.LoopBanner;

import java.util.List;

public class HomeFragment extends Fragment {

    private LoopBanner mBanner;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBanner = view.findViewById(R.id.loop_banner);
        mHandler = new Handler(Looper.getMainLooper());
        HttpClient.get(NetConstants.BANNER_URL, null, null, new HttpCallback<Banner>() {
            @Override
            public void onSuccess(Banner data) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBanner.setBanner(data);
                    }
                });
            }

            @Override
            public void onError(int code, String message, Exception exception) {

            }
        }, Banner.class);
    }
}
