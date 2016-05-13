package com.zu.jinhao.zhihuribao.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.adapter.ThemeListAdapter;
import com.zu.jinhao.zhihuribao.database.DataBaseHelper;
import com.zu.jinhao.zhihuribao.fragment.NetErrorFragment;
import com.zu.jinhao.zhihuribao.service.ZhihuDailyService;
import com.zu.jinhao.zhihuribao.util.Configuration;
import com.zu.jinhao.zhihuribao.util.PreferenceUtil;
import com.zu.jinhao.zhihuribao.util.RetrofitUtil;
import com.zu.jinhao.zhihuribao.fragment.HomePageFragment;
import com.zu.jinhao.zhihuribao.fragment.ThemeDailyFragment;
import com.zu.jinhao.zhihuribao.model.SubjectDailyJson;
import com.zu.jinhao.zhihuribao.util.Util;
import com.zu.jinhao.zhihuribao.widget.LoginButton;

import java.util.List;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String TAG_THEME_DAILY_FRAGMENT = "TAG_THEME_DAILY_FRAGMENT";
    private static final String TAG_INDEX_FRAGMENT = "TAG_INDEX_FRAGMENT";
    private static final String TAG_ERROR_FRAGMENT = "ERROR_FRAGMENT";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private View headerView;
    private ListView themeDailyKindsListView;
    private LoginButton loginButton;
    private LinearLayout leftMenuMyCollect;
    private LinearLayout leftMenuOfflineDownload;
    private LinearLayout leftMenuIndex;
    private List<SubjectDailyJson.SubjectDaily> subjectDailies;//在这里边就记录了所有主题日报的信息
    private View currentPageView;//记录当前是哪一个页面
    private int currentPosition;//记录当前的position
    private HomePageFragment homePageFragment;//主页的Fragment
    private ThemeDailyFragment themeDailyFragment;//主题日报的Fragment
    private NetErrorFragment netErrorFragment;
    private String fragmentFlag;
    private DataBaseHelper dbHelper;
    private String tips;
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVariable();//初始化一些变量
        initViews();//初始化控件
        createFragments();//创建Fragment
        setUpToolbar();//设置Toolbar
        initLeftMenu();//初始化左侧菜单
        initThemeDailyListEvents();//初始化主题日报(ListView的点击事件)
        initViewEvents();//初始化其他控件的点击事件
        setDefaultIndexPage();//设置默认加载首页Fragment
    }
    private void initVariable() {
        dbHelper = new DataBaseHelper(this, Configuration.getDatabaseName(),null,1);
        tips = getResources().getString(R.string.net_error_yet);
    }
    private void initViews() {
        headerView = LayoutInflater.from(this).inflate(R.layout.left_menu_top, null);
        loginButton = (LoginButton) headerView.findViewById(R.id.left_menu_login_button);
        leftMenuMyCollect = (LinearLayout) headerView.findViewById(R.id.left_menu_my_collect);
        leftMenuOfflineDownload = (LinearLayout) headerView.findViewById(R.id.left_menu_offline_download);
        leftMenuIndex = (LinearLayout) headerView.findViewById(R.id.left_menu_index);
        themeDailyKindsListView = (ListView)findViewById(R.id.type_list);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

    }
    private void createFragments() {
        //提前创建三个Fragment，因为Fragment只有三个，所以没有必要在用到的时候创建
        homePageFragment = new HomePageFragment();
        themeDailyFragment = new ThemeDailyFragment();
        netErrorFragment = new NetErrorFragment();
    }
    private void setUpToolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(themeDailyKindsListView);
            }
        });
        toolbar.setTitle(getResources().getString(R.string.home_page));
        toolbar.setTitleTextColor(getResources().getColor(R.color.WHITE));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.message:
                        break;
                    case R.id.night_pattern:
                        break;
                    case R.id.setting_select:
                        break;
                }
                return true;
            }
        });
    }
    private void initLeftMenu() {
        themeDailyKindsListView.addHeaderView(headerView);
        if (Util.isNetworkConnected(this)){
            ZhihuDailyService service = RetrofitUtil.getZhihuDailyService();
            Call<SubjectDailyJson> subjectDailyJsonCall =  service.getSubjectDailyJson();
            subjectDailyJsonCall.enqueue(new Callback<SubjectDailyJson>() {
                @Override
                public void onResponse(Response<SubjectDailyJson> response) {
                    SubjectDailyJson subjectDailyJson = response.body();
                    subjectDailies = PreferenceUtil.readUserPreference(getApplicationContext(), subjectDailyJson.getOthers());
                    save2Database(subjectDailies);
                    ThemeListAdapter adapter = new ThemeListAdapter(
                            getApplicationContext(),
                            subjectDailies
                    );
                    themeDailyKindsListView.setAdapter(adapter);
                }
                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        else {
            subjectDailies = PreferenceUtil.readUserPreference(getApplicationContext(), dbHelper.queryAll());
            if (subjectDailies == null || subjectDailies.size() ==0){
                //无网络，还是第一次进入
                Toast.makeText(this,getResources().getString(R.string.net_error),Toast.LENGTH_LONG).show();
                finish();
            }
            ThemeListAdapter adapter = new ThemeListAdapter(
                    getApplicationContext(),subjectDailies
            );
            themeDailyKindsListView.setAdapter(adapter);
        }
    }
    private void save2Database(final List<SubjectDailyJson.SubjectDaily> others) {
        Log.d(TAG,"===save2Database enter===");
        if (dbHelper.queryAll() == null || dbHelper.queryAll().size() == 0){
            Log.d(TAG,"===save2Database not null===");
            Observable.create(
                    new Observable.OnSubscribe<List<SubjectDailyJson.SubjectDaily>>() {
                        @Override
                        public void call(Subscriber<? super List<SubjectDailyJson.SubjectDaily>> subscriber) {
                            subscriber.onNext(others);
                            subscriber.onCompleted();
                        }
                    }
            ).
                    flatMap(dailies -> Observable.from(dailies))
                    .doOnNext(daily -> dbHelper.insertDaily(daily))
                    .subscribe();
        }

    }
    private void initThemeDailyListEvents() {
        themeDailyKindsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                switch2ThemeDaily(view);
            }
        });
    }
    private void initViewEvents() {
        loginButton.setOnLoginButtonClickListener(new LoginButton.OnLoginButtonClick() {
            @Override
            public void onClick(View view) {
                LoginActivity.actionStart(MainActivity.this);
            }
        });
        leftMenuMyCollect.setOnClickListener(this);
        leftMenuOfflineDownload.setOnClickListener(this);
        leftMenuIndex.setOnClickListener(this);
    }
    private void setDefaultIndexPage() {
        if (Util.isNetworkConnected(this)){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, homePageFragment);
            transaction.commit();
            currentPageView = leftMenuIndex;
            fragmentFlag = TAG_INDEX_FRAGMENT;
        }
        else{
            netErrorFragment = new NetErrorFragment();
            netErrorFragment.setToDoListener(new NetErrorFragment.ToDoTryAgainListener() {
                @Override
                public void todo() {
                    if(Util.isNetworkConnected(getBaseContext())){
                        currentPosition = 0;
                        switch2ThemeDaily(leftMenuIndex);
                    }
                    else {
                        Toast.makeText(getBaseContext(),tips,Toast.LENGTH_SHORT).show();
                    }
                }
            });
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame,netErrorFragment).commit();
            currentPageView = leftMenuIndex;
            fragmentFlag = TAG_ERROR_FRAGMENT;
        }
    }
    private void switch2ThemeDaily(final View view){
        currentPageView.setBackgroundColor(Color.WHITE);
        currentPageView = view;
        currentPageView.setBackgroundColor(getResources().getColor(R.color.LIGHT_GRAY));
        drawerLayout.closeDrawer(themeDailyKindsListView);
        if (currentPosition==0) {
            //说明要切换到主页的Fragment
            toolbar.setTitle(getResources().getString(R.string.home_page));
            if (Util.isNetworkConnected(this)){
                //有网
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, homePageFragment).commit();
                fragmentFlag = TAG_INDEX_FRAGMENT;
            }
            else {
                //无网
                netErrorFragment.setToDoListener(new NetErrorFragment.ToDoTryAgainListener() {
                    @Override
                    public void todo() {
                        if (Util.isNetworkConnected(getBaseContext())) {
                            //点击了我已连接好网络，并且网络确实已经连好
                            switch2ThemeDaily(view);
                        }else{
                            Toast.makeText(getBaseContext(),tips,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if (fragmentFlag.equals(TAG_ERROR_FRAGMENT)) {
                    return;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,netErrorFragment).commit();
                fragmentFlag = TAG_ERROR_FRAGMENT;
            }
        }
        if (currentPosition>0) {
            //说明要切换到主题日报的Fragment
            SubjectDailyJson.SubjectDaily subjectDaily = subjectDailies.get(currentPosition - 1);
            toolbar.setTitle(subjectDaily.getName());
            if (Util.isNetworkConnected(this)){
                themeDailyFragment.setId(subjectDaily.getId());
                if (fragmentFlag.equals(TAG_THEME_DAILY_FRAGMENT)) {
                    //表示上一个是主题日报
                    themeDailyFragment.updateValues(subjectDaily.getId());
                }
                if (fragmentFlag.equals(TAG_INDEX_FRAGMENT) || fragmentFlag.equals(TAG_ERROR_FRAGMENT)) {
                    //表示上一个是首页或者错误页
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, themeDailyFragment, TAG_THEME_DAILY_FRAGMENT)
                            .commit();
                }
                fragmentFlag = TAG_THEME_DAILY_FRAGMENT;
            }
            else{
                netErrorFragment.setToDoListener(new NetErrorFragment.ToDoTryAgainListener() {
                    @Override
                    public void todo() {
                        if(Util.isNetworkConnected(getBaseContext())){
                            switch2ThemeDaily(view);
                        }
                        else {
                            Toast.makeText(getBaseContext(),tips,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if (fragmentFlag.equals(TAG_ERROR_FRAGMENT)) {
                    return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, netErrorFragment).commit();
                fragmentFlag = TAG_ERROR_FRAGMENT;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_menu_my_collect:
                LoginActivity.actionStart(this);
                break;
            case R.id.left_menu_offline_download:

                break;
            case R.id.left_menu_index:
                currentPosition = 0;
                switch2ThemeDaily(leftMenuIndex);
                break;
        }
    }
}
