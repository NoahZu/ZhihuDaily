package com.zu.jinhao.zhihuribao.model;

import java.util.List;

/**
     5.URL: http://news.at.zhihu.com/api/4/news/before/20131119
     若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
     知乎日报的生日为 2013 年 5 月 19 日，若 before 后数字小于 20130520 ，只会接收到空消息
     输入的今日之后的日期仍然获得今日内容，但是格式不同于最新消息的 JSON 格式
 */
/**
 * 分析：
     date : 日期
     stories : 当日新闻
     title : 新闻标题
     images : 图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
     ga_prefix : 供 Google Analytics 使用
     type : 作用未知
     id : url 与 share_url 中最后的数字（应为内容的 id）
     multipic : 消息是否包含多张图片（仅出现在包含多图的新闻中）
     top_stories : 界面顶部 ViewPager 滚动显示的显示内容（子项格式同上）
 * */
public class PastNewsJson {
    String date;
    List<Story> stories;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
