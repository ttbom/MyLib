package com.example.thtlibrary.utils;

import android.content.Context;

/**
 * @author liujingxing  on 16/5/5.
 */
public class UtilLibrary {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}