package vn.mran.bc1.activity;

import android.widget.CompoundButton;
import android.widget.ToggleButton;

import vn.mran.bc1.R;
import vn.mran.bc1.base.BaseActivity;
import vn.mran.bc1.constant.PrefValue;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.util.Task;

/**
 * Created by Mr An on 28/11/2017.
 */

public class SettingActivity extends BaseActivity {
    private ToggleButton togSound;

    private boolean playSound;
    private Runnable togSoundRunnable = new Runnable() {
        @Override
        public void run() {
            Task.startNewBackGroundThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    if (playSound) {
                        Media.playBackgroundMusic(getApplicationContext());
                    } else {
                        Media.stopBackgroundMusic();
                    }
                }
            }));
        }
    };

    @Override
    public void initLayout() {
        togSound = findViewById(R.id.togSound);
    }

    @Override
    public void initValue() {
        playSound = preferences.getBooleanValue(PrefValue.SETTING_SOUND, true);
        togSound.setChecked(playSound);
    }

    @Override
    public void initAction() {
        togSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.storeValue(PrefValue.SETTING_SOUND, b);
                playSound = b;
                Task.removeCallBack(togSoundRunnable);
                Task.postDelay(togSoundRunnable,500);
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }
}
