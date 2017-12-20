package vn.mran.bc1.helper;

import android.os.SystemClock;
import android.view.View;

/**
 *
 * Created by AnPham on 05.05.2016.
 *
 * Last modified on 19.01.2017
 *
 * Copyright 2017 Audi AG, All Rights Reserved
 *
 */
public abstract class OnDoubleClickListener implements View.OnClickListener {
    private long minClickInterval = 250;
    private long mLastClickTime;
    public abstract void onDoubleClick(View v);

    @Override
    public final void onClick(View v) {
        long currentClickTime=SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;
        if(elapsedTime<=minClickInterval){
            onDoubleClick(v);
        }
    }

}