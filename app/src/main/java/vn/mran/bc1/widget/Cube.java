package vn.mran.bc1.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr An on 13/12/2017.
 */

public class Cube extends View {

    private final String TAG = getClass().getSimpleName();

    private List<Line> drawCubeList;
    private Bitmap[] animalArrayRight;
    private Bitmap[] animalArrayLeft;
    private Rect[] animalRect = new Rect[3];
    private int[] posAnimalVisible = new int[3];
    private int[] assignNumberAnimalArray = new int[]{0, 1, 2, 3, 4, 5};
    private Point[] listMidPoint = new Point[3];
    private Paint linePaint;

    private int minWidth;
    private int maxWidth;
    private int minHeight;
    private int maxHeight;

    public Cube(Context context) {
        super(context);
        init();
    }

    public Cube(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        drawCubeList = new ArrayList<>();

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        linePaint.setStrokeWidth(w / 70);
        minWidth = (w / 70);
        maxWidth = w - (w / 70);
        minHeight = (w / 70);
        maxHeight = h - (w / 70);

        drawCubeList.add(new Line(minWidth, maxHeight / 3, maxWidth / 2, minHeight));
        drawCubeList.add(new Line(maxWidth / 2, minHeight, maxWidth, maxHeight / 3));
        drawCubeList.add(new Line(maxWidth, maxHeight / 3, maxWidth / 2, maxHeight * 2 / 3));
        drawCubeList.add(new Line(maxWidth / 2, maxHeight * 2 / 3, minWidth, maxHeight / 3));
        drawCubeList.add(new Line(minWidth, maxHeight / 3, minWidth, maxHeight * 2 / 3));
        drawCubeList.add(new Line(maxWidth, maxHeight / 3, maxWidth, maxHeight * 2 / 3));
        drawCubeList.add(new Line(minWidth, maxHeight * 2 / 3, maxWidth / 2, maxHeight));
        drawCubeList.add(new Line(maxWidth / 2, maxHeight, maxWidth, maxHeight * 2 / 3));
        drawCubeList.add(new Line(maxWidth / 2, maxHeight * 2 / 3, maxWidth / 2, maxHeight));

        listMidPoint[0] = new Point(maxWidth / 2, maxHeight / 3);

        animalRect[0] = new Rect(listMidPoint[0].x - animalArrayLeft[0].getWidth() / 2,
                listMidPoint[0].y - animalArrayLeft[0].getHeight() / 2,
                listMidPoint[0].x + animalArrayLeft[0].getWidth() / 2,
                listMidPoint[0].y + animalArrayLeft[0].getHeight() / 2);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draw cube
        for (int i = 0; i < drawCubeList.size(); i++)
            canvas.drawLine(drawCubeList.get(i).startX,
                    drawCubeList.get(i).startY,
                    drawCubeList.get(i).stopX,
                    drawCubeList.get(i).stopY,
                    linePaint);

        //Draw animal
        for (int i = 0; i < animalRect.length; i++) {
            if (animalRect[i] != null) {
                boolean b = randomBoolean();

                //True set Right
                if (b) {
                    canvas.drawBitmap(animalArrayRight[posAnimalVisible[i]], null, animalRect[i], null);
                } else {
                    canvas.drawBitmap(animalArrayLeft[posAnimalVisible[i]], null, animalRect[i], null);
                }
            } else {
                Log.d(TAG, "asd");
            }
        }
    }

    /**
     * set Animal list
     */
    public void setAnimal(Bitmap[] animalArraysRight, Bitmap[] animalArraysLeft) {
        this.animalArrayRight = animalArraysRight;
        this.animalArrayLeft = animalArraysLeft;
    }

    /**
     * Set position animal visible in cube
     *
     * @param posAnimalVisible
     */
    public void setPosAnimalVisible(int[] posAnimalVisible) {
        this.posAnimalVisible = posAnimalVisible;
        invalidate();
    }

    private class Line {
        private int startX;
        private int startY;
        private int stopX;
        private int stopY;

        public Line(int startX, int startY, int stopX, int stopY) {
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
        }

        public Line() {
        }
    }

    /**
     * Random boolean
     *
     * @return
     */
    public boolean randomBoolean() {
        return Math.random() < 0.5;
    }
}
