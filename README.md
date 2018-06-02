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



效果如下图：

![](./pictures/banner1.jpg)
![](./pictures/banner2.jpg)
![](./pictures/banner3.jpg)

## 使用方法



