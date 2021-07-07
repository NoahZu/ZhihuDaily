// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ThemeDailyFragment$$ViewBinder<T extends com.zu.jinhao.zhihuribao.fragment.ThemeDailyFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558511, "field 'dailyItemList' and method 'onItemClick'");
    target.dailyItemList = finder.castView(view, 2131558511, "field 'dailyItemList'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p0, p1, p2, p3);
        }
      });
  }

  @Override public void unbind(T target) {
    target.dailyItemList = null;
  }
}
