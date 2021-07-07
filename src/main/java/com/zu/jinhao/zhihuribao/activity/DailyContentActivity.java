package com.zu.jinhao.zhihuribao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

import com.zu.jinhao.zhihuribao.service.ZhihuDailyService;
import com.zu.jinhao.zhihuribao.util.RetrofitUtil;
import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.model.NewsContentJson;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class DailyContentActivity extends ActionBarActivity {
    private static final String TAG = "DailyContentActivity";
    @Bind(R.id.theme_daily_content_id)
    WebView webView;
    @Bind(R.id.daily_content_toolbar)
    Toolbar toolbar;

    public static final  String URL_ID = "url_id";
    private int id;
    public static void actionStart(Context context,int id){
        Intent intent = new Intent(context, DailyContentActivity.class);
        intent.putExtra(URL_ID, id);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_daily_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra(URL_ID,0);
        iniToolbar();//做一些Toolbar的设置
        getWeb();//获取url并显示在webview上
    }
    private void getWeb() {
        ZhihuDailyService zhihuDailyServiceCall = RetrofitUtil.getZhihuDailyService();
        Call<NewsContentJson> newsContentJsonCall = zhihuDailyServiceCall.getNewsContent("" + id);
        newsContentJsonCall.enqueue(new Callback<NewsContentJson>() {
            @Override
            public void onResponse(Response<NewsContentJson> response) {
                webView.loadUrl(response.body().getShare_url());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private void iniToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_theme_daily_content, menu);
        return true;
    }
}
