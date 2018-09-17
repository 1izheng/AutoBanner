package com.yjz.banner;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 无限循环banner,支持左右滑动,基于ViewPager实现
 *
 * @author lizheng
 *         created at 2018/9/17 上午11:55
 */
public class AutoBannerView<T> extends LinearLayout {

    private Context mContext;

    /**
     * 指示器类型 默认 图片指示器 0
     */
    private int indicatorType = 0;

    /**
     * gravity  默认居中 1
     */
    private int indicatorGravity = 1;

    /**
     * 是否自动轮播,默认true
     */
    private boolean autoPlay = true;

    /**
     * 轮播间隔 默认3秒
     */
    private int delayTime = 3000;

    /**
     * 图片指示器尺寸
     */
    private int indicatorSize = 8;
    /**
     * 图片指示器margin
     */
    private int indicatorMargin = 4;
    /**
     * 数字指示器textsize
     */
    private int numIndicatorTextSize = 15;
    /**
     * 数字指示器textColor
     */
    private int numIndicatorTextColor;
    /**
     * 选中图片指示器样式
     */
    private int indicatorSelectedResId = R.drawable.indicator_selected;
    /**
     * 未选中图片指示器样式
     */
    private int indicatorUnSelectedResId = R.drawable.indicator_unselected;
    /**
     * 指示器父布局高度
     */
    private int indicatorLayoutHeight = 36;
    /**
     * 指示器父布局背景 默认透明
     */
    private int indicatorLayoutBackground;

    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;

    /**
     * 滚动图片视图适配
     */
    private AutoBannerAdapter mBannerAdapter;

    /**
     * 图片轮播指示器控件
     */
    private LinearLayout mGroup;
    private LinearLayout tvGroup;

    /**
     * 文字指示器
     */
    private TextView tvIndicator;

    /**
     * 图片总数
     */
    private int imageCount;

    /**
     * 图片轮播指示个图
     */
    private ImageView mImageView = null;

    /**
     * 滚动图片指示视图列表
     */
    private ImageView[] mImageViews = null;

    /**
     * 最大值
     */
    private int MaxValue = 1000;
    /**
     * 默认值
     */
    private int DefaultValue = 100;

    /**
     * 是否停止循环
     */
    private boolean isStop;

