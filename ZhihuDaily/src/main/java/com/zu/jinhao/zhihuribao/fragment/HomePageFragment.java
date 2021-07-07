package com.zu.jinhao.zhihuribao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.activity.DailyContentActivity;
import com.zu.jinhao.zhihuribao.adapter.HomePageDailyListAdapter;
import com.zu.jinhao.zhihuribao.adapter.ViewPagerAdapter;
import com.zu.jinhao.zhihuribao.model.LastNewsJson;
import com.zu.jinhao.zhihuribao.model.PastNewsJson;
import com.zu.jinhao.zhihuribao.model.Story;
import com.zu.jinhao.zhihuribao.util.RetrofitUtil;
import com.zu.jinhao.zhihuribao.util.Util;
import com.zu.jinhao.zhihuribao.widget.DotWidget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zujinhao on 15/8/26.
 */
public class HomePageFragment extends Fragment {
    private static final String TAG = "HomePageFragment";
    private View headerView;
    private View view;
    private DotWidget dotWidget;
    private ViewPager displayDailyPager;
    @Bind(R.id.index_daily_item_list) ListView dailyListView;
    private List<Story> indexListStories;
    private HomePageDailyListAdapter adapter;
    private PtrFrameLayout ptrFrameLayout;
    private Calendar currentCalendar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCalendar = Calendar.getInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_index, null);
        ButterKnife.bind(this,view);
        initViews();//初始化View
        initPullToRefreshLayout();//初始化下拉刷新控件
        initListView();
        getLastNews();//获取最新日报
        return view;
    }
    private void initViews() {
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.index_daily_header, null);
        displayDailyPager = (ViewPager)this.headerView.findViewById(R.id.display_daily_pager);
        dotWidget = (DotWidget)this.headerView.findViewById(R.id.dot_widget);
        dailyListView.addHeaderView(headerView);
        ptrFrameLayout = (PtrFrameLayout)this.view.findViewById(R.id.index_ptr_layout);
    }
    private void initPullToRefreshLayout() {
        final MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, Util.dp2px(getActivity(), 15), 0, Util.dp2px(getActivity(), 10));
        header.setPtrFrameLayout(ptrFrameLayout);

        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh(false);
            }
        }, 100);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, dailyListView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                getLastNews();
                ptrFrameLayout.refreshComplete();
            }
        });
        ptrFrameLayout.setPinContent(true);
    }
    private void initListView() {
        indexListStories = new ArrayList<Story>();
        adapter = new HomePageDailyListAdapter(getActivity(),indexListStories);
        dailyListView.setAdapter(adapter);
        dailyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Story story = indexListStories.get(position - 1);
                if (story.getType() != -1) {
                    DailyContentActivity.actionStart(getActivity(), story.getId());
                }
            }
        });
        dailyListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getLastVisiblePosition() == view.getCount() - 1) {
                    getNewsByDate(getYesterday());
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }
    private void getLastNews() {
       RetrofitUtil.getZhihuDailyService().getLasetNews()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<LastNewsJson>() {
                   @Override
                   public void onCompleted() {
                       Toast.makeText(getActivity(),"获取数据完成",Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onError(Throwable e) {
                        Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onNext(LastNewsJson lastNewsJson) {
                       List<LastNewsJson.TopStory> stories = lastNewsJson.getTop_stories();
                       setTopStoriesToViewPager(stories);
                       setNewsToListView(lastNewsJson.getStories());
                   }
               });
    }

    private void setTopStoriesToViewPager(List<LastNewsJson.TopStory> stories) {
        displayDailyPager.setAdapter(new ViewPagerAdapter(getActivity(), stories));
    }
    private void setNewsToListView(final List<Story> stories) {
        indexListStories.clear();
        indexListStories.addAll(stories);
        adapter.notifyDataSetChanged();
    }
    private void addNewsToListView(final List<Story> stories,String date){
        Story dateStory = new Story();
        dateStory.setType(-1);
        dateStory.setTitle(date);
        indexListStories.add(dateStory);
        indexListStories.addAll(stories);
        adapter.notifyDataSetChanged();
    }
    private void getNewsByDate(final String date) {
                RetrofitUtil.getZhihuDailyService().getPastNews(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<PastNewsJson>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(PastNewsJson pastNewsJson) {
                                addNewsToListView(pastNewsJson.getStories(),date);
                            }
                        });

    }
    public String getYesterday(){
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        currentCalendar.add(Calendar.DATE, -1);
        Date date = currentCalendar.getTime();
        String dateString = sim.format(date);
        return dateString;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this.view);
    }
}
