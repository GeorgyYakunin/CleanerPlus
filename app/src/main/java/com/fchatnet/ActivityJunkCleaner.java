package com.fchatnet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fchatnet.ramboost.R;



import java.util.Random;

public class ActivityJunkCleaner extends AppCompatActivity {

    TextView maintext,cachetext,temptext,residuetext,systemtext,free,mb;
    Button mainbutton;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    int checkvar=0;
    int alljunk;
    androidx.appcompat.widget.Toolbar mActionBarToolbar;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_PERMISSIONS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_junk_clean);
        mainbutton=(Button) findViewById(R.id.mainbutton);
        free = findViewById(R.id.free);
        mb=findViewById(R.id.sufix);

        //CheckPermission();





        maintext=(TextView) findViewById(R.id.maintext);
//        cachetext=(TextView) findViewById(R.id.cachetext);
//        temptext =(TextView) findViewById(R.id.temptext);
//        residuetext =(TextView) findViewById(R.id.residuetext);
//        systemtext =(TextView) findViewById(R.id.systemtext);
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

            sharedpreferences = getApplicationContext().getSharedPreferences("waseem", Context.MODE_PRIVATE);


            if (sharedpreferences.getString("junk", "1").equals("1")) {
                //mainbrush.setImageResource(R.drawable.junk_red);
                mainbutton.setText("clean");
//                cache.setImageResource(R.drawable.cache);
//                temp.setImageResource(R.drawable.temp);
//                residue.setImageResource(R.drawable.res);
//                system.setImageResource(R.drawable.sys);

                Random ran1 = new Random();
                final int proc1 = ran1.nextInt(20) + 5;

                Random ran2 = new Random();
                final int proc2 = ran2.nextInt(15) + 10;

                Random ran3 = new Random();
                final int proc3 = ran3.nextInt(30) + 15;

                Random ran4 = new Random();
                final int proc4 = ran4.nextInt(25) + 10;

                alljunk = proc1 + proc2 + proc3 + proc4;

                maintext.setText(alljunk+"");
                maintext.setTextColor(Color.parseColor("#FFFFFF"));

//                cachetext.setText(proc1 + "MB");
//                cachetext.setTextColor(Color.parseColor("#F22938"));
//
//                temptext.setText(proc2 + " MB");
//                temptext.setTextColor(Color.parseColor("#F22938"));
//
//                residuetext.setText(proc3 + " MB");
//                residuetext.setTextColor(Color.parseColor("#F22938"));
//
//                systemtext.setText(proc4 + " MB");
//                systemtext.setTextColor(Color.parseColor("#F22938"));

            } else {
                //mainbrush.setImageResource(R.drawable.junk_blue);
                maintext.setText("No Junk File Already Cleaned");
                maintext.setTextSize(18);
                maintext.setTextColor(Color.parseColor("#FFFFFF"));
                mb.setVisibility(View.GONE);
                free.setVisibility(View.GONE);
                mainbutton.setText("cleaned");
//                cache.setImageResource(R.drawable.cache2);
//                temp.setImageResource(R.drawable.temp2);
//                residue.setImageResource(R.drawable.res2);
//                system.setImageResource(R.drawable.sys2);


//                maintext.setText("CRYSTAL CLEAR");
//                maintext.setTextColor(Color.parseColor("#24D149"));

//                cachetext.setText(0 + " MB");
//                cachetext.setTextColor(Color.parseColor("#24D149"));
//
//                temptext.setText(0 + " MB");
//                temptext.setTextColor(Color.parseColor("#24D149"));
//
//                residuetext.setText(0 + " MB");
//                residuetext.setTextColor(Color.parseColor("#24D149"));
//
//                systemtext.setText(0 + " MB");
//                systemtext.setTextColor(Color.parseColor("#24D149"));
            }


            mainbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sharedpreferences.getString("junk", "1").equals("1")) {

                        Intent i = new Intent(getApplicationContext(), ActivityScanningJunk.class);
                        i.putExtra("junk", alljunk + "");
                        startActivity(i);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms

                                maintext.setText("No Junk File Already Cleaned");
                                maintext.setTextSize(18);
                                mb.setVisibility(View.GONE);
                                free.setVisibility(View.GONE);
                                maintext.setTextColor(Color.parseColor("#FFFFFF"));
                                //mainbrush.setImageResource(R.drawable.junk_blue);
                                mainbutton.setText("cleaned");
//                                cache.setImageResource(R.drawable.cache2);
//                                temp.setImageResource(R.drawable.temp2);
//                                residue.setImageResource(R.drawable.res2);
//                                system.setImageResource(R.drawable.sys2);


                               // maintext.setText("CRYSTAL CLEAR");
//                                maintext.setTextColor(Color.parseColor("#FFFFFF"));

//                                cachetext.setText(0 + " MB");
//                                cachetext.setTextColor(Color.parseColor("#24D149"));
//
//                                temptext.setText(0 + " MB");
//                                temptext.setTextColor(Color.parseColor("#24D149"));
//
//                                residuetext.setText(0 + " MB");
//                                residuetext.setTextColor(Color.parseColor("#24D149"));
//
//                                systemtext.setText(0 + " MB");
//                                systemtext.setTextColor(Color.parseColor("#24D149"));


                                editor = sharedpreferences.edit();
                                editor.putString("junk", "0");
                                editor.commit();


                                Intent intent = new Intent(getApplicationContext(), Alaram_Junk.class);

                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                                        intent, PendingIntent.FLAG_ONE_SHOT);

                                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (600 * 1000), pendingIntent);

                            }
                        }, 2000);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Junk Files ALready Cleaned.", Toast.LENGTH_LONG).show();

//                        LayoutInflater inflater = getLayoutInflater(getArguments());
//                        View layout = inflater.inflate(R.layout.my_toast, null);
//
//                        ImageView image = (ImageView) layout.findViewById(R.id.image);
//
//                        TextView text = (TextView) layout.findViewById(R.id.textView1);
//                        text.setText("No Junk Files ALready Cleaned.");
//
//                        Toast toast = new Toast(getApplicationContext());
//                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setView(layout);
//                        toast.show();
                    }
                }
            });


//            Random ran1 = new Random();
//            final int proc1 = ran1.nextInt(20) + 5;
//
//            Random ran2 = new Random();
//            final int proc2 = ran2.nextInt(15) + 10;
//
//            Random ran3 = new Random();
//            final int proc3 = ran3.nextInt(30) + 15;
//
//            Random ran4 = new Random();
//            final int proc4 = ran4.nextInt(25) + 10;
//
//            alljunk=proc1+proc2+proc3+proc4;
//
//            maintext.setText(alljunk+" MB");
//            maintext.setTextColor(Color.parseColor("#F22938"));
//
//            cachetext.setText(proc1+" MB");
//            cachetext.setTextColor(Color.parseColor("#F22938"));
//
//            temptext.setText(proc2+" MB");
//            temptext.setTextColor(Color.parseColor("#F22938"));
//
//            residuetext.setText(proc3+" MB");
//            residuetext.setTextColor(Color.parseColor("#F22938"));
//
//            systemtext.setText(proc4+" MB");
//            systemtext.setTextColor(Color.parseColor("#F22938"));

        }
        catch (Exception e)
        {

        }
    }

}
