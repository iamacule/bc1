package vn.mran.bc1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import vn.mran.bc1.R;
import vn.mran.bc1.base.BaseActivity;
import vn.mran.bc1.draw.DrawLid;
import vn.mran.bc1.draw.DrawParallaxStar;
import vn.mran.bc1.helper.Log;
import vn.mran.bc1.instance.Rule;
import vn.mran.bc1.util.MyAnimation;
import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.Task;
import vn.mran.bc1.util.TouchEffect;
import vn.mran.bc1.widget.CustomTextView;

/**
 * Created by Mr An on 18/12/2017.
 */

public class BattleActivity extends BaseActivity implements DrawLid.OnDrawLidUpdate, View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    private ImageView imgResult1;
    private ImageView imgResult2;
    private ImageView imgResult3;

    private ImageView imgAction;
    private ImageView imgPlate;

    private CustomTextView txtAction;

    private DrawParallaxStar drawParallaxStar;
    private DrawLid drawLid;

    private Bitmap[] bpTopArray = new Bitmap[6];
    private int[] resultArrays;

    @Override
    public void initLayout() {
        imgResult1 = findViewById(R.id.imgResult1);
        imgResult2 = findViewById(R.id.imgResult2);
        imgResult3 = findViewById(R.id.imgResult3);
        imgAction = findViewById(R.id.imgAction);
        imgPlate = findViewById(R.id.imgPlate);
        txtAction = findViewById(R.id.txtAction);
//        drawParallaxStar = findViewById(R.id.drawParallaxStar);
        drawLid = findViewById(R.id.drawLid);
    }

    @Override
    public void initValue() {

        imgPlate.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenWidth * 8 / 10));

        imgAction.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        TouchEffect.addAlpha(imgAction);

//        drawParallaxStar.setStarSize((int)screenWidth/10);
        drawLid.setLidSize((int) screenWidth * 8 / 10);

        bpTopArray[0] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.bau), screenWidth / 5);
        bpTopArray[1] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.cua), screenWidth / 5);
        bpTopArray[2] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.tom), screenWidth / 5);
        bpTopArray[3] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ca), screenWidth / 5);
        bpTopArray[4] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ga), screenWidth / 5);
        bpTopArray[5] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.nai), screenWidth / 5);
    }

    @Override
    public void initAction() {
        drawLid.setOnDrawLidUpdate(this);

        imgAction.setOnClickListener(this);

        //Set result at first time
        setResult();
        findViewById(R.id.frCenter).startAnimation(MyAnimation.shake(this));
    }

    @Override
    public int setLayout() {
        return R.layout.activity_battle;
    }

    @Override
    public void onTouch() {
        hideStatusBar();
    }

    @Override
    public void onLidChanged(boolean isOpened) {
        Log.d(TAG, "isLidOpened : " + isOpened);
        if (isOpened) {
            setTopImage();
            txtAction.setText(getString(R.string.shake));
        } else {
            setResult();
            findViewById(R.id.frCenter).startAnimation(MyAnimation.shake(this));
            txtAction.setText(getString(R.string.open));
        }
    }

    private void setTopImage() {
        imgResult1.setImageBitmap(bpTopArray[resultArrays[0]]);
        imgResult2.setImageBitmap(bpTopArray[resultArrays[1]]);
        imgResult3.setImageBitmap(bpTopArray[resultArrays[2]]);
    }

    /**
     * Set result from rule
     */
    private void setResult() {
        resultArrays = Rule.getInstance().getResult();
        for (int result : resultArrays) {
            Log.d(TAG, "Result : " + result);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgAction:
                Task.startAliveBackGroundThread(new Runnable() {
                    @Override
                    public void run() {
                        drawLid.action();
                    }
                });
                break;
        }
    }
}
