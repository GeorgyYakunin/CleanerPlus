package com.fchatnet.cleaner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fchatnet.ActivityMainScreen;
import com.fchatnet.ramboost.R;

import lal.adhish.gifprogressbar.GifView;

public class ActivityJunkClean extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_junk_cleaner);
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
        pGif.setImageResource(R.drawable.junkcleaner);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                MainFragment buh = new MainFragment();
//                ft.replace(android.R.id.content, buh );
//                ft.commit();
                finish();
                startActivity(new Intent(getApplicationContext(), ActivityMainScreen.class));
            }
        },4000);
    }
}
