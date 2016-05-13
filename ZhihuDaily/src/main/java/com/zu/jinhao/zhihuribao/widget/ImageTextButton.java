package com.zu.jinhao.zhihuribao.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zu.jinhao.zhihuribao.R;

/**
 * Created by zujinhao on 15/8/25.
 */
public class ImageTextButton extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    private OnButtonClickListener listener;

    public ImageTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.image_text_button,this);
        this.imageView = (ImageView)view.findViewById(R.id.icon_image);
        this.textView = (TextView)view.findViewById(R.id.icon_text);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }

    public void setImageView(Drawable drawable){
        this.imageView.setImageDrawable(drawable);
    }
    public void setTextView(String text){
        this.textView.setText(text);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.listener = listener;
    }
    public interface OnButtonClickListener {
        void onClick(View view);
    }
}
