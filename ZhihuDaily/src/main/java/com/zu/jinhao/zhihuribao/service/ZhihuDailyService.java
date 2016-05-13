package com.zu.jinhao.zhihuribao.service;

import com.zu.jinhao.zhihuribao.model.LastNewsJson;
import com.zu.jinhao.zhihuribao.model.LauncherImageJson;
import com.zu.jinhao.zhihuribao.model.NewsContentJson;
import com.zu.jinhao.zhihuribao.model.PastNewsJson;
import com.zu.jinhao.zhihuribao.model.SubjectDailyContentJson;
import com.zu.jinhao.zhihuribao.model.SubjectDailyJson;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by zujinhao on 15/9/23.
 */
public interface ZhihuDailyService {

    //启动图片
    @GET("start-image/1080*1776")
    Observable<LauncherImageJson> getlauncherImage();

    //最新消息
    @GET("news/latest")
    Observable<LastNewsJson> getLasetNews();

    //消息内容
    @GET("news/{newsId}")
    Observable<NewsContentJson> getNewsContent(@Path("newsId") String newsId);

    //过往消息
    @GET("news/before/{date}")
    Observable<PastNewsJson> getPastNews(@Path("date") String date);

    //主题日报列表
    @GET("themes")
    Observable<SubjectDailyJson> getSubjectDailyJson();

    //主题日报内容
    @GET("theme/{dailyId}")
    Observable<SubjectDailyContentJson> getSubjectDailyContentJson(@Path("dailyId") String dailyId);

    //日报内容
    @GET("news/{newsId}")
    Observable<NewsContentJson> getStory(@Path("newsId") String newsId);

}
