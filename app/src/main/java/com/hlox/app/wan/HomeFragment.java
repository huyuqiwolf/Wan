package com.hlox.app.wan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hlox.app.wan.bean.Banner;
import com.hlox.app.wan.bean.HomeArticle;
import com.hlox.app.wan.net.HttpCallback;
import com.hlox.app.wan.net.HttpClient;
import com.hlox.app.wan.net.NetConstants;
import com.hlox.app.wan.widget.LoopBanner;

import java.util.Locale;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeFragment";
    private LoopBanner mBanner;
    private Handler mHandler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private int mHomePageNum = 0;
    private int mCurrPage;
    private int mPageCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBanner = view.findViewById(R.id.loop_banner);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mHandler = new Handler(Looper.getMainLooper());
        // 初始化banner
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

        // 加载首页数据
        HttpClient.get(getHomeUrl(mHomePageNum), null, null, new HttpCallback<HomeArticle>() {
            @Override
            public void onSuccess(HomeArticle data) {
                // 数据请求成功
                mCurrPage = data.getData().getCurPage();
                mPageCount = data.getData().getPageCount();
                fillData(data, false);
            }

            @Override
            public void onError(int code, String message, Exception exception) {
                Log.e(TAG, "onError: " + code + "," + message + "," + exception);
            }
        }, HomeArticle.class);
        mAdapter = new ArticleAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        // 监听加载更多
        initLoadMore();
        // 监听下拉刷新
        initSwipeRefresh();
    }

    private void initLoadMore() {
       mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               if(mCurrPage == mPageCount && mCurrPage != 0){
                   // 已经没有新数据了
                   return;
               }
               LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
               int position = manager.findLastCompletelyVisibleItemPosition();
               if(position == manager.getItemCount()-1){
                    // 滑动到底部了
                   mHomePageNum++;
                   HttpClient.get(getHomeUrl(mHomePageNum), null, null, new HttpCallback<HomeArticle>() {
                       @Override
                       public void onSuccess(HomeArticle data) {
                           mCurrPage = data.getData().getCurPage();
                           mPageCount = data.getData().getPageCount();
                           fillData(data,true);
                       }

                       @Override
                       public void onError(int code, String message, Exception exception) {

                       }
                   },HomeArticle.class);
               }
           }
       });
    }

    private void initSwipeRefresh() {
        // 下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void fillData(HomeArticle data, boolean add) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(add){
                    mAdapter.addData(data.getData().getDatas());
                }else {
                    mAdapter.setData(data.getData().getDatas());
                }
            }
        });
    }

    private String getHomeUrl(int pageNum) {
        return String.format(Locale.ENGLISH, NetConstants.HOME_ARTICAL, pageNum);
    }

    @Override
    public void onRefresh() {
        HttpClient.get(getHomeUrl(0), null, null, new HttpCallback<HomeArticle>() {
            @Override
            public void onSuccess(HomeArticle data) {
                mCurrPage = data.getData().getCurPage();
                mPageCount = data.getData().getPageCount();
                fillData(data, false);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(int code, String message, Exception exception) {
                Log.e(TAG, "onRefresh error:" + code + "," + message + "," + exception);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, HomeArticle.class);
    }
}
