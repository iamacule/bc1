package vn.mran.bc1.util.toast;

import android.annotation.SuppressLint;
import android.app.Activity;

/**
 * Created by AnPham on 09.07.2016.
 * <p>
 * Last modified on 19.01.2017
 * <p>
 * Copyright 2017 Audi AG, All Rights Reserved
 */
public class Boast {
    private volatile static Boast globalBoast = null;
    private ToastInfo internalToast;

    private Boast(ToastInfo toast) {
        internalToast = toast;
    }

    @SuppressLint("ShowToast")
    public static void makeText(Activity activity, String message) {
        new Boast(new ToastInfo(activity, message)).show();
    }

    @SuppressLint("ShowToast")
    public static void makeText(Activity activity, String message, int color) {
        new Boast(new ToastInfo(activity, message, color)).show();
    }

    public void cancel() {
        internalToast.cancel();
    }

    public void show() {
        show(true);
    }

    private void show(boolean cancelCurrent) {
        // cancel current
        if (cancelCurrent && (globalBoast != null)) {
            globalBoast.cancel();
        }

        // save an instance of this current notification
        globalBoast = this;

        internalToast.show();
    }
}