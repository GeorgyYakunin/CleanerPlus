package com.fchatnet.cleaner.base;

import android.os.Bundle;
import android.view.View;

import com.fchatnet.cleaner.swipeback.SwipeBackActivityBase;
import com.fchatnet.cleaner.swipeback.SwipeBackActivityHelper;
import com.fchatnet.cleaner.swipeback.SwipeBackLayout;
import com.fchatnet.cleaner.swipeback.Utils;


public abstract class ActivityBasicBackSwipe extends ActivityBasic implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHelper = new SwipeBackActivityHelper(this);
        this.mHelper.onActivityCreate();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mHelper.onPostCreate();
    }

    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null || this.mHelper == null) {
            return v;
        }
        return this.mHelper.findViewById(id);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mHelper.getSwipeBackLayout();
    }

    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
