// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.zu.jinhao.zhihuribao.activity.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558483, "field 'backImageButton'");
    target.backImageButton = finder.castView(view, 2131558483, "field 'backImageButton'");
  }

  @Override public void unbind(T target) {
    target.backImageButton = null;
  }
}
