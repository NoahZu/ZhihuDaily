package com.zu.jinhao.zhihuribao.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.model.SubjectDailyJson;
import com.zu.jinhao.zhihuribao.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zujinhao on 15/8/17.
 */
public class ThemeListAdapter extends BaseAdapter {
    private static final String TAG = "ThemeListAdapter";
    private Context context;
    List<SubjectDailyJson.SubjectDaily> dailyTypes;

    public ThemeListAdapter(Context context, List<SubjectDailyJson.SubjectDaily> dailyTypes) {
        this.context = context;
        this.dailyTypes = dailyTypes;
    }

    @Override
    public int getCount() {
        return dailyTypes.size();
    }

    @Override
    public SubjectDailyJson.SubjectDaily getItem(int position) {
        return dailyTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null ){
            convertView= LayoutInflater.from(context).inflate(R.layout.left_menu_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.update(position);
        return convertView;
    }

    public void setData(List<SubjectDailyJson.SubjectDaily> subjectDailies) {
        this.dailyTypes = subjectDailies;
    }

    class ViewHolder{
        @Bind(R.id.type_item_name)
        TextView textView;
        @Bind(R.id.type_item_button)
        ImageButton imageButton;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
        public void update(int position){
            final int final_position = position;
            final SubjectDailyJson.SubjectDaily subjectDaily = dailyTypes.get(final_position);
            this.textView.setText(subjectDaily.getName());
            if (subjectDaily.isAdd()){
                this.imageButton.setBackground(context.getResources().getDrawable(R.mipmap.menu_arrow));
            }
            else {
                this.imageButton.setBackground(context.getResources().getDrawable(R.mipmap.menu_follow));
            }
            this.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!subjectDaily.isAdd()) {
                        view.setBackground(context.getResources().getDrawable(R.mipmap.menu_arrow));
                        subjectDaily.setIsAdd(true);
                        int currentIndex = dailyTypes.indexOf(subjectDaily);
                        int i;
                        for (i = currentIndex - 1; i >= 0; i--) {
                            SubjectDailyJson.SubjectDaily tempDaily = dailyTypes.get(i);
                            if(!tempDaily.isAdd())
                                dailyTypes.set(i + 1, tempDaily);
                            else
                                break;
                        }
                        dailyTypes.set(i+1, subjectDaily);
                        PreferenceUtil.addInSharePreference(context,subjectDaily);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
