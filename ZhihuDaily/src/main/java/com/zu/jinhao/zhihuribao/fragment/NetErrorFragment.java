package com.zu.jinhao.zhihuribao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zu.jinhao.zhihuribao.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zujinhao on 15/9/9.
 */
public class NetErrorFragment extends Fragment {
    private View view;
    @Bind(R.id.try_again) Button tryAgain;
    private ToDoTryAgainListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_neterror,null);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.try_again)
    public void tryAgain(){
        listener.todo();
    }
    public interface ToDoTryAgainListener{
        void todo();
    }
    public void setToDoListener(ToDoTryAgainListener listener){
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(view);
    }
}