    /**
     * @param context
     */
    public AutoBannerView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public AutoBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoBannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initAttrs(context, attrs);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoBanner);
        indicatorType = typedArray.getInt(R.styleable.AutoBanner_indicator_type, 0);
        indicatorGravity = typedArray.getInt(R.styleable.AutoBanner_indicator_gravity, 1);
        delayTime = typedArray.getInt(R.styleable.AutoBanner_delay_time, 3000);
        autoPlay = typedArray.getBoolean(R.styleable.AutoBanner_auto_play, true);
        indicatorSize = typedArray.getDimensionPixelSize(R.styleable.AutoBanner_indicator_size, dp2px(8));
        indicatorMargin = typedArray.getDimensionPixelSize(R.styleable.AutoBanner_indicator_margin, dp2px(4));
        indicatorSelectedResId = typedArray.getResourceId(R.styleable.AutoBanner_indicator_drawable_selected, R.drawable.indicator_selected);
        indicatorUnSelectedResId = typedArray.getResourceId(R.styleable.AutoBanner_indicator_drawable_unselected, R.drawable.indicator_unselected);
        numIndicatorTextSize = typedArray.getDimensionPixelSize(R.styleable.AutoBanner_num_indicator_textsize, 15);
        indicatorLayoutHeight = typedArray.getDimensionPixelSize(R.styleable.AutoBanner_indicator_layout_height, dp2px(36));

        numIndicatorTextColor = typedArray.getColor(R.styleable.AutoBanner_num_indicator_textcolor, ContextCompat.getColor(context, android.R.color.white));
        indicatorLayoutBackground = typedArray.getColor(R.styleable.AutoBanner_indicator_layout_background,ContextCompat.getColor(context, android.R.color.transparent));

        typedArray.recycle();
    }


    private void init(Context context) {
        View.inflate(context, R.layout.ad_banner_view, this);
        mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
        mAdvPager.addOnPageChangeListener(new GuidePageChangeListener());
        mAdvPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageTimerTask();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        mGroup = (LinearLayout) findViewById(R.id.ly_img_indicator);
        tvGroup = (LinearLayout) findViewById(R.id.ly_num_indicator);
        tvIndicator = (TextView) findViewById(R.id.tv_indicator);

        //指示器类型
        if (indicatorType == 0) {
            showImgIndicator();
        } else {
            showNumIndicator();
        }
    }

    /**
     * 装填图片数据
     *
     * @param imageUrlList
     * @param autoBannerViewListener
     */
    public void setImageResources(List<T> imageUrlList, AutoBannerViewListener autoBannerViewListener) {

        if (imageUrlList != null && imageUrlList.size() > 0) {
            this.setVisibility(View.VISIBLE);
        } else {
            this.setVisibility(View.GONE);
            return;
        }

        if (imageUrlList.size() == 1) {
            MaxValue = 1;
        }

        // 清除
        mGroup.removeAllViews();
        // 图片广告数量
        imageCount = imageUrlList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(mContext);
            LayoutParams params = new LayoutParams(indicatorSize, indicatorSize);
            params.leftMargin = indicatorMargin;
            mImageView.setLayoutParams(params);

            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i].setBackgroundResource(R.drawable.indicator_selected);
            } else {
                mImageViews[i].setBackgroundResource(R.drawable.indicator_unselected);
            }
            mGroup.addView(mImageViews[i]);
        }

        mBannerAdapter = new AutoBannerAdapter(mContext, imageUrlList, autoBannerViewListener);
        mAdvPager.setAdapter(mBannerAdapter);

        mAdvPager.setCurrentItem(MaxValue / 2 - (MaxValue / 2) % mImageViews.length);
        startImageTimerTask();
    }

    /**
     * 图片轮播(手动控制自动轮播与否，便于资源控制）
     */
    public void start() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void stop() {
        stopImageTimerTask();
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
        if (!autoPlay) {
            return;
        }
        stopImageTimerTask();
        if (mImageViews != null && mImageViews.length > 1) {
            // 图片滚动
            mHandler.postDelayed(mImageTimerTask, delayTime);
        }
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (mImageViews != null) {
                int position = mAdvPager.getCurrentItem() + 1;
                if (position >= MaxValue - DefaultValue) {
                    position = MaxValue / 2 - (MaxValue / 2) % mImageViews.length;
                }
                mAdvPager.setCurrentItem(position);
                //当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环
                if (!isStop) {
                    mHandler.postDelayed(mImageTimerTask, delayTime);
                }

            }
        }
    };

    /**
     * 是否自动播放
     *
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    /**
     * 显示图形indicator
     */
    private void showImgIndicator() {
        tvGroup.setVisibility(View.GONE);
        mGroup.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams lps = (RelativeLayout.LayoutParams) mGroup.getLayoutParams();
        lps.width = LayoutParams.MATCH_PARENT;
        lps.height = indicatorLayoutHeight;
        mGroup.setLayoutParams(lps);
        mGroup.setBackgroundColor(indicatorLayoutBackground);
        if (indicatorGravity == 0) {
            mGroup.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else if (indicatorGravity == 1) {
            mGroup.setGravity(Gravity.CENTER);
        } else {
            mGroup.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }

    }

    /**
     * 显示数字indicator
     */
    private void showNumIndicator() {
        tvGroup.setVisibility(View.VISIBLE);
        mGroup.setVisibility(GONE);
        tvIndicator.setTextColor(numIndicatorTextColor);
        tvIndicator.setTextSize(TypedValue.COMPLEX_UNIT_DIP, numIndicatorTextSize);

        RelativeLayout.LayoutParams lps = (RelativeLayout.LayoutParams) tvGroup.getLayoutParams();
        lps.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lps.height = indicatorLayoutHeight;
        tvGroup.setLayoutParams(lps);
        tvGroup.setBackgroundColor(indicatorLayoutBackground);

        if (indicatorGravity == 0) {
            tvIndicator.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else if (indicatorGravity == 1) {
            tvIndicator.setGravity(Gravity.CENTER);
        } else {
            tvIndicator.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
    }

    /**
     * 轮播时间间隔
     *
     * @param time
     */
    public void setDelayTime(int time) {
        this.delayTime = time;
    }

    /**
     * 设置gravity
     *
     * @param gravity 0:left 1:center 2:right
     */
    public void setIndicatorGravity(int gravity) {
        this.indicatorGravity = gravity;
    }

    /**
     * 轮播图片监听
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                startImageTimerTask();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            index = index % mImageViews.length;
            // 设置图片滚动指示器背
            mImageViews[index].setBackgroundResource(indicatorUnSelectedResId);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i].setBackgroundResource(indicatorSelectedResId);
                }
            }
            tvIndicator.setText(index + 1 + "/" + imageCount);
        }
    }

    private class AutoBannerAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private List<ImageView> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private List<T> mAdList = new ArrayList<>();

        /**
         * 广告图片点击监听
         */
        private AutoBannerViewListener mAutoBannerViewListener;

        private Context mContext;

        public AutoBannerAdapter(Context context, List<T> adList, AutoBannerViewListener autoBannerViewListener) {
            this.mContext = context;
            this.mAdList = adList;
            mAutoBannerViewListener = autoBannerViewListener;
            mImageViewCacheList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return MaxValue;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            T imageUrl = mAdList.get(position % mAdList.size());
            ImageView imageView;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = position % mAdList.size();
                    mAutoBannerViewListener.onImageClick(index, v);
                }
            });

            container.addView(imageView);
            mAutoBannerViewListener.displayImage(imageUrl, imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            mAdvPager.removeView(view);
            mImageViewCacheList.add(view);

        }

    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public interface AutoBannerViewListener<T> {
        /**
         * 加载图片资源
         *
         * @param imageUrl
         * @param imageView
         */
        void displayImage(T imageUrl, ImageView imageView);

        /**
         * 单击图片事件
         *
         * @param position
         * @param imageView
         */
        void onImageClick(int position, View imageView);
    }


    private int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}