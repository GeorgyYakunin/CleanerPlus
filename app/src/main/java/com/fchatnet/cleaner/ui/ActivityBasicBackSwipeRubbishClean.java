package com.fchatnet.cleaner.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fchatnet.cleaner.ActivityJunkClean;




import com.fchatnet.cleaner.adapter.RublishMemoryAdapter;
import com.fchatnet.cleaner.base.ActivityBasicBackSwipe;
import com.fchatnet.cleaner.model.CacheListItem;
import com.fchatnet.cleaner.model.StorageSize;
import com.fchatnet.cleaner.service.CleanerService;
import com.fchatnet.cleaner.utils.StorageUtil;
import com.fchatnet.cleaner.utils.SystemBarTintManager;
import com.fchatnet.cleaner.utils.UIElementsHelper;
import com.fchatnet.cleaner.views.RotateLoading;
import com.fchatnet.ramboost.R;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

public class ActivityBasicBackSwipeRubbishClean extends ActivityBasicBackSwipe implements CleanerService.OnActionListener, OnDismissCallback {
    private static final int INITIAL_DELAY_MILLIS = 300;
    protected static final int PROCESS_MAX = 8;
    protected static final int PROCESS_PROCESS = 9;
    protected static final int SCANING = 5;
    protected static final int SCAN_FINIFSH = 6;
    ActionBar ab;

    @BindView(R.id.bottom_lin)
    LinearLayout bottom_lin;
    @BindView(R.id.clear_button)
    Button clearButton;
    @BindView(R.id.clean_up_tv_done)
    TextView doneBtn;

    @BindView(R.id.clean_up_rocket)
    ImageView doneImg;



    @BindView(R.id.clean_done_iv_done)
    ImageView imageRush;

    @BindView(R.id.clean_done_iv_bg)
    ImageView doneRocket;

    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.layout_container)
    RelativeLayout layoutContainer1;
    @BindView(R.id.layout_container2)
    RelativeLayout layoutContainer2;

    private boolean mAlreadyCleaned = false;
    private boolean mAlreadyScanned = false;
    List<CacheListItem> mCacheListItem = new ArrayList();
    long mCleanSize;
    private CleanerService mCleanerService;

    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.progressBar)
    View mProgressBar;
    @BindView(R.id.progressBarText)
    TextView mProgressBarText;
    @BindView(R.id.rotate_loading)
    RotateLoading mRotateLoading;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            ActivityBasicBackSwipeRubbishClean.this.mCleanerService = ((CleanerService.CleanerServiceBinder) service).getService();
            ActivityBasicBackSwipeRubbishClean.this.mCleanerService.setOnActionListener(ActivityBasicBackSwipeRubbishClean.this);
            if (!ActivityBasicBackSwipeRubbishClean.this.mCleanerService.isScanning() && !ActivityBasicBackSwipeRubbishClean.this.mAlreadyScanned) {
                ActivityBasicBackSwipeRubbishClean.this.mCleanerService.scanCache();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            ActivityBasicBackSwipeRubbishClean.this.mCleanerService.setOnActionListener(null);
            ActivityBasicBackSwipeRubbishClean.this.mCleanerService = null;
        }
    };
    int pprocess = 0;
    int ptotal = 0;
    Resources res;
    RublishMemoryAdapter rublishMemoryAdapter;
    @BindView(R.id.sufix)
    TextView sufix;
    SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;

    TextView textCounter;




    String sizoo = String.valueOf(0);

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_rubl_clean);


        textCounter = (TextView) findViewById(R.id.textCounter);









        findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ActivityJunkClean.class));
            }
        });

//        PackageManager pm = getPackageManager();
//// Get all methods on the PackageManager
//        Method[] methods = pm.getClass().getDeclaredMethods();
//        for (Method m : methods) {
//            if (m.getName().equals("freeStorage")) {
//                // Found the method I want to use
//                try {
//                    long desiredFreeStorage = 8 * 1024 * 1024 * 1024; // Request for 8GB of free space
//                    m.invoke(pm, desiredFreeStorage , null);
//                    Log.d("desiredFreeStorage",""+desiredFreeStorage);
//                    Toast.makeText(getApplicationContext(), ""+desiredFreeStorage, Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    // Method invocation failed. Could be a permission problem
//                }
//                break;
//            }
//        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.res = getResources();
        this.mRotateLoading.start();
        int footerHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.footer_height);
        this.rublishMemoryAdapter = new RublishMemoryAdapter(this.mContext, this.mCacheListItem);
        this.mListView.setAdapter(this.rublishMemoryAdapter);
        this.mListView.setOnItemClickListener(this.rublishMemoryAdapter);
        bindService(new Intent(this.mContext, CleanerService.class), this.mServiceConnection, 1);

