package com.zu.jinhao.zhihuribao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zu.jinhao.zhihuribao.R;

/**
 * Created by zujinhao on 15/8/18.
 */
public class DotWidget extends LinearLayout {

    View[] dotViews;
    int currentPos;
    View currentDotView;
    int[] dotViewIds = new int[]{
            R.id.dot1,
            R.id.dot2,
            R.id.dot3,
            R.id.dot4,
            R.id.dot5
    };
    private static  final int DOT_COUNT = 5;
    private static  final int DOT_START_INDEX = 0;
    public DotWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDotViews();
    }

    private void initDotViews() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.dot_widget,this);
        dotViews = new View[DOT_COUNT];
        for(int dotIndex = 0;dotIndex<DOT_COUNT;dotIndex++){
            dotViews[dotIndex] = view.findViewById(dotViewIds[dotIndex]);
        }
        currentPos = DOT_START_INDEX;
        currentDotView = dotViews[currentPos];
        currentDotView.setSelected(true);
    }
//
//    public void nextDot(){
//        currentDotView.setSelected(false);
//        ++currentPos;
//        if (currentPos >=DOT_COUNT){
//            currentPos = DOT_START_INDEX;
//        }
//        currentDotView = dotViews[currentPos];
//        currentDotView.setSelected(true);
//    }
//    public void previousDot(){
//        currentDotView.setSelected(false);
//        --currentPos;
//        if (currentPos <DOT_START_INDEX){
//            currentPos = DOT_START_INDEX;
//        }
//        currentDotView = dotViews[currentPos];
//        currentDotView.setSelected(true);
//    }
//    public void toFirstDot(){
//        currentDotView.setSelected(false);
//        currentPos = DOT_START_INDEX;
//        currentDotView = dotViews[currentPos];
//        currentDotView.setSelected(true);
//    }
//    public void toLastDot(){
//        currentDotView.setSelected(false);
//        currentPos = DOT_COUNT-1;
//        currentDotView = dotViews[currentPos];
//        currentDotView.setSelected(true);
//    }
    public void toDot(int index){
        currentDotView.setSelected(false);
        currentPos = index;
        currentDotView = dotViews[currentPos];
        currentDotView.setSelected(true);
    }
}
