package com.fchatnet.cleaner.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;





import com.fchatnet.cleaner.base.ActivityBasicBackSwipe;
import com.fchatnet.cleaner.fragment.SoftwareManageFragment;
import com.fchatnet.cleaner.fragment.WeakFragmentPagerAdapter;
import com.fchatnet.cleaner.utils.SystemBarTintManager;
import com.fchatnet.cleaner.utils.UIElementsHelper;
import com.fchatnet.cleaner.views.SlidingTab;
import com.fchatnet.ramboost.R;


import butterknife.BindView;

public class ActivityBasicBackSwipeSoftwareManage extends ActivityBasicBackSwipe {
    ActionBar ab;
    private MyPagerAdapter adapter;
    @BindView(R.id.pagerFragmentTask)
    ViewPager pager;
    Resources res;
    @BindView(R.id.tabs)
    SlidingTab tabs;

    public class MyPagerAdapter extends WeakFragmentPagerAdapter {
        private final String[] TITLES = new String[]{"Installed App", "System App"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public CharSequence getPageTitle(int position) {
            return this.TITLES[position];
        }

        public int getCount() {
            return this.TITLES.length;
        }

        public Fragment getItem(int position) {
            SoftwareManageFragment fragment = new SoftwareManageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            saveFragment(fragment);
            return fragment;
        }
    }





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_software_manage);














        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        this.res = getResources();
        this.adapter = new MyPagerAdapter(getSupportFragmentManager());
        this.pager.setAdapter(this.adapter);
        this.pager.setPageMargin((int) TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics()));
        this.tabs.setViewPager(this.pager);
        setTabsValue();
    }

    private void setTabsValue() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.tabs.setShouldExpand(true);
        this.tabs.setDividerColor(0);
        this.tabs.setUnderlineHeight((int) TypedValue.applyDimension(1, 1.0f, dm));
        this.tabs.setIndicatorHeight((int) TypedValue.applyDimension(1, 3.0f, dm));
        this.tabs.setTextSize((int) TypedValue.applyDimension(2, 16.0f, dm));
        this.tabs.setTextColor(Color.parseColor("#ffffff"));
        this.tabs.setIndicatorColor(Color.parseColor("#ffffff"));
        this.tabs.setSelectedTextColor(Color.parseColor("#ffffff"));
        this.tabs.setTabBackground(0);
    }

    private void applyKitKatTranslucency() {
        if (VERSION.SDK_INT >= 19) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setTintDrawable(UIElementsHelper.getGeneralActionBarBackground(this));
            getActionBar().setBackgroundDrawable(UIElementsHelper.getGeneralActionBarBackground(this));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != android.R.id.home) {
            return super.onOptionsItemSelected(item);
        }


        finish();
        return true;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= 67108864;
        } else {
            winParams.flags &= -67108865;
        }
        win.setAttributes(winParams);
    }
}
