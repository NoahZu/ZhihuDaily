package com.zu.jinhao.zhihuribao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.model.Story;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zujinhao on 15/8/27.
 * 该Adapter用于显示首页上的ListView
 */
public class HomePageDailyListAdapter extends BaseAdapter {
    private static final String TAG = "ThemeDailyListAdapter";
    private Context context;
    private List<Story> stories;
    public HomePageDailyListAdapter(Context context, List<Story> stories){
        this.context = context;
        this.stories = stories;
    }
    @Override
    public int getCount() {
        return this.stories.size();
    }
    @Override
    public Story getItem(int position) {
        return stories.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Story story =getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.theme_daily_item,null);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final ViewHolder viewholder = (ViewHolder)convertView.getTag();
        if (story.getType() == -1){
            viewholder.imageView.setVisibility(View.GONE);
            viewholder.titleText.setText(convert2date(story.getTitle()));
            convertView.setBackgroundColor(context.getResources().getColor(R.color.divider_back));
            return convertView;
        }
        convertView.setBackgroundColor(Color.WHITE);
        viewholder.titleText.setText(story.getTitle());
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
        TextView titleText;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    private String convert2date(String dateString){
        char[] array = dateString.toCharArray();
        Log.d(TAG,"array[4]"+array[4]);
        Log.d(TAG,"array[5]"+array[5]);
        Log.d(TAG,"array[6]"+array[6]);
        Log.d(TAG,"array[7]"+array[7]);
        return array[4]+""+array[5]+"月"+array[6]+""+array[7]+"日";
    }
}
