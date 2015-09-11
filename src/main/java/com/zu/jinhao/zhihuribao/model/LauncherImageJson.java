package com.zu.jinhao.zhihuribao.model;

/**
 * Created by zujinhao on 15/8/18.
 */
/**
     URL: http://news-at.zhihu.com/api/4/start-image/1080*1776
     start-image 后为图像分辨率，接受如下格式

     320*432
     480*728
     720*1184
     1080*1776
*/
/**
     分析：
     text : 供显示的图片版权信息
     img : 图像的 URL
*/
public class LauncherImageJson {
    String text;
    String img;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
