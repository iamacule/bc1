package vn.mran.bc1.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.mran.bc1.R;
import vn.mran.bc1.base.BaseActivity;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.TouchEffect;

/**
 * Created by MrAn PC on 13-Feb-16.
 */
public class ChooserActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "ChooserActivity";
    private ImageView imgPlay;
    private ImageView imgBattle;
    //    private ImageView imgSetting;
    private ImageView imgExit;

    @Override
    public void initLayout() {
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgBattle = (ImageView) findViewById(R.id.imgBattle);
//        imgSetting = (ImageView) findViewById(R.id.imgSetting);
        imgExit = (ImageView) findViewById(R.id.imgExit);
    }

    @Override
    public void initValue() {
        TouchEffect.addAlpha(imgPlay);
        TouchEffect.addAlpha(imgBattle);
//        TouchEffect.addAlpha(imgSetting);
        TouchEffect.addAlpha(imgExit);

        setVersion();
    }

    private void setVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            ((TextView) findViewById(R.id.txtVersion)).setText("v" + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initAction() {
        startAnimation();
        imgPlay.setOnClickListener(this);
        imgBattle.setOnClickListener(this);
//        imgSetting.setOnClickListener(this);
        imgExit.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_chooser;
    }

    private void startAnimation() {
        imgPlay.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgBattle.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
//        imgSetting.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgExit.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
    }

    @Override
    public void onBackPressed() {
        finish();
        Media.stopBackgroundMusic();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPlay:
                startActivity(PlayActivity.class);
                break;
            case R.id.imgBattle:
                startActivity(BattleActivity.class);
                break;
//            case R.id.imgSetting:
////                startActivity(SettingActivity.class);
//                break;
            case R.id.imgExit:
                finish();
                Media.stopBackgroundMusic();
                break;
        }
    }
}
