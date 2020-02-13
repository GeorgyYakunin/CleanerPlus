package com.fchatnet.cleaner.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fchatnet.Apps;
import com.fchatnet.Memory_Adapter;
import com.fchatnet.ActivityMainScreen;
import com.fchatnet.ramboost.R;




import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import lal.adhish.gifprogressbar.GifView;


public class ActivityDoneCleanUp extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;


    ImageView front,back;
    Runnable myRunnable2;
    Handler myHandler2;
    Handler myHandler;
    Runnable myRunnable;
    int check = 0;
    TextView files;
    List<ApplicationInfo> packages;
    int prog=0;
    Timer T2,T3;
    Memory_Adapter mAdapter;
    RecyclerView recyclerView;
    public List<Apps> apps;
    PackageManager pm;
    ImageView mImgCheck;
    TextView scanning;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clean_done);

        mActionBarToolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);

        mActionBarToolbar.setTitleTextColor(Color.WHITE);

        mActionBarToolbar.setNavigationIcon(R.drawable.bck);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        GifView pGif = (GifView) findViewById(R.id.progressBar);
        pGif.setImageResource(R.drawable.memory);

//
//
//



//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
//
//                finish();
//                startActivity(new Intent(getApplicationContext(), ActivityMainScreen.class));
//            }
//        },5000);
        apps=new ArrayList<>();
        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mAdapter = new Memory_Adapter(apps);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.computeHorizontalScrollExtent();
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Limit Brightness Upto 80%", 0);


            }
        }, 1000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Decrease Device Performance", 1);


            }
        }, 2000);

        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Close All Battery Consuming Apps", 2);



            }
        }, 3000);

        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3);


            }
        }, 4000);

        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 4);

                remove(0);


            }
        }, 5000);

        final Handler handler6 = new Handler();
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);
                remove(0);


            }
        }, 6000);

        final Handler handler7 = new Handler();
        handler7.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                remove(0);


            }
        }, 7000);

        final Handler handler8 = new Handler();
        handler8.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                remove(0);




//                front.setImageResource(0);
//                imageView.setImageResource(0);
//                front.setImageDrawable(ContextCompat.getDrawable(Sacnning_Junk.this, R.drawable.task_complete));
//                imageView.setImageDrawable(ContextCompat.getDrawable(Sacnning_Junk.this, R.drawable.green_circle));







                if (Build.VERSION.SDK_INT < 23) {

//                    scanning.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
//                    scanning.setText(junk.getString("junk")+ " MB of Junk Files Are Cleared");

                } else {

//                    scanning.setTextAppearance(android.R.style.TextAppearance_Medium);
//                    scanning.setTextColor(Color.WHITE);
//                    scanning.setText(junk.getString("junk")+ " MB of Junk Files Are Cleared");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ActivityMainScreen.class));
                        }
                    },2000);
                }










            }
        }, 8000);

    }
    public void add(String text, int position) {


        int p=0 + (int)(Math.random() * ((packages.size()-1 - 0) + 1));


        Drawable ico = null;

        Apps item=new Apps();

        String packageName = packages.get(p).packageName;
        try {
            String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
            ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            item.setImage(ico = getPackageManager().getApplicationIcon(packages.get(p).packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        item.setSize(packages.get(p).dataDir);
        apps.add(item);
//        mDataSet.add(position, text);
        mAdapter.notifyItemInserted(position);
    }


    public void remove(int position) {
//        mDataSet.add(position, text);
        mAdapter.notifyItemRemoved(position);
        apps.remove(position);
    }



    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDivider = context.getResources().getDrawable(R.drawable.line_divvide, context.getTheme());
            } else {
                mDivider = context.getResources().getDrawable(R.drawable.line_divvide);

            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }


}
