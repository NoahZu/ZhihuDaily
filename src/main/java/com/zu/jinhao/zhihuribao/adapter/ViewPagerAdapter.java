package com.zu.jinhao.zhihuribao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.model.LastNewsJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zujinhao on 15/8/18.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<LastNewsJson.TopStory> stories;
    private List<View> views;
    private Context context;

    public ViewPagerAdapter(Context context,List<LastNewsJson.TopStory> stories){
        this.context = context;
        this.stories = stories;
        views = new ArrayList<View>();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        for(int i = 0;i<stories.size();i++){
            View view = layoutInflater.inflate(R.layout.activity_view_pager,null);
            views.add(view);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return this.stories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        ImageView displayImage = (ImageView)view.findViewById(R.id.display_picture);
        TextView displayText = (TextView)view.findViewById(R.id.display_text);
        Picasso.with(context).load(stories.get(position).getImage()).into(displayImage);
        displayText.setText(stories.get(position).getTitle());
        container.addView(view);
        return views.get(position);
    }
}
