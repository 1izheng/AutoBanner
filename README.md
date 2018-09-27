# AutoBanner

## 介绍
AutoBanner是一个简单实现自动无限轮播图的自定义banner控件。

## 属性

| **属性名称** | **描述** | **类型** | **默认值** | **值** |
| --- | ---| --- | --- | --- |
| app:indicator_type | 指示器类型 | enum | image | image / number |
| app:delay_time | 轮播时间间隔 | int | 3000 | ms |
| app:auto_play | 是否开启自动轮播 | boolean | true | true / false |
| app:indicator_gravity | 指示器位置 | enum | center | left / center / right |
| app:indicator_margin | 图片指示器间距 | dimension | 4 | dp |
| app:indicator_size | 指示器图片大小 | dimension | 8 | dp |
| app:indicator_drawable_selected | 指示器选中图片 | reference | 黑点子 | - |
| app:indicator_drawable_unselected | 指示器未选中图片 | reference | 白点子 | - |
| app:indicator_layout_height | 指示器父布局高度 | dimension | 36 | dp |
| app:indicator_layout_background | 指示器父布局背景 | reference/color | transparent | - |

## 方法

| **方法名称** | **说明** | **参数** |
| --- | ---| --- |
| setAutoPlay(boolean autoPlay) | 设置是否自动轮播 | 默认true |
| setDelayTime(int time) | 设置轮播图片间隔 | 默认3000 |
| setIndicatorGravity(int gravity) | 指示器gravity | 0:left 1:center 2:right |
| setImageResources(List<String> imageUrlList, AutoBannerViewListener lis) | 填充图片数据，调用此方法会自动调用start() | --- |
| start() | 开始轮播 | --- |
| stop() | 停止轮播 | --- |

效果如下图：

![](./pic/banner1.jpg)
![](./pic/banner2.jpg)
![](./pic/banner3.jpg)

## 使用方法
1.Gradle中添加依赖

```
dependencies{
    compile 'com.1izheng:AutoBanner:1.0'
}
```
2.添加权限 AndroidManifest.xml (显示网络图片需要Internet)

```
<uses-permission android:name="android.permission.INTERNET" />
```

3.布局文件中添加

```
    <com.jz.autobanner.AutoBannerView
            android:id="@+id/autoBanner"
            android:layout_width="match_parent"
            android:layout_height="自己定"
            />
```

4.in Activity or Fragment

```

        autoBannerView = findViewById(R.id.autoBanner);
        List<String> images = new ArrayList<>();
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frslibvijrj30k80q678q.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frrifts8l5j30j60ojq6u.jpg");
        images.add("http://ww1.sinaimg.cn/large/0065oQSqly1frjd77dt8zj30k80q2aga.jpg");
        autoBannerView.setImageResources(images, new AutoBannerView.AutoBannerViewListener<String>() {
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


        //可选
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


```



over~ 需要扩展的可以下载自己扩展，类很简单~


## License



     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.



