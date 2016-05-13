// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DailyContentActivity$$ViewBinder<T extends com.zu.jinhao.zhihuribao.activity.DailyContentActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558492, "field 'webView'");
    target.webView = finder.castView(view, 2131558492, "field 'webView'");
    view = finder.findRequiredView(source, 2131558491, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558491, "field 'toolbar'");
  }

  @Override public void unbind(T target) {
    target.webView = null;
    target.toolbar = null;
  }
}
