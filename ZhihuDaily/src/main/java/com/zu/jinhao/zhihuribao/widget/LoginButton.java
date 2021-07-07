package com.zu.jinhao.zhihuribao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zu.jinhao.zhihuribao.R;

/**
 * Created by zujinhao on 15/8/17.
 */
public class LoginButton extends LinearLayout {
    View view;
    OnLoginButtonClick onLoginButtonClick;


    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.login_button,this);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClick.onClick(v);
            }
        });
    }

    public void setOnLoginButtonClickListener(OnLoginButtonClick onLoginButtonClick){
        this.onLoginButtonClick = onLoginButtonClick;
    }
    public interface OnLoginButtonClick{
        void onClick(View view);
    }

}
