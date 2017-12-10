package com.mshafeeq.msknlib.log;

import android.util.Log;

public class MsknLog {
    private static boolean mEnable = false;

    public static void enableLogging(boolean enable) {
        mEnable = enable;
    }

    public static void logit(String filter, String msg) {
        if(mEnable)
            Log.d(filter, msg);
    }
}
