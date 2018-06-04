package com.jz.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jz.autobanner.AutoBannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> mDatas;
    private Activity mActivity;
    private AutoBannerView autoBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("AutoBannerView");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);

        initData();

        RecyclerView recyclerView = findViewById(R.id.recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(new BaseAdapter());

        autoBannerView = findViewById(R.id.autoBanner);
        List<String> images = new ArrayList<>();
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frslibvijrj30k80q678q.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frrifts8l5j30j60ojq6u.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frjd77dt8zj30k80q2aga.jpg");
        autoBannerView.setImageResources(images, new AutoBannerView.AutoBannerViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                //显示图片方法,图片框架自己选~ 这里使用的是Glide
                Glide.with(mActivity).load(imageURL).into(imageView);
            }

            @Override
            public void onImageClick(int position, View imageView) {
                //点击图片的事件...
                Toast.makeText(mActivity, "点击了位置" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoBannerView.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoBannerView.start();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mDatas.add(i + "--->title");
        }
    }


    class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.Vh> {


        @Override
        public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
            Vh holder = new Vh(LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(Vh holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class Vh extends RecyclerView.ViewHolder {
            TextView tv;

            public Vh(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
            }
        }
    }
}
