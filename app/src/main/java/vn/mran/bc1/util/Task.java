package vn.mran.bc1.util;

import android.app.Activity;
import android.os.Handler;

/**
 * Created by An Pham on 31-Mar-17.
 * Last modifined on 31-Mar-17
 */

public class Task {
    private static Thread backgroundThread;
    private static Thread aliveThread;
    private static Handler handler = new Handler();

    /**
     * Background Thread for doing hard task, for make an UI smoothly
     */
    public static void startBackGroundThread(final Runnable runnable) {
        if (backgroundThread != null) {
            if (backgroundThread.isAlive()) {
                backgroundThread.interrupt();
                Thread.currentThread().interrupt();
            }
            backgroundThread = null;
        }
        backgroundThread = new Thread(runnable);
        backgroundThread.start();
    }

    /**
     * Background Thread for doing hard task, for make an UI smoothly
     */
    public static void startAliveBackGroundThread(final Runnable runnable) {
        if (aliveThread != null) {
            if (!aliveThread.isAlive()) {
                aliveThread = new Thread(runnable);
                aliveThread.start();
            }
        }else {
            aliveThread = new Thread(runnable);
            aliveThread.start();
        }
    }

    /**
     * New Background Thread for doing hard task, for make an UI smoothly
     */
    public static void startNewBackGroundThread(Thread thread) {
        thread.start();
    }

    /**
     * Run on UI Thread
     */
    public static void runOnUIThread(final Activity activity, final Runnable runnable) {
        activity.runOnUiThread(runnable);
    }

    public static void runOnUIThread(final Runnable runnable) {
        handler.post(runnable);
    }

    /**
     * Post delay
     */
    public static void postDelay(final Runnable runnable, final int time) {
        handler.postDelayed(runnable, time);
    }

    /**
     * Remove CallBack
     *
     * @param runnable
     */
    public static void removeCallBack(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {

        }
    }
}
