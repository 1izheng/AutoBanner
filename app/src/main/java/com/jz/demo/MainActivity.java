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
        autoBannerView.setAutoPlay(false);

        List<String> images = new ArrayList<>();
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frslibvijrj30k80q678q.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frrifts8l5j30j60ojq6u.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frjd77dt8zj30k80q2aga.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527915111732&di=ae478dd0be933d0be66088844cd073d6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0170215739312e6ac72580ed89d7f7.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527915146245&di=2e231ab2c59c2814db76aa12ce1f629c&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01801e55c36e296ac7255808749841.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528509895&di=541a36acf742b19e551d9f393c0bdfef&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c54d582930eba84a0e282b53c730.png");
        autoBannerView.setImageResources(images, new AutoBannerView.AutoBannerViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                Glide.with(mActivity).load(imageURL).into(imageView);
            }

            @Override
            public void onImageClick(int position, View imageView) {

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