//        File[] files = getCacheDir().listFiles();
//        for (File f:files) {
//            sizoo = sizoo+f.length();
//            Log.d("cache Size",""+sizoo);
//        }
//        Log.d("cache Size",""+sizoo);

//        if (VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            this.textCounter.setText(Math.toIntExact(Long.parseLong(sizoo)));
//        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {
    }

    public void onScanStarted(Context context) {
        this.mProgressBarText.setText(R.string.scanning);
        this.mCleanSize = 0;
        showProgressBar(true);
    }

    @Override
    public void onScanProgressUpdated(Context context, int current, int max, int size) {
        this.mProgressBarText.setText(getString(R.string.scanning_m_of_n, new Object[]{Long.valueOf(current), Long.valueOf(max)}));
        this.mCleanSize += size;
        StorageSize mStorageSize = StorageUtil.convertStorageSize(this.mCleanSize);


//        this.textCounter.setCurrentTextValue(sizoo);
        this.sufix.setText(mStorageSize.suffix);
    }



    @SuppressLint("WrongConstant")
    public void onScanCompleted(Context context, List<CacheListItem> apps) {
        showProgressBar(false);
        this.mCacheListItem.clear();
        this.mCacheListItem.addAll(apps);
        this.rublishMemoryAdapter.notifyDataSetChanged();
        this.bottom_lin.setVisibility(0);
        if (!this.mAlreadyScanned) {
            this.mAlreadyScanned = true;
        }
        this.mListView.setVisibility(0);
    }

    public void onCleanStarted(Context context) {
        if (isProgressBarVisible()) {
            showProgressBar(false);
        }
        if (!isFinishing()) {
            showDialogLoading();
        }
    }

    public void onCleanCompleted(Context context, long cacheSize) {
        dismissDialogLoading();
        this.header.setVisibility(View.GONE);
        this.bottom_lin.setVisibility(View.GONE);
        this.mCacheListItem.clear();
        this.rublishMemoryAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.clean_up_tv_done})
    public void onClickDone() {

        finish();

    }

//    @OnClick(R.id.clear_button)
//    public void onClickClear() {
//        getActionBar().hide();
//        this.layoutContainer1.setVisibility(View.GONE);
//        this.layoutContainer2.setVisibility(View.VISIBLE);
//        Animation anim = AnimationUtils.loadAnimation(this.mContext, R.anim.rote_anim);
//        anim.setAnimationListener(new AnimationListener() {
//            @SuppressLint("WrongConstant")
//            public void onAnimationEnd(Animation animation) {
//                ActivityBasicBackSwipeRubbishClean.this.doneRocket.setVisibility(View.GONE);
//                ActivityBasicBackSwipeRubbishClean.this.doneImg.setImageResource(R.drawable.ic_done_white_128dp_2x);
//                imageRush.setVisibility(View.GONE);
//                ActivityBasicBackSwipeRubbishClean.this.doneBtn.setVisibility(0);
//            }
//
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            public void onAnimationStart(Animation animation) {
//            }
//        });
//        this.doneRocket.setAnimation(anim);
//        if (this.mCleanerService != null && !this.mCleanerService.isScanning() && !this.mCleanerService.isCleaning() && this.mCleanerService.getCacheSize() > 0) {
//            this.mAlreadyCleaned = false;
//            this.mCleanerService.cleanCache();
//        }
//    }

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

    @SuppressLint("WrongConstant")
    private boolean isProgressBarVisible() {
        return this.mProgressBar.getVisibility() == 0;
    }

    @SuppressLint("ResourceType")
    private void showProgressBar(boolean show) {
        if (show) {
            this.mProgressBar.setVisibility(0);
            return;
        }
        this.mProgressBar.startAnimation(AnimationUtils.loadAnimation(this.mContext, 17432577));
        this.mProgressBar.setVisibility(View.GONE);
    }

    public void onDestroy() {
        unbindService(this.mServiceConnection);
        super.onDestroy();
    }
}
