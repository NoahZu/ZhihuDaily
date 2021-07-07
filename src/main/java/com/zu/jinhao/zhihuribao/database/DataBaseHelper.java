package com.zu.jinhao.zhihuribao.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zu.jinhao.zhihuribao.model.SubjectDailyJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zujinhao on 15/9/9.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHelper";
    private Context context;
    private static final String CREATE_TABLE = "create table daily("+
            "id integer primary key autoincrement"+
            ",color integer"+
            ",thumbnail text"+
            ",description text"+
            ",name text"+
            ",is_add text"+
            ")";
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("sql", CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertDaily(SubjectDailyJson.SubjectDaily subjectDaily){
        String sql = "insert into daily values (" +
                subjectDaily.getId()+"," +
                subjectDaily.getColor()+",'"+
                subjectDaily.getThumbnail()+"','"+
                subjectDaily.getDescription()+"','"+
                subjectDaily.getName()+"','"+
                subjectDaily.isAdd()+"'"+
                ");";
        Log.d(TAG,"insert sql:"+sql);
        getWritableDatabase().execSQL(sql);
    }
    public List<SubjectDailyJson.SubjectDaily> queryAll(){
        Cursor cursor =  getWritableDatabase().rawQuery("select * from daily;",null);
        List<SubjectDailyJson.SubjectDaily> subjectDalies = new ArrayList<SubjectDailyJson.SubjectDaily>();
        while (cursor.moveToNext()){
            SubjectDailyJson.SubjectDaily subjectDaily = new SubjectDailyJson.SubjectDaily(
                cursor.getInt(cursor.getColumnIndex("color")),
                    cursor.getString(cursor.getColumnIndex("thumbnail")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name"))
            );
            String isAdd = cursor.getString(cursor.getColumnIndex("is_add"));
            if (isAdd.equals("true")){
                subjectDaily.setIsAdd(true);
            }
            else subjectDaily.setIsAdd(false);
            subjectDalies.add(subjectDaily);
        }
        return subjectDalies;
    }

    public void clear(){
        String sql = "delete from daily";
        getWritableDatabase().execSQL(sql);
    }
}
