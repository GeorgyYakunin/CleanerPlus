package com.fchatnet.cleaner.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.fchatnet.cleaner.utils.C0221T;

@SuppressLint({"NewApi"})
public class BaseFragment extends Fragment {
    protected void startActivity(Class<?> cls) {
        startActivity((Class) cls, null);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivity(String action) {
        startActivity(action, null);
    }

    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void showShort(String message) {
        C0221T.showShort(getActivity(), (CharSequence) message);
    }

    protected void showLong(String message) {
        C0221T.showLong(getActivity(), (CharSequence) message);
    }
}
