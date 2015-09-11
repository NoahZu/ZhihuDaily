package com.zu.jinhao.zhihuribao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.util.StringFromHttpLoader;
import com.zu.jinhao.zhihuribao.model.LauncherImageJson;
import com.zu.jinhao.zhihuribao.util.Url;
import com.zu.jinhao.zhihuribao.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LaunchActivity extends Activity {

    private static final String TAG = "LaunchActivity";
    @Bind(R.id.launcher_image)
    ImageView launcherImageView;
    @Bind(R.id.launcher_image_author)
    TextView launcherImageAuthorText;

    private LauncherImageJson launcherImageJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        getLaunchInfoJson();//获取启动信息的json
        setLaunchAnimation();
    }

    private void getLaunchInfoJson(){
        if(Util.isNetworkConnected(this)){
            StringFromHttpLoader stringFromHttpLoader = new StringFromHttpLoader();
            stringFromHttpLoader.setGetHttpStringListener(
                    new StringFromHttpLoader.GetHttpStringListener() {
                @Override
                public void onGetHttpString(String jsonString) {
                    launcherImageJson = new Gson().fromJson(jsonString, LauncherImageJson.class);
                    initLauncherInfo();
                }
            });
            stringFromHttpLoader.execute(Url.LAUNCHER_PAGE_URL);
            return;
        }
        launcherImageJson = new Gson().fromJson(Url.firstLauncherJson, LauncherImageJson.class);
        initLauncherInfo();
    }
    private void setLaunchAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.launcher_animation);
        launcherImageView.setAnimation(animation);
        animation.setFillAfter(true);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                enterMainActivity();
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void initLauncherInfo() {
        Log.d(TAG, "===image" + launcherImageJson.getImg() + "text" + launcherImageJson.getText());
        Picasso.with(this).load(launcherImageJson.getImg()).error(R.mipmap.picasso).into(launcherImageView);
        launcherImageAuthorText.setText(launcherImageJson.getText());
    }
    private void enterMainActivity() {
        MainActivity.actionStart(this);
    }
}
