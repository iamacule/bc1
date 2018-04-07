package vn.mran.bc1.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import vn.mran.bc1.R;
import vn.mran.bc1.base.BaseFragment;
import vn.mran.bc1.base.FragmentNavigator;
import vn.mran.bc1.fragment.ChooserFragment;
import vn.mran.bc1.fragment.SplashFragment;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.instance.Rule;

/**
 * Created by Mr An on 23/03/2018.
 */

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private FragmentNavigator fragmentNavigator;
    public Media media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Rule.init(getApplicationContext());
        initFragmentNavigator();
        hideStatusBar();
        media = new Media(this);
    }

    public void onBackPressed() {
        if (fragmentNavigator.getActiveFragment() instanceof ChooserFragment) {
            moveTaskToBack(true);
        } else {
            fragmentNavigator.goOneBack();
        }
    }

    public void goTo(BaseFragment baseFragment) {
        fragmentNavigator.goTo(baseFragment);
    }

    public Fragment getActiveFragment() {
        return fragmentNavigator.getActiveFragment();
    }

    private void initFragmentNavigator() {
        fragmentNavigator = new FragmentNavigator(getSupportFragmentManager(), R.id.container, R.anim.slide_in_left, R.anim.slide_in_right,
                R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentNavigator.setRootFragment(new SplashFragment());
    }

    public void hideStatusBar() {
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

    @Override
    protected void onStart() {
        super.onStart();
        media.pausePlayBackgroundMusic();
    }

    @Override
    protected void onStop() {
        media.pausePlayBackgroundMusic();
        super.onStop();
    }

    public  void startBackgroundMusic(){
        media.playBackgroundMusic();
    }
}
