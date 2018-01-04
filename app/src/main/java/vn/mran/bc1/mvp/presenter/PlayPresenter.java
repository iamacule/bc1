package vn.mran.bc1.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import vn.mran.bc1.helper.Log;
import vn.mran.bc1.mvp.view.BattleView;
import vn.mran.bc1.mvp.view.PlayView;

/**
 * Created by Mr An on 29/11/2017.
 */

public class PlayPresenter {
    private final String TAG = getClass().getSimpleName();

    private Context context;

    private PlayView view;

    private boolean isNetworkEnable = false;

    private boolean run = true;

    public PlayPresenter(PlayView view) {
        this.context = (Context) view;
        this.view = view;
        isNetworkEnable = isOnline();
        new NetworkCheckingThread().start();
    }

    public void stopCheckingNetwork() {
        Log.d(TAG, "Stop checking network");
        run = false;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class NetworkCheckingThread extends Thread {
        @Override
        public void run() {
            while (run) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Checking network");
                if (isOnline()) {
                    if (!isNetworkEnable) {
                        isNetworkEnable = true;
                        view.onNetworkChanged(isNetworkEnable);
                    }
                } else {
                    if (isNetworkEnable) {
                        isNetworkEnable = false;
                        view.onNetworkChanged(isNetworkEnable);
                    }
                }
            }
        }
    }

    public String updateText(String oldText) {
        if (oldText.length() * 2 < 60) {
            StringBuilder stringBuilder = new StringBuilder(oldText);
            for (int i = stringBuilder.length() * 2; i <= 60; i++) {
                stringBuilder.append(" ");
            }

            stringBuilder.append(oldText);
            return stringBuilder.toString();
        }
        return oldText;
    }
}
