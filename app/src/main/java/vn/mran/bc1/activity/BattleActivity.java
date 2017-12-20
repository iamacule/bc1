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

        Bitmap bpRersult1 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.tom), screenWidth / 5);
        Bitmap bpRersult2 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.nai), screenWidth / 5);
        Bitmap bpRersult3 = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ga), screenWidth / 5);
        imgResult1.setImageBitmap(bpRersult1);
        imgResult2.setImageBitmap(bpRersult2);
        imgResult3.setImageBitmap(bpRersult3);
    }

    @Override
    public void initAction() {
        drawLid.setOnDrawLidUpdate(this);

        imgAction.setOnClickListener(this);
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
            txtAction.setText(getString(R.string.shake));
        } else {
            findViewById(R.id.frCenter).startAnimation(MyAnimation.shake(this));
            txtAction.setText(getString(R.string.open));
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
