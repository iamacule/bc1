package vn.mran.bc1.util.toast;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import vn.mran.bc1.R;
import vn.mran.bc1.widget.CustomTextView;

/**
 * Created by AnPham on 04.05.2016.
 * <p>
 * Last modified on 19.01.2017
 * <p>
 * Copyright 2017 Audi AG, All Rights Reserved
 */
public class ToastInfo {
    private Toast toast;

    public ToastInfo(Activity activity, String message) {
        create(activity, message);
    }

    public ToastInfo(Activity activity, String message, int color) {
        create(activity, message, color);
    }

    public void cancel() {
        toast.cancel();
    }

    public void show() {
        toast.show();
    }

    private void create(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) activity.findViewById(R.id.toast_parent));
        CustomTextView text = (CustomTextView) layout.findViewById(R.id.toast_message);
        text.setText(message);

        toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    private void create(Activity activity, String message, int color) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) activity.findViewById(R.id.toast_parent));
        CustomTextView text = (CustomTextView) layout.findViewById(R.id.toast_message);
        text.setTextColor(color);
        text.setText(message);

        toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
    }
}
