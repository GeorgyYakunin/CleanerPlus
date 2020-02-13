package com.fchatnet.cleaner;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fchatnet.Apps;
import com.fchatnet.Scan_Cpu_Apps;
import com.fchatnet.ramboost.R;



import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import lal.adhish.gifprogressbar.GifView;

public class ActivityCoolingCpu extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;
    Scan_Cpu_Apps mAdapter;
    RecyclerView recyclerView;
    List<Apps> app = null;
    PackageManager pm;
    List<ApplicationInfo> packages;
    TextView cooledcpu;
    RelativeLayout rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cooling_cpu);
        mActionBarToolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);






        mActionBarToolbar.setTitleTextColor(Color.WHITE);
        rel=(RelativeLayout) findViewById(R.id.rel);
        app= new ArrayList<>();
        cooledcpu=(TextView) findViewById(R.id.cpucooler);

        mActionBarToolbar.setNavigationIcon(R.drawable.bck);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        GifView pGif = (GifView) findViewById(R.id.progressBar);
        pGif.setImageResource(R.drawable.cooling);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setItemAnimator(new SlideInLeftAnimator());


        mAdapter = new Scan_Cpu_Apps(ActivityScanningCpu.apps);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.computeHorizontalScrollExtent();
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        try {


            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    add("Limit Brightness Upto 80%", 0);


                }
            }, 0);

            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Decrease Device Performance", 1);


                }
            }, 900);

            final Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Close All Battery Consuming Apps", 2);


                }
            }, 1800);

            final Handler handler4 = new Handler();
            handler4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3);


                }
            }, 2700);

            final Handler handler5 = new Handler();
            handler5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 4);
                }
            }, 3700);
//
            final Handler handler6 = new Handler();
            handler6.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);
                }
            }, 4400);

            final Handler handler7 = new Handler();
            handler7.postDelayed(new Runnable() {
                @Override
                public void run() {
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                    remove(0);

//                    final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
//                    ImageView imageView=(ImageView)findViewById(R.id.centerImage);
//                    rippleBackground.startRippleAnimation();

//                    img_animation.setImageResource(0);
//                    img_animation.setBackgroundResource(0);
//                    cpu.setImageResource(R.drawable.green_circle);
//                    scanner.setImageResource(R.drawable.task_complete);


                    rel.setVisibility(View.GONE);

                    cooledcpu.setText("Cooled CPU to 25.3°C");
                    final Handler handler6 = new Handler();
                    handler6.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);

                            finish();

                        }
                    }, 1000);
                }
            }, 5500);
//
//        final Handler handler8 = new Handler();
//        handler8.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
//                remove(0);
//
//
//            }
//        }, 8000);

        }
        catch(Exception e)
        {

        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                FragmentManager fm = getFragmentManager();
////                FragmentTransaction ft = fm.beginTransaction();
////                MainFragment buh = new MainFragment();
////                ft.replace(android.R.id.content, buh );
////                ft.commit();
//                finish();
//                startActivity(new Intent(getApplicationContext(), ActivityMainScreen.class));
//            }
//        },2000);



    }
    public void add(String text, int position) {


//        int p=0 + (int)(Math.random() * ((packages.size() - 0) + 1));

//        Drawable ico = null;

//        Apps item=new Apps();

//        String packageName = packages.get(p).packageName;
//        try {
//            String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
//            ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
//            item.setImage(ico = getPackageManager().getApplicationIcon(packages.get(p).packageName));
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


//        item.setSize(packages.get(position).dataDir);
//        CPUCooler.apps.add(item);
//        mDataSet.add(position, text);
        try {


            mAdapter.notifyItemInserted(position);
        }
        catch(Exception e)
        {

        }
    }


    public void remove(int position) {
//        mDataSet.add(position, text);
        mAdapter.notifyItemRemoved(position);
        try {
            ActivityScanningCpu.apps.remove(position);
        }
        catch(Exception e)
        {

        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
