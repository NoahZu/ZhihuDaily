package com.zu.jinhao.zhihuribao.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zu.jinhao.zhihuribao.model.SubjectDailyJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zujinhao on 15/9/8.
 */
public class PreferenceUtil {
    private static final String dailyOfUserPreference = "dailyOfUserPreference";
    public static List<SubjectDailyJson.SubjectDaily> readUserPreference(Context context,List<SubjectDailyJson.SubjectDaily> others) {
        SharedPreferences pref = context.getSharedPreferences(dailyOfUserPreference, context.MODE_PRIVATE);
        List<SubjectDailyJson.SubjectDaily> addedList = new ArrayList<SubjectDailyJson.SubjectDaily>();
        List<SubjectDailyJson.SubjectDaily> notAddList = new ArrayList<SubjectDailyJson.SubjectDaily>();
        for (int i = 0; i < others.size(); i++) {
            SubjectDailyJson.SubjectDaily subjectDaily = others.get(i);
            boolean isAdded = pref.getBoolean(subjectDaily.getName(), false);
            if (isAdded)
            {
                subjectDaily.setIsAdd(true);
                addedList.add(subjectDaily);
            }
            else
                notAddList.add(subjectDaily);
        }
        addedList.addAll(notAddList);
        return addedList;
    }
    public static void addInSharePreference(Context context,SubjectDailyJson.SubjectDaily subjectDaily) {
        SharedPreferences.Editor editor = context.getSharedPreferences(dailyOfUserPreference,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(subjectDaily.getName(),true);
        editor.commit();
    }
}
