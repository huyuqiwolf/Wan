package com.hlox.app.wan.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hlox.app.wan.R;
import com.hlox.app.wan.bean.Banner;
import com.hlox.app.wan.net.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义View，实现无限循环的Banner
 */
public class LoopBanner extends FrameLayout {
    private Context mContext;
    private ViewPager mViewPager;
    private Banner mBanner;
    private Adapter mAdapter;
    private Handler mHandler;

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
    }

    public void setBanner(Banner banner) {
        this.mBanner = banner;
        List<Banner.Data> data = mBanner.getData();
        if (data != null) {
            mAdapter.setData(data);
            mAdapter.notifyDataSetChanged();
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
            return data == null ? 0 : data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = mList.get(position);
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
            // super.destroyItem(container, position, object);
            container.removeView(mList.get(position));
        }

        public void setData(List<Banner.Data> data) {
            this.data = data;
            if (mList != null) {
                mList.clear();
            } else {
                mList = new ArrayList<>();
            }
            if (this.data != null) {
                for (int i = 0; i < this.data.size(); i++) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setTag(this.data.get(i));
                    mList.add(imageView);
                }
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
