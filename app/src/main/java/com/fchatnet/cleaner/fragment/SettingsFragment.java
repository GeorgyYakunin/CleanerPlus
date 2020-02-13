package com.fchatnet.cleaner.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.fchatnet.cleaner.base.FragmentContainerActivityBasicBackSwipe;


public class SettingsFragment extends PreferenceFragment {


    public static void launch(Activity from) {
        FragmentContainerActivityBasicBackSwipe.launch(from, SettingsFragment.class, null);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



}
