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

import vn.mran.bc1.R;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.Task;

/**
 * Created by Mr An on 28/11/2017.
 */

public class DrawPlateLid extends View {

    public interface OnDrawLidUpdate {
        void onTouch();

        void onLidChanged(boolean isOpened);
    }

    private final String TAG = getClass().getSimpleName();

    private OnDrawLidUpdate onDrawLidUpdate;

    private Bitmap bpLid;
    private Bitmap bpPlate;

    private Rect rectLid;
    private Rect rectPlate;
    private int width;
    private int height;

    private Point midPoint;
    private boolean isLidOpened = false;

    public DrawPlateLid(Context context) {
        super(context);
        init(context);
    }

    public DrawPlateLid(Context context, @Nullable AttributeSet attrs) {
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
        midPoint = new Point(width / 2, height * 57 / 100);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw
        rectPlate = new Rect(width / 2 - bpPlate.getWidth() / 2, height * 57 / 100 - bpPlate.getHeight() / 2, width / 2 + bpPlate.getWidth() / 2, height * 57 / 100 + bpPlate.getHeight() / 2);
        canvas.drawBitmap(bpPlate, null, rectPlate, null);

        rectLid = new Rect(midPoint.x - bpLid.getWidth() / 2, midPoint.y - bpLid.getHeight() / 2, midPoint.x + bpLid.getWidth() / 2, midPoint.y + bpLid.getHeight() / 2);
        canvas.drawBitmap(bpLid, null, rectLid, null);
    }

    public void setWidth(int w) {
        bpLid = ResizeBitmap.resize(bpLid, w * 9 / 10);
        bpPlate = ResizeBitmap.resize(bpPlate, w * 9 / 10);
        invalidate();
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
        else openLid();
    }

    private void closeLid() {
        for (int i = 0 - bpLid.getHeight() / 2; i <= height * 57 / 100; i += 3) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            midPoint.y = i;
            postInvalidate();
            if (isLidOpened) {
                if (i >= (height * 57 / 100) - 3) {
                    isLidOpened = false;
                    Task.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            Media.playShortSound(getContext(), R.raw.open_close);
                            onDrawLidUpdate.onLidChanged(isLidOpened);
                        }
                    });
                }
            }
        }
    }

    private void openLid() {
        isLidOpened = true;
        Task.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                Media.playShortSound(getContext(), R.raw.open_close);
                onDrawLidUpdate.onLidChanged(isLidOpened);
            }
        });
        for (int i = height * 57 / 100; i >= 0 - bpLid.getHeight() / 2; i -= 3) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            midPoint.y = i;
            postInvalidate();
        }
    }
}
