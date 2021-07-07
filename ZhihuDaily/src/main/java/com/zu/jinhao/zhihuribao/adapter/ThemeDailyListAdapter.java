package com.zu.jinhao.zhihuribao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.model.SubjectDailyContentJson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zujinhao on 15/8/27.
 */
public class ThemeDailyListAdapter extends BaseAdapter {
    private static final String TAG = "ThemeDailyListAdapter";
    private Context context;
    private List<SubjectDailyContentJson.Story> stories;
    public ThemeDailyListAdapter(Context context,List<SubjectDailyContentJson.Story> stories){
        this.context = context;
        this.stories = stories;
    }
    @Override
    public int getCount() {
        return this.stories.size();
    }

    @Override
    public SubjectDailyContentJson.Story getItem(int position) {
        return stories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectDailyContentJson.Story story =getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.theme_daily_item,null);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final ViewHolder viewholder = (ViewHolder)convertView.getTag();
        viewholder.textView.setText(story.getTitle());
        if (story.getImages() == null){
            viewholder.imageView.setVisibility(View.GONE);
            return convertView;
        }
        Picasso.with(context).load(story.getImages().get(0)).into(viewholder.imageView);
        return convertView;
    }
    class ViewHolder{
        @Bind(R.id.daily_item_image)
        ImageView imageView;
        @Bind(R.id.daily_item_text)
        TextView textView;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
