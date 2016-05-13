// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomePageDailyListAdapter$ViewHolder$$ViewBinder<T extends com.zu.jinhao.zhihuribao.adapter.HomePageDailyListAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558537, "field 'imageView'");
    target.imageView = finder.castView(view, 2131558537, "field 'imageView'");
    view = finder.findRequiredView(source, 2131558536, "field 'titleText'");
    target.titleText = finder.castView(view, 2131558536, "field 'titleText'");
  }

  @Override public void unbind(T target) {
    target.imageView = null;
    target.titleText = null;
  }
}
