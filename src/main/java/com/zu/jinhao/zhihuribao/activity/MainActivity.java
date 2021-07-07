package com.zu.jinhao.zhihuribao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.zu.jinhao.zhihuribao.fragment.HomePageFragment;
import com.zu.jinhao.zhihuribao.fragment.ThemeDailyFragment;
import com.zu.jinhao.zhihuribao.model.SubjectDailyJson;
import com.zu.jinhao.zhihuribao.service.ZhihuDailyService;
import com.zu.jinhao.zhihuribao.util.Configuration;
import com.zu.jinhao.zhihuribao.util.PreferenceUtil;
import com.zu.jinhao.zhihuribao.util.RetrofitUtil;
import com.zu.jinhao.zhihuribao.widget.LoginButton;
import java.util.List;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String TAG_THEME_DAILY_FRAGMENT = "TAG_THEME_DAILY_FRAGMENT";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private View headerView;
    private ListView themeDailyKindsListView;
    private LoginButton loginButton;
    private LinearLayout leftMenuMyCollect;
    private LinearLayout leftMenuOfflineDownload;
    private LinearLayout leftMenuIndex;
    private HomePageFragment homePageFragment;
    private ThemeDailyFragment themeDailyFragment;
    private List<SubjectDailyJson.SubjectDaily> subjectDailies;
    private View currentPageView;
    private DataBaseHelper dbHelper;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVariable();
        initViews();
        createFragments();
        setUpToolbar();
        initLeftMenu();
        initThemeDailyListEvents();
        initViewEvents();
        setDefaultIndexPage();
    }
    private void initVariable() {
        dbHelper = new DataBaseHelper(this, Configuration.getDatabaseName(),null,1);
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
        themeDailyFragment = new ThemeDailyFragment();
        homePageFragment = new HomePageFragment();
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
        subjectDailies = PreferenceUtil.readUserPreference(getApplicationContext(), dbHelper.queryAll());
        if (subjectDailies == null || subjectDailies.size() ==0){
            Toast.makeText(this,getResources().getString(R.string.net_error),Toast.LENGTH_LONG).show();
            finish();
        }
        ThemeListAdapter adapter = new ThemeListAdapter(
                getApplicationContext(),subjectDailies
        );
        themeDailyKindsListView.setAdapter(adapter);

        ZhihuDailyService service = RetrofitUtil.getZhihuDailyService();
        Call<SubjectDailyJson> subjectDailyJsonCall =  service.getSubjectDailyJson();
        subjectDailyJsonCall.enqueue(new Callback<SubjectDailyJson>() {
            @Override
            public void onResponse(Response<SubjectDailyJson> response) {
                SubjectDailyJson subjectDailyJson = response.body();
                subjectDailies = PreferenceUtil.readUserPreference(getApplicationContext(), subjectDailyJson.getOthers());
                save2Database(subjectDailies);
                adapter.setData(subjectDailies);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void save2Database(final List<SubjectDailyJson.SubjectDaily> others) {
        if (dbHelper.queryAll() == null || dbHelper.queryAll().size() == 0){
            Observable.from(others)
                    .doOnNext((daily) -> dbHelper.insertDaily(daily))
                    .subscribe();
        }
    }
    private void initThemeDailyListEvents() {
        themeDailyKindsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch2ThemeDaily(view, position);
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, homePageFragment);
        transaction.commit();
        currentPageView = leftMenuIndex;
    }
    private void switch2ThemeDaily(final View view, int position){
        setBackground(view);
        SubjectDailyJson.SubjectDaily subjectDaily = subjectDailies.get(position - 1);
        toolbar.setTitle(subjectDaily.getName());
        themeDailyFragment.setId(subjectDaily.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, themeDailyFragment, TAG_THEME_DAILY_FRAGMENT)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
        themeDailyFragment.updateValues(subjectDaily.getId());
    }

    private void setBackground(View view) {
        currentPageView.setBackgroundColor(Color.WHITE);
        currentPageView = view;
        currentPageView.setBackgroundColor(getResources().getColor(R.color.LIGHT_GRAY));
        drawerLayout.closeDrawer(themeDailyKindsListView);
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
                switchHomeDaily(leftMenuIndex);
                break;
        }
    }

    private void switchHomeDaily(View view) {
        setBackground(view);
        toolbar.setTitle(getResources().getString(R.string.home_page));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, homePageFragment).commit();
    }
}
