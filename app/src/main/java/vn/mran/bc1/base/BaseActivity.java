package vn.mran.bc1.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import vn.mran.bc1.util.Preferences;
import vn.mran.bc1.util.ScreenUtil;

public abstract class BaseActivity extends AppCompatActivity {
    protected float screenWidth;
    protected float screenHeight;
    protected Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(setLayout());
        initBaseValue();
        initLayout();
        initValue();
        initAction();
    }

    private void initBaseValue() {
        screenWidth = ScreenUtil.getScreenWidth(getWindowManager());
        screenHeight = ScreenUtil.getScreenHeight(getWindowManager());
        preferences = new Preferences(getApplicationContext());
    }

    public abstract void initLayout();

    public abstract void initValue();

    public abstract void initAction();

    public abstract int setLayout();

    protected void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    protected void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            getSupportActionBar().hide();
        }
    }
}