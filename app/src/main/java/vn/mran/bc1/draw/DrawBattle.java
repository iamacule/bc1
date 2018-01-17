package vn.mran.bc1.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import vn.mran.bc1.R;
import vn.mran.bc1.helper.Log;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.Task;

/**
 * Created by Mr An on 28/11/2017.
 */

public class DrawBattle extends View {

    private final int MID_POINT_Y = 55;
    private final int ANIMAL_SIZE_PERCENT = 40;
    private final int BOTTOM_Y_2 = 63;
    private final int BOTTOM_Y_1 = 62;
    private final int RIGHT_MAX = 62;
    private final int LEFT_Y_2 = 48;
    private final int LEFT_Y_1 = 47;
    private final int LEFT_X_1 = 32;
    private final int LEFT_X_2 = 34;
    private final int RIGHT_X_1 = 67;
    private final int RIGHT_X_2 = 68;

    public interface OnDrawLidUpdate {
        void onTouch();

        void onLidChanged(boolean isOpened);
    }

    private final String TAG = getClass().getSimpleName();

    private Bitmap[] animalArrays1 = new Bitmap[6];
    private Bitmap[] animalArrays2 = new Bitmap[6];
    private OnDrawLidUpdate onDrawLidUpdate;

    private Bitmap bpLid;
    private Bitmap bpPlate;

    private Rect rectLid;
    private Rect rectPlate;
    private int width;
    private int height;

    private Point midPoint = new Point(-9999, -9999);
    private boolean isLidOpened = false;
    private int[] resultArrays = new int[3];
    private int[] randomNumberArrays = new int[3];
    private Point[] randomMidPointArrays = new Point[]{new Point(), new Point(), new Point()};

    public DrawBattle(Context context) {
        super(context);
        init(context);
    }

    public DrawBattle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnDrawLidUpdate(OnDrawLidUpdate onDrawLidUpdate) {
        this.onDrawLidUpdate = onDrawLidUpdate;
    }

    private void init(Context context) {
        bpLid = BitmapFactory.decodeResource(context.getResources(), R.drawable.lid);
        bpPlate = BitmapFactory.decodeResource(context.getResources(), R.drawable.plate);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        midPoint = new Point(width / 2, height * MID_POINT_Y / 100);
        bpLid = ResizeBitmap.resize(bpLid, w * 95 / 100);
        bpPlate = ResizeBitmap.resize(bpPlate, w * 95 / 100);

        Task.startNewBackGroundThread(new Thread(new Runnable() {
            @Override
            public void run() {

                animalArrays1[0] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.bau_1), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays1[1] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.cua_1), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays1[2] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.tom_1), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays1[3] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ca_1), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays1[4] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ga_1), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays1[5] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.nai_1), width * ANIMAL_SIZE_PERCENT / 100);

                animalArrays2[0] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.bau_2), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays2[1] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.cua_2), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays2[2] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.tom_2), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays2[3] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ca_2), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays2[4] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ga_2), width * ANIMAL_SIZE_PERCENT / 100);
                animalArrays2[5] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.nai_2), width * ANIMAL_SIZE_PERCENT / 100);

                updateRandomMidPointArrays();

                openLid(false);
            }
        }));
    }

    public void setBpPlate(Bitmap bpPlate) {
        if (width > 0) {
            this.bpPlate = ResizeBitmap.resize(bpPlate, width * 9 / 10);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Draw
        rectPlate = new Rect(width / 2 - bpPlate.getWidth() / 2, height * MID_POINT_Y / 100 - bpPlate.getHeight() / 2, width / 2 + bpPlate.getWidth() / 2, height * MID_POINT_Y / 100 + bpPlate.getHeight() / 2);
        canvas.drawBitmap(bpPlate, null, rectPlate, null);

        if (isLidOpened) {
            if (resultArrays.length > 0) {
                for (int i = 0; i < resultArrays.length; i++) {
                    Bitmap bp = null;
                    Rect rect;
                    if (randomNumberArrays[i] == 0) {
                        bp = animalArrays1[resultArrays[i]];
                    } else {
                        bp = animalArrays2[resultArrays[i]];
                    }

                    if (bp != null) {
                        rect = new Rect(randomMidPointArrays[i].x - bp.getWidth() / 2, randomMidPointArrays[i].y - bp.getHeight() / 2,
                                randomMidPointArrays[i].x + bp.getWidth() / 2, randomMidPointArrays[i].y + bp.getHeight() / 2);
                        canvas.drawBitmap(bp, null, rect, null);
                    }
                }
            }
        }

        rectLid = new Rect(midPoint.x - bpLid.getWidth() / 2, midPoint.y - bpLid.getHeight() / 2, midPoint.x + bpLid.getWidth() / 2, midPoint.y + bpLid.getHeight() / 2);
        canvas.drawBitmap(bpLid, null, rectLid, null);
    }

    private void updateRandomMidPointArrays() {
        switch (getRandomNumber(0, 1)) {
            case 0:
                for (int i = 0; i < randomMidPointArrays.length; i++) {
                    switch (i) {
                        case 0:
                            randomMidPointArrays[i].x = getRandomNumber(width * LEFT_X_1 / 100, width * LEFT_X_2 / 100);
                            randomMidPointArrays[i].y = getRandomNumber(height * LEFT_Y_1 / 100, height * LEFT_Y_2 / 100);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].x);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].y);
                            break;

                        case 1:
                            randomMidPointArrays[i].x = getRandomNumber(width * RIGHT_X_1 / 100, width * RIGHT_X_2 / 100);
                            randomMidPointArrays[i].y = getRandomNumber(height * LEFT_Y_1 / 100, height * LEFT_Y_2 / 100);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].x);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].y);
                            break;

                        case 2:
                            randomMidPointArrays[i].x = getRandomNumber(width * LEFT_X_1 / 100, width * RIGHT_MAX / 100);
                            randomMidPointArrays[i].y = getRandomNumber(height * BOTTOM_Y_1 / 100, height * BOTTOM_Y_2 / 100);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].x);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].y);
                            break;
                    }
                }
                break;
            case 1:
                for (int i = 0; i < randomMidPointArrays.length; i++) {
                    switch (i) {
                        case 0:
                            randomMidPointArrays[i].x = getRandomNumber(width * LEFT_X_1 / 100, width * RIGHT_MAX / 100);
                            randomMidPointArrays[i].y = getRandomNumber(height * LEFT_Y_1 / 100, height * LEFT_Y_2 / 100);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].x);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].y);
                            break;

                        case 1:
                            randomMidPointArrays[i].x = getRandomNumber(width * LEFT_X_1 / 100, width * LEFT_X_2 / 100);
                            randomMidPointArrays[i].y = getRandomNumber(height * BOTTOM_Y_1 / 100, height * BOTTOM_Y_2 / 100);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].x);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].y);
                            break;

                        case 2:
                            randomMidPointArrays[i].x = getRandomNumber(width * RIGHT_X_1 / 100, width * RIGHT_X_2 / 100);
                            randomMidPointArrays[i].y = getRandomNumber(height * BOTTOM_Y_1 / 100, height * BOTTOM_Y_2 / 100);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].x);
                            Log.d(TAG, "randomMidPointArrays : " + randomMidPointArrays[i].y);
                            break;
                    }
                }
                break;
        }

        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onDrawLidUpdate != null)
                    onDrawLidUpdate.onTouch();
                break;
        }
        return false;
    }

    public void action() {
        if (isLidOpened)
            closeLid();
        else openLid(true);
    }

    private void closeLid() {
        int num = height / 170;
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            midPoint.y = midPoint.y + num;
            midPoint.x = midPoint.x - num;
            postInvalidate();
            if (midPoint.y >= (height * MID_POINT_Y / 100) + num) {
                isLidOpened = false;
                Task.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        Media.playShortSound(getContext(), R.raw.open_close);
                        onDrawLidUpdate.onLidChanged(isLidOpened);
                    }
                });
                break;
            }
        }
    }

    private void openLid(final boolean sound) {
        isLidOpened = true;
        Task.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (sound)
                    Media.playShortSound(getContext(), R.raw.open_close);
                onDrawLidUpdate.onLidChanged(isLidOpened);
            }
        });
        int num = height / 170;
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            midPoint.y = midPoint.y - num;
            midPoint.x = midPoint.x + num;
            postInvalidate();
            if (midPoint.x >= width + bpLid.getWidth() / 2) {
                break;
            }
        }
    }

    private int getRandomNumber(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public void setResultArrays(int[] resultArrays) {
        this.resultArrays = resultArrays;
        this.randomNumberArrays = new int[]{
                getRandomNumber(0, 1),
                getRandomNumber(0, 1),
                getRandomNumber(0, 1)
        };
        invalidate();

        if (width > 0) {
            Task.startNewBackGroundThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    updateRandomMidPointArrays();
                }
            }));
        }
    }
}
