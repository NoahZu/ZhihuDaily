package com.zu.jinhao.zhihuribao.util;

import com.zu.jinhao.zhihuribao.service.ZhihuDailyService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by zujinhao on 15/9/23.
 */
public class RetrofitUtil {
    private static Retrofit retrofit;
    public static ZhihuDailyService zhihuDailyService;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zhihuDailyService = retrofit.create(ZhihuDailyService.class);
    }

    public static ZhihuDailyService getZhihuDailyService() {
        return zhihuDailyService;
    }
}
