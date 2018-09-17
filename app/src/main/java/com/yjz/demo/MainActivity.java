package com.yjz.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yjz.banner.AutoBannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 * @author lizheng
 * created at 2018/9/17 下午4:33
 */

public class MainActivity extends AppCompatActivity {


    private List<String> mDatas;
    private Activity mActivity;
    private AutoBannerView autoBannerView1,autoBannerView2,autoBannerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;


        initData();

        autoBannerView1 = findViewById(R.id.autoBanner1);
        autoBannerView2 = findViewById(R.id.autoBanner2);
        autoBannerView3 = findViewById(R.id.autoBanner3);

        List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537183356767&di=154f287eee90e547c26530ddc0b2ab03&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01f16a59659533a8012193a3edf634.jpg%40900w_1l_2o_100sh.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537183391821&di=be6d11059e2df9ee8f4b4bd160821836&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b3365718b90532f8759bffee3016.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537183415961&di=64f026c23473ed4ed17e0634ed02610e&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01aace5959bbf2a8012193a3a67a28.jpg%402o.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537183443965&di=27fe0bceb4fe2841958332c01021dc65&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a9b458cf491fa801219c778f973d.jpg%401280w_1l_2o_100sh.jpg");
        autoBannerView1.setImageResources(images, new AutoBannerView.AutoBannerViewListener<String>() {
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

        autoBannerView2.setImageResources(images, new AutoBannerView.AutoBannerViewListener<String>() {
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

        autoBannerView3.setImageResources(images, new AutoBannerView.AutoBannerViewListener<String>() {
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
        autoBannerView1.stop();
        autoBannerView2.stop();
        autoBannerView3.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoBannerView1.start();
        autoBannerView2.start();
        autoBannerView3.start();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mDatas.add(i + "--->title");
        }
    }



}
