package com.fchatnet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fchatnet.cleaner.model.SDCardInfo;
import com.fchatnet.cleaner.ActivityScanningCpu;
import com.fchatnet.cleaner.utils.AppUtil;
import com.fchatnet.cleaner.utils.StorageUtil;
import com.fchatnet.cleaner.widget.circleprogress.ArcProgress;
import com.fchatnet.ramboost.R;



import java.util.Timer;
import java.util.TimerTask;

import butterknife.Unbinder;

public class ActivityMainScreen extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private Timer timer;
    private Timer timer2;

    ArcProgress arcStore;


    ArcProgress arcProcess;


    TextView capacity;
    Unbinder unbinder;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar mActionBarToolbar;


    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_PERMISSIONS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_screen);
        arcProcess = findViewById(R.id.arc_process);
        arcStore = findViewById(R.id.arc_store);
        capacity = findViewById(R.id.capacity);
        //CheckPermission();


        //MobileAds.initialize(ActivityMainScreen.this);
        navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drw);

//
//
//

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Phone Cleaner");

        ActivityCompat.requestPermissions(ActivityMainScreen.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                211);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        mActionBarDrawerToggle =
                new ActionBarDrawerToggle(
                        this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close)
                {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

//        LinearLayout phoneboost = (LinearLayout) findViewById(R.id.phone_boost);
//        phoneboost.setOnClickListener(ActivityMainScreen.this);
        findViewById(R.id.background_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ActivityScanningCpu.class));

            }
        });
        findViewById(R.id.app_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ActivityBatteryOptimizing.class));
            }
        });
        findViewById(R.id.phone_boost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ActivityBasicBackSwipeMemoryClean.class));
                CharSequence  s = getResources().getString(R.string.in_develop);
                Toast toast=Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        findViewById(R.id.junk_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ActivityJunkCleaner.class));
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        fillData();
    }


    private void fillData() {
        // TODO Auto-generated method stub
        timer = null;
        timer2 = null;
        timer = new Timer();
        timer2 = new Timer();


        long l = AppUtil.getAvailMemory(getApplicationContext());
        long y = AppUtil.getTotalMemory(getApplicationContext());
        final double x = (((y - l) / (double) y) * 100);
        //   arcProcess.setProgress((int) x);

        arcProcess.setProgress(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ActivityMainScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (arcProcess.getProgress() >= (int) x) {
                            timer.cancel();
                        } else {
                            arcProcess.setProgress(arcProcess.getProgress() + 1);
                        }

                    }
                });
            }
        }, 50, 20);

        SDCardInfo mSDCardInfo = StorageUtil.getSDCardInfo();
        SDCardInfo mSystemInfo = StorageUtil.getSystemSpaceInfo(getApplicationContext());

        long nAvailaBlock;
        long TotalBlocks;
        if (mSDCardInfo != null) {
            nAvailaBlock = mSDCardInfo.free + mSystemInfo.free;
            TotalBlocks = mSDCardInfo.total + mSystemInfo.total;
        } else {
            nAvailaBlock = mSystemInfo.free;
            TotalBlocks = mSystemInfo.total;
        }

        final double percentStore = (((TotalBlocks - nAvailaBlock) / (double) TotalBlocks) * 100);

        capacity.setText(StorageUtil.convertStorage(TotalBlocks - nAvailaBlock) + "/" + StorageUtil.convertStorage(TotalBlocks));
        arcStore.setProgress(0);

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                ActivityMainScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (arcStore.getProgress() >= (int) percentStore) {
                            timer2.cancel();
                        } else {
                            arcStore.setProgress(arcStore.getProgress() + 1);
                        }

                    }
                });
            }
        }, 50, 20);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            
            case R.id.Exit: {
                finish();
                break;
            }

        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onDestroy() {
        timer.cancel();
        timer2.cancel();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 211: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ActivityMainScreen.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
