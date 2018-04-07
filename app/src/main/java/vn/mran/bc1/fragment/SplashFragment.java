package vn.mran.bc1.fragment;

import android.os.Handler;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import vn.mran.bc1.R;
import vn.mran.bc1.base.BaseFragment;
import vn.mran.bc1.constant.PrefValue;
import vn.mran.bc1.instance.Rule;
import vn.mran.bc1.util.MyAnimation;
import vn.mran.bc1.widget.CustomTextView;

public class SplashFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private final Handler handler = new Handler();
    private CustomTextView txtTitle;
    private LinearLayout lnMain;

    @Override
    public void initLayout() {
        txtTitle = v.findViewById(R.id.txtTitle);
        lnMain = v.findViewById(R.id.lnMain);
    }

    @Override
    public void initValue() {
        Rule.init(getContext());
    }

    @Override
    public void initAction() {
        txtTitle.startAnimation(MyAnimation.fadeIn(getActivity()));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = MyAnimation.fadeOut(getActivity());
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (preferences.getBooleanValue(PrefValue.SETTING_SOUND, true)) {
                            getMedia().playBackgroundMusic();
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        lnMain.clearAnimation();
                        lnMain.removeAllViews();
                        goTo(new ChooserFragment());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                lnMain.startAnimation(animation);
            }
        }, 1500);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_splash;
    }
}
