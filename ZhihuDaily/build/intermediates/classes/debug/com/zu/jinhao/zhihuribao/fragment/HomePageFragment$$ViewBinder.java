// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomePageFragment$$ViewBinder<T extends com.zu.jinhao.zhihuribao.fragment.HomePageFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558506, "field 'dailyListView'");
    target.dailyListView = finder.castView(view, 2131558506, "field 'dailyListView'");
  }

  @Override public void unbind(T target) {
    target.dailyListView = null;
  }
}
