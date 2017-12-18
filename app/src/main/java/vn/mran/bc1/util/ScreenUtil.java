package vn.mran.bc1.util;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MrAn PC on 07-Feb-16.
 */
public class ScreenUtil {
    private static DisplayMetrics dm = new DisplayMetrics();

    public static Point getScreenDimentions(WindowManager windowManager){
        Point point = new Point();
        int width = 0, height = 0;
        final DisplayMetrics metrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        Method mGetRawH = null, mGetRawW = null;

        try {
            // For JellyBean 4.2 (API 17) and onward
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(metrics);

                width = metrics.widthPixels;
                height = metrics.heightPixels;
            } else {
                mGetRawH = Display.class.getMethod("getRawHeight");
                mGetRawW = Display.class.getMethod("getRawWidth");

                try {
                    width = (Integer) mGetRawW.invoke(display);
                    height = (Integer) mGetRawH.invoke(display);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        }
        point.set(width,height);
        return point;
    }

    //Get Screen width
    public static float getScreenWidth(WindowManager windowManager){
        return getScreenDimentions(windowManager).x;
    }

    //Get screen height
    public static float getScreenHeight(WindowManager windowManager){
        return getScreenDimentions(windowManager).y;
    }
}
