// Generated code from Butter Knife. Do not modify!
package com.zu.jinhao.zhihuribao.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NetErrorFragment$$ViewBinder<T extends com.zu.jinhao.zhihuribao.fragment.NetErrorFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558509, "field 'tryAgain' and method 'tryAgain'");
    target.tryAgain = finder.castView(view, 2131558509, "field 'tryAgain'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.tryAgain();
        }
      });
  }

  @Override public void unbind(T target) {
    target.tryAgain = null;
  }
}
