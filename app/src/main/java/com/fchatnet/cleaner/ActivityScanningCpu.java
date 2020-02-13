package com.fchatnet.cleaner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fchatnet.Apps;
import com.fchatnet.RecyclerAdapter;
import com.fchatnet.ramboost.R;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ActivityScanningCpu extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;

    TextView batterytemp, showmain, showsec, nooverheating;
    float temp;
    Button coolbutton;// tempimg;
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;
    public static List<Apps> apps;
    List<Apps> apps2;
    int check = 0;


    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                int level = intent.getIntExtra("level", 0);
                temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;

                batterytemp.setText(temp + "°C");

                if (temp >= 30.0) {

                    apps = new ArrayList<>();
                    apps2 = new ArrayList<>();
                    //tempimg.setImageResource(R.drawable.red_cooler);
                    showmain.setText("OVERHEATED");
                    showmain.setTextColor(Color.parseColor("#F22938"));
                    showsec.setText("Apps are causing problem hit cool down");
                    nooverheating.setText("");

                    coolbutton.setText("cool_button_blue");
                    coolbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent i = new Intent(getApplicationContext(), ActivityCoolingCpu.class);
                            startActivity(i);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

//                        getActivity().unregisterReceiver(batteryReceiver);
                                    nooverheating.setText("Currently No App causing Overheating");
                                    showmain.setText("NORMAL");
                                    showmain.setTextColor(Color.parseColor("#24D149"));
                                    showsec.setText("CPU Temperature is GOOD");
                                    showsec.setTextColor(Color.parseColor("#24D149"));
                                    coolbutton.setText("cooled");
                                    // tempimg.setImageResource(R.drawable.blue_cooler);
                                    batterytemp.setText("25.3" + "°C");
                                    recyclerView.setAdapter(null);

                                }
                            }, 2000);


                            coolbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "CPU Temperature is Already Normal", Toast.LENGTH_SHORT).show();

//                                    LayoutInflater inflater = getLayoutInflater(getArguments());
//                                    View layout = inflater.inflate(R.layout.my_toast, null);
//
//                                    ImageView image = (ImageView) layout.findViewById(R.id.image);
//
//                                    TextView text = (TextView) layout.findViewById(R.id.textView1);
//                                    text.setText("CPU Temperature is Already Normal.");
//
//                                    Toast toast = new Toast(getApplicationContext());
//                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
//                                    toast.setDuration(Toast.LENGTH_LONG);
//                                    toast.setView(layout);
//                                    toast.show();
                                }
                            });
                        }
                    });

                    if (Build.VERSION.SDK_INT < 23) {

                        showsec.setTextAppearance(context, android.R.style.TextAppearance_Medium);
                        showsec.setTextColor(Color.parseColor("#F22938"));

                    } else {

                        showsec.setTextAppearance(android.R.style.TextAppearance_Small);
                        showsec.setTextColor(Color.parseColor("#F22938"));
                    }


                    recyclerView.setItemAnimator(new SlideInLeftAnimator());
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
//                recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

                    recyclerView.getItemAnimator().setAddDuration(10000);

                    mAdapter = new RecyclerAdapter(apps);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
                    recyclerView.computeHorizontalScrollExtent();
                    recyclerView.setAdapter(mAdapter);
                    getAllICONS();
//                recyclerView.getItemAnimator().setRemoveDuration(1000);
//                recyclerView.getItemAnimator().setMoveDuration(1000);
//                recyclerView.getItemAnimator().setChangeDuration(1000);

                }
            }
            catch(Exception e)
            {

            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scan_cpu);
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
        try {

            getApplicationContext().registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


            //tempimg = (ImageView) findViewById(R.id.tempimg);
            showmain = (TextView) findViewById(R.id.showmain);
            showsec = (TextView) findViewById(R.id.showsec);
            coolbutton = (Button) findViewById(R.id.coolbutton);
            nooverheating = (TextView) findViewById(R.id.nooverheating);

            showmain.setText("NORMAL");
            showsec.setText("CPU Temperature is GOOD");
            nooverheating.setText("Currently No App causing Overheating");
            coolbutton.setText("cooled");
            coolbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LayoutInflater inflater = getLayoutInflater(getArguments());
//                    View layout = inflater.inflate(R.layout.my_toast, null);
//
//                    ImageView image = (ImageView) layout.findViewById(R.id.image);
//
//                    TextView text = (TextView) layout.findViewById(R.id.textView1);
//                    text.setText("CPU Temperature is Already Normal.");
//
//                    Toast toast = new Toast(getApplicationContext());
//                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
//                    toast.setDuration(Toast.LENGTH_LONG);
//                    toast.setView(layout);
//                    toast.show();
                }
            });

            //tempimg.setImageResource(R.drawable.blue_cooler);
            batterytemp = (TextView) findViewById(R.id.batterytemp);

            Log.e("Temperrature", temp + "");
        } catch (Exception e) {

        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//                startActivity(new Intent(getApplicationContext(),ActivityCoolingCpu.class));
//            }
//        },6000);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {

            getApplicationContext().unregisterReceiver(batteryReceiver);
        } catch (Exception e) {

        }
    }
    public void getAllICONS() {

        PackageManager pm = getApplicationContext().getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        if (packages != null) {
            for (int k = 0; k < packages.size(); k++) {
                // String pkgName = app.getPackageName();
                String packageName = packages.get(k).packageName;
                Log.e("packageName-->", "" + packageName);

                if (!packageName.equals("fast.cleaner.battery.saver")) {

//                String size = packages.get(k).metaData.size()+"";
//                Log.e("Size-->", "" + packageName);
                    Drawable ico = null;
                    try {
                        String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                        Apps app = new Apps();

//                    app.setSize("" + pName);

                        File file = new File(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA).publicSourceDir);
                        long size = file.length();

                        Log.e("SIZE", size / 1000000 + "");
                        app.setSize(size / 1000000 + 20 + "MB");

                        ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                        app.setImage(ico = getApplicationContext().getPackageManager().getApplicationIcon(packages.get(k).packageName));
                        getApplicationContext().getPackageManager();
                        Log.e("ico-->", "" + ico);

                        if (((a.flags & ApplicationInfo.FLAG_SYSTEM) == 0)) {
//                        System.out.println(">>>>>>packages is system package"+pi.packageName);

                            if (check <= 5) {
                                check++;
                                apps.add(app);
                            } else {
                                getApplicationContext().unregisterReceiver(batteryReceiver);
//                            batterytemp.setText("25.3" + "°C");
                                break;
                            }

                        }
                        mAdapter.notifyDataSetChanged();


                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("ERROR", "Unable to find icon for package '"
                                + packageName + "': " + e.getMessage());
                    }
                    // icons.put(processes.get(k).topActivity.getPackageName(),ico);


                }
            }

        }

        if (apps.size() > 1) {
            mAdapter = new RecyclerAdapter(apps);
            mAdapter.notifyDataSetChanged();
        }


    }
}
