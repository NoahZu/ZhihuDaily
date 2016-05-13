// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LaunchActivity$$ViewBinder<T extends com.zu.jinhao.zhihuribao.activity.LaunchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558480, "field 'launcherImageView'");
    target.launcherImageView = finder.castView(view, 2131558480, "field 'launcherImageView'");
    view = finder.findRequiredView(source, 2131558482, "field 'launcherImageAuthorText'");
    target.launcherImageAuthorText = finder.castView(view, 2131558482, "field 'launcherImageAuthorText'");
  }

  @Override public void unbind(T target) {
    target.launcherImageView = null;
    target.launcherImageAuthorText = null;
  }
}
