package vn.mran.bc1.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import vn.mran.bc1.R;
import vn.mran.bc1.util.ResizeBitmap;

/**
 * Created by Mr An on 30/11/2017.
 */

public class DrawParallaxStar extends View {

    private final String TAG = getClass().getSimpleName();
    private Bitmap star;
    private Rect[] rectStar = new Rect[5];
    private int width = -1;
    private int height = -1;

    public DrawParallaxStar(Context context) {
        super(context);
        init(context);
    }

    public DrawParallaxStar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        star = BitmapFactory.decodeResource(context.getResources(), R.drawable.flower);

        new ParallaxStarThread().start();
        new RandomStarThread().start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Draw star
        for (int i = 0; i < rectStar.length; i++) {
            if (rectStar[i] != null) {
                canvas.drawBitmap(star, null, rectStar[i], null);
            }
        }
    }

    public void setStarSize(int w) {
        star = ResizeBitmap.resize(star, w);
        invalidate();
    }

    private class RandomStarThread extends Thread {
        @Override
        public void run() {
            while (true) {
                //Draw star
                if (width != -1 && height != -1) {
                    for (int i = 0; i < rectStar.length; i++) {
                        if (rectStar[i] == null) {
                            int left = randomWithRange(0, width - star.getWidth());
                            int top = randomWithRange(0 - width, 0);
                            Rect rect = new Rect(
                                    left,
                                    top,
                                    left + star.getWidth(),
                                    top + star.getHeight());
                            boolean isIntersect = false;
                            for (int j = 0; j < rectStar.length; j++) {
                                if (rectStar[j] != null) {
                                    if (rect.intersect(rectStar[j])) {
                                        isIntersect = true;
                                    }
                                }
                            }
                            if (!isIntersect)
                                rectStar[i] = rect;

                        }
                    }
                }
            }
        }

        int randomWithRange(int min, int max) {
            int range = (max - min) + 1;
            return (int) (Math.random() * range) + min;
        }
    }

    private class ParallaxStarThread extends Thread {
        @Override
        public void run() {
            while (true) {
                for (int i = 0; i < rectStar.length; i++) {
                    if (rectStar[i] != null) {
                        rectStar[i].set(rectStar[i].left,
                                rectStar[i].top + 1,
                                rectStar[i].right,
                                rectStar[i].bottom + 1);

                        if (rectStar[i].top > height) {
                            rectStar[i] = null;
                        }
                        postInvalidate();

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
