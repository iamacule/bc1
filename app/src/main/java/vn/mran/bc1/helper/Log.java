package vn.mran.bc1.helper;

/**
 * Created by Mr An on 27/11/2017.
 */

public class Log {
    private static boolean enableLog = false;

    public static void d(String tag, String message) {
        if (enableLog)
            android.util.Log.d(tag, message);
    }

    public static void e(String tag, String message) {
        if (enableLog)
            android.util.Log.e(tag, message);
    }
}
