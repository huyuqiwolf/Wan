package com.hlox.app.wan.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hlox.app.wan.R;
import com.hlox.app.wan.bean.Banner;
import com.hlox.app.wan.net.ImageUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义View，实现无限循环的Banner
 */
public class LoopBanner extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final int DELAY = 5000;
    private Context mContext;
    private ViewPager mViewPager;
    private Banner mBanner;
    private Adapter mAdapter;
    private Handler mHandler;
    private int mCurrent;
    private Runnable mLoop = new Runnable() {
        @Override
        public void run() {
            if(mBanner== null ||mBanner.getData() == null || mBanner.getData().size()<=1){
                return;
            }
            if(mHandler == null || mViewPager == null){
                return;
            }
            mViewPager.setCurrentItem(++mCurrent);
            mHandler.postDelayed(this,DELAY);
        }
    };

    public LoopBanner(@NonNull Context context) {
        this(context, null);
    }

    public LoopBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
        inflate(context, R.layout.layout_banner, this);
        mViewPager = findViewById(R.id.pager);
        mAdapter = new Adapter(mContext);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        setSpeed();
    }

    private void setSpeed() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(mViewPager,new ViewPagerScroller(mContext));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void setBanner(Banner banner) {
        this.mBanner = banner;
        List<Banner.Data> data = mBanner.getData();
        if (data != null) {
            mAdapter.setData(data);
            mAdapter.notifyDataSetChanged();
            mCurrent = 10000 * data.size();
            mViewPager.setCurrentItem(mCurrent,false);
            mHandler.postDelayed(mLoop,DELAY);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        if (listener == null) {
            return;
        }
        if (mAdapter == null) {
            return;
        }
        mAdapter.setListener(listener);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("TAG", "onPageSelected: "+position );
        mCurrent = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    static class ViewPagerScroller extends Scroller{
    private int mDuration = 2000;
        public ViewPagerScroller(Context context) {
            super(context);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy,mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    static class Adapter extends PagerAdapter {

        private List<Banner.Data> data;
        private List<ImageView> mList;

        private OnClickListener mListener;
        private Context mContext;
        private Handler mHandler;

        public Adapter(Context mContext) {
            this.mContext = mContext;
            mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public int getCount() {
            return data == null ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int temp = position % mList.size();
            ImageView imageView = mList.get(temp);
            ImageUtils.loadImage(imageView, ((Banner.Data) imageView.getTag()).getImagePath(), mHandler);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onClick((Banner.Data) imageView.getTag());
                    }
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            int temp = position % mList.size();
            container.removeView(mList.get(temp));
        }

        public void setData(List<Banner.Data> data) {
            this.data = data;
            if (mList != null) {
                mList.clear();
            } else {
                mList = new ArrayList<>();
            }
            if (this.data != null) {
                ImageView pre = new ImageView(mContext);
                pre.setTag(this.data.get(this.data.size()-1));
                pre.setScaleType(ImageView.ScaleType.FIT_XY);
                mList.add(pre);

                for (int i = 0; i < this.data.size(); i++) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setTag(this.data.get(i));
                    mList.add(imageView);
                }

                ImageView next = new ImageView(mContext);
                next.setTag(this.data.get(0));
                next.setScaleType(ImageView.ScaleType.FIT_XY);
                mList.add(next);
            }
        }

        public void setListener(OnClickListener listener) {
            this.mListener = listener;
        }
    }

    public interface OnClickListener {
        void onClick(Banner.Data data);
    }
}
