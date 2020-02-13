package com.fchatnet;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fchatnet.ramboost.R;




import java.util.List;

import lal.adhish.gifprogressbar.GifView;

public class ActivityBatteryOptimizing extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_battery_optimizing);
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
        pGif.setImageResource(R.drawable.battery_gif);











        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                new KillBackgroundProcessesTask().execute();
                finish();
            }
        },5000);










    }
    private class KillBackgroundProcessesTask extends AsyncTask<Void,Integer,Integer> {
        @Override
        protected Integer doInBackground(Void...Void){
            // Get an instance of PackageManager
            PackageManager pm = getPackageManager();

            // Get an instance of ActivityManager
            ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

            // Get a list of RunningAppProcessInfo
            List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();

            // Count the number of running processes
            int initialRunningProcessesSize = list.size();

            // Iterate over the RunningAppProcess list
            for(ActivityManager.RunningAppProcessInfo process: list){
                // Ignore, if the process contains package list is empty
                if(process.pkgList.length == 0) continue;

                try{
                    // Get the PackageInfo for current process
                    PackageInfo packageInfo = pm.getPackageInfo(process.pkgList[0],PackageManager.GET_ACTIVITIES);

                    // Ignore the self app package
                    if(!packageInfo.packageName.equals(getApplication().getPackageName())){
                        // Try to kill other background processes
                        // System processes are ignored
                        am.killBackgroundProcesses(packageInfo.packageName);
                    }
                }catch(PackageManager.NameNotFoundException e){
                    // Catch the exception
                    e.printStackTrace();
                }
            }

            // Get the running processes after killing some
            int currentRunningProcessesSize = am.getRunningAppProcesses().size();

            // Return the number of killed processes
            return initialRunningProcessesSize - currentRunningProcessesSize;
        }

        protected void onPostExecute(Integer result){
            // Show the number of killed processes
            Toast.makeText(getApplicationContext(),"Killed : " + result + " processes",Toast.LENGTH_SHORT).show();

            // Refresh the TextView with running processes
            populateTextViewWithRunningProcesses();
        }
    }
    protected void populateTextViewWithRunningProcesses(){
        // Empty the TextView
        //mTextView.setText("");

        // Initialize a new instance of ActivityManager
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        // Get a list of RunningAppProcessInfo
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();

        // Display the number of running processes
        Toast.makeText(getApplicationContext(),"Running processes : " +
                runningProcesses.size(),Toast.LENGTH_SHORT).show();

        // Loop through the running processes
        for(ActivityManager.RunningAppProcessInfo processInfo: runningProcesses ){
            // Get the process name
            //mTextView.setText(mTextView.getText() + processInfo.processName + "\n");
        }
    }




}
