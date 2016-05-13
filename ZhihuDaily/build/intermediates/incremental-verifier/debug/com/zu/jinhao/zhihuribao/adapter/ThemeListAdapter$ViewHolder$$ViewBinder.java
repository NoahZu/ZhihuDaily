// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ThemeListAdapter$ViewHolder$$ViewBinder<T extends com.zu.jinhao.zhihuribao.adapter.ThemeListAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558516, "field 'textView'");
    target.textView = finder.castView(view, 2131558516, "field 'textView'");
    view = finder.findRequiredView(source, 2131558517, "field 'imageButton'");
    target.imageButton = finder.castView(view, 2131558517, "field 'imageButton'");
  }

  @Override public void unbind(T target) {
    target.textView = null;
    target.imageButton = null;
  }
}
