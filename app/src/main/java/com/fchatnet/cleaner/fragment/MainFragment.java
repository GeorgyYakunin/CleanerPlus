package com.fchatnet.cleaner.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fchatnet.ActivityBatteryOptimizing;
import com.fchatnet.cleaner.base.BaseFragment;
import com.fchatnet.cleaner.model.SDCardInfo;
import com.fchatnet.cleaner.ActivityScanningCpu;
import com.fchatnet.cleaner.ui.ActivityBasicBackSwipeRubbishClean;
import com.fchatnet.cleaner.utils.AppUtil;
import com.fchatnet.cleaner.utils.StorageUtil;
import com.fchatnet.cleaner.widget.circleprogress.ArcProgress;
import com.fchatnet.ramboost.R;

import java.util.Timer;
import java.util.TimerTask;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.arc_store)
    ArcProgress arcStore;

    @BindView(R.id.arc_process)
    ArcProgress arcProcess;

    @BindView(R.id.capacity)
    TextView capacity;

    Context mContext;

    private Timer timer;
    private Timer timer2;
     Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.frag_main_1, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();

        LinearLayout phoneboost = (LinearLayout) view.findViewById(R.id.phone_boost);
        phoneboost.setOnClickListener(this);
        view.findViewById(R.id.background_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityScanningCpu.class));
            }
        });
        view.findViewById(R.id.app_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ActivityBatteryOptimizing.class));
            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        fillData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    private void fillData() {
        // TODO Auto-generated method stub
        timer = null;
        timer2 = null;
        timer = new Timer();
        timer2 = new Timer();


        long l = AppUtil.getAvailMemory(mContext);
        long y = AppUtil.getTotalMemory(mContext);
        final double x = (((y - l) / (double) y) * 100);
        //   arcProcess.setProgress((int) x);

        arcProcess.setProgress(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
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
        SDCardInfo mSystemInfo = StorageUtil.getSystemSpaceInfo(mContext);

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
                getActivity().runOnUiThread(new Runnable() {
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
    public void onClick(View view) {
        if(view.getId() == R.id.phone_boost ){
            CharSequence  s = getResources().getString(R.string.in_develop);
            Toast toast=Toast.makeText(mContext.getApplicationContext(),s,Toast.LENGTH_SHORT);
            toast.show();
        }
    }




    @OnClick(R.id.junk_clean)
    void rubbishClean() {
        startActivity(ActivityBasicBackSwipeRubbishClean.class);
    }


//    @OnClick(R.id.app_manager)
//    void AutoStartManage() {
//        startActivity(ActivityBasicBackSwipeSoftwareManage.class);
//    }

    @OnClick(R.id.background_app)



    @Override
    public void onDestroyView() {
        super.onDestroyView();
       unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        timer.cancel();
        timer2.cancel();
        super.onDestroy();
    }
}
