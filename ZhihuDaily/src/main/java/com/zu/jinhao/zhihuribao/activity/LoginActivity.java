package com.zu.jinhao.zhihuribao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;
import com.zu.jinhao.zhihuribao.R;
import com.zu.jinhao.zhihuribao.widget.ImageTextButton;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends Activity {
    ImageTextButton loginViaSinaWeiboButton;
    ImageTextButton loginViaTencentWeiboButton;
    @Bind(R.id.login_back)
    ImageButton backImageButton;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }
    private void initViews() {
        Resources res = getResources();
        loginViaSinaWeiboButton = (ImageTextButton)findViewById(R.id.login_via_sina);
        loginViaSinaWeiboButton.setImageView(getResources().getDrawable(R.mipmap.account_sina));
        loginViaSinaWeiboButton.setTextView(res.getString(R.string.sina_weibo));
        loginViaTencentWeiboButton = (ImageTextButton)findViewById(R.id.login_via_tencent);
        loginViaTencentWeiboButton.setImageView(getResources().getDrawable(R.mipmap.account_tencent));
        loginViaTencentWeiboButton.setTextView(res.getString(R.string.tencent_weibo));
    }
    private void initEvents() {
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loginViaSinaWeiboButton.setOnButtonClickListener(new ImageTextButton.OnButtonClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_open), Toast.LENGTH_SHORT).show();
            }
        });
        loginViaTencentWeiboButton.setOnButtonClickListener(new ImageTextButton.OnButtonClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_open), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
