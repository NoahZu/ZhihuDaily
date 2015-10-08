package com.zu.jinhao.zhihuribao.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zu.jinhao.zhihuribao.util.RetrofitUtil;
import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.activity.DailyContentActivity;
import com.zu.jinhao.zhihuribao.adapter.ThemeDailyListAdapter;
import com.zu.jinhao.zhihuribao.model.SubjectDailyContentJson;
import com.zu.jinhao.zhihuribao.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by zujinhao on 15/8/26.
 */
public class ThemeDailyFragment extends Fragment {
    private static final String TAG = "ThemeDailyFragment";
    private View view;
    private View headerView;
    private View emptyView;
    private ImageView displayImage;
    private TextView displayText;
    @Bind(R.id.theme_daily_item_list) ListView dailyItemList;
    private PtrFrameLayout ptrFrameLayout;
    private int id;//当前主题日报的id
    private SubjectDailyContentJson subjectDailyContentJson;
    private List<SubjectDailyContentJson.Story> stories;
    private ThemeDailyListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_theme_daily, null);
        ButterKnife.bind(this, view);
        initViews();
        initPtrFrameLayout();
        initValues();
        return this.view;
    }

    private void initValues() {
        stories = new ArrayList<SubjectDailyContentJson.Story>();
        adapter = new ThemeDailyListAdapter(getActivity(), stories);
        dailyItemList.setAdapter(adapter);
        setViewValues(id);
    }

    private void initViews() {
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_view_pager, null);
        emptyView = this.view.findViewById(R.id.error_view);
        dailyItemList.setEmptyView(emptyView);
        ptrFrameLayout = (PtrFrameLayout)this.view.findViewById(R.id.theme_ptr_layout);
        displayImage = (ImageView)headerView.findViewById(R.id.display_picture);
        displayText = (TextView)headerView.findViewById(R.id.display_text);
    }
    @OnItemClick(R.id.theme_daily_item_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            //position==0代表点击了headerView，在这里不作处理
            SubjectDailyContentJson.Story story = subjectDailyContentJson.getStories().get(position - 1);
            DailyContentActivity.actionStart(getActivity(), story.getId());
        }
    }
    private void initPtrFrameLayout() {
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
        ptrFrameLayout.setPinContent(true);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, dailyItemList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                Log.d(TAG, "====onRefreshBegin id:" + id);
                setViewValues(id);
                ptrFrameLayout.refreshComplete();
            }
        });
    }
    private void setViewValues(int id) {
        if(Util.isNetworkConnected(getActivity())){
            if(dailyItemList.getHeaderViewsCount() == 0){
                dailyItemList.addHeaderView(headerView);
            }
            RetrofitUtil.getZhihuDailyService().getSubjectDailyContentJson(id + "")
            .enqueue(new Callback<SubjectDailyContentJson>() {
                @Override
                public void onResponse(Response<SubjectDailyContentJson> response) {
                    subjectDailyContentJson = response.body();
                    setDailyContent2View();
                }
                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    private void setDailyContent2View() {
        stories.clear();
        if (subjectDailyContentJson != null ){
            Log.d(TAG,"===set===");
            Picasso.with(getActivity()).load(subjectDailyContentJson.getBackground()).into(displayImage);
            displayText.setText(subjectDailyContentJson.getDescription());
            stories.addAll(subjectDailyContentJson.getStories());
        }
        adapter.notifyDataSetChanged();
    }

    public void setId(int id) {
        this.id = id;
        Log.d(TAG, "===set id:" + id);
    }
    public void updateValues(int id){
        dailyItemList.removeHeaderView(headerView);
        setViewValues(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewValues(this.id);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(view);
    }
}
