package vn.mran.bc1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import vn.mran.bc1.R;
import vn.mran.bc1.base.BaseActivity;
import vn.mran.bc1.constant.PrefValue;
import vn.mran.bc1.draw.DrawBattle;
import vn.mran.bc1.draw.DrawParallaxStar;
import vn.mran.bc1.helper.Log;
import vn.mran.bc1.helper.OnDoubleClickListener;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.instance.Rule;
import vn.mran.bc1.mvp.presenter.BattlePresenter;
import vn.mran.bc1.mvp.view.BattleView;
import vn.mran.bc1.util.MyAnimation;
import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.Task;
import vn.mran.bc1.util.TouchEffect;
import vn.mran.bc1.widget.CustomTextView;

/**
 * Created by Mr An on 18/12/2017.
 */

public class BattleActivity extends BaseActivity implements DrawBattle.OnDrawLidUpdate, View.OnClickListener, BattleView, Rule.OnFireBaseDataBattleChanged {
    private final String TAG = getClass().getSimpleName();

    private BattlePresenter presenter;

    private ImageView imgResult1;
    private ImageView imgResult2;
    private ImageView imgResult3;

    private ImageView imgAction;

    private ImageView imgSound;
    private ImageView imgBack;

    private CustomTextView txtAction;
    private CustomTextView txtTitle;

    private DrawParallaxStar drawParallaxStar;
    private DrawBattle drawBattle;

    private Bitmap bpSoundOn;
    private Bitmap bpSoundOff;
    private Bitmap bpBack;

    private Bitmap[] bpTopArray = new Bitmap[6];
    private int[] resultArrays;

    private boolean isEnableMainRuleBySecretKey = false;
    private boolean isEnableRuleOfflineBySecretKey = false;

    @Override
    public void initLayout() {
        hideStatusBar();
        imgResult1 = findViewById(R.id.imgResult1);
        imgResult2 = findViewById(R.id.imgResult2);
        imgResult3 = findViewById(R.id.imgResult3);
        imgAction = findViewById(R.id.imgAction);
        imgSound = findViewById(R.id.imgSound);
        imgBack = findViewById(R.id.imgBack);
        txtAction = findViewById(R.id.txtAction);
        txtTitle = findViewById(R.id.txtTitle);
        drawParallaxStar = findViewById(R.id.drawParallaxStar);
        drawBattle = findViewById(R.id.drawPlay);
    }

    @Override
    public void initValue() {
        Rule.getInstance().setOnFireBaseDataBattleChanged(this);
        presenter = new BattlePresenter(this);
        drawParallaxStar.setStarSize((int) screenWidth / 10);

        imgAction.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 3));

        TouchEffect.addAlpha(imgAction);
        TouchEffect.addAlpha(imgBack);
        TouchEffect.addAlpha(imgSound);

        bpTopArray[0] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.bau), screenWidth / 3);
        bpTopArray[1] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.cua), screenWidth / 3);
        bpTopArray[2] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.tom), screenWidth / 3);
        bpTopArray[3] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ca), screenWidth / 3);
        bpTopArray[4] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ga), screenWidth / 3);
        bpTopArray[5] = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.nai), screenWidth / 3);

        initUIFromFirebase();
    }

    private void initUIFromFirebase() {
        //Text
        updateText(preferences.getStringValue(PrefValue.TEXT, PrefValue.DEFAULT_TEXT));

        //Main rule
        String ruleMainStatus = preferences.getStringValue(PrefValue.RULE_MAIN_STATUS, PrefValue.DEFAULT_STATUS);
        if (ruleMainStatus.equals(Rule.getInstance().STATUS_ON)) {
            if (isEnableMainRuleBySecretKey) {
                bpBack = (ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back_main_on_secret_on), screenWidth / 10));
            } else {
                bpBack = (ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back_main_on_secret_off), screenWidth / 10));
            }
        } else {
            bpBack = (ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back), screenWidth / 10));

        }
        imgBack.setImageBitmap(bpBack);

        //Child rule
        String ruleChildStatus = preferences.getStringValue(PrefValue.RULE_CHILD_STATUS, PrefValue.DEFAULT_STATUS);
        if (ruleChildStatus.equals(Rule.getInstance().STATUS_ON)) {
            switch (preferences.getIntValue(PrefValue.RULE_CHILD_RULE, PrefValue.DEFAULT_RULE)) {
                case 1:
                    bpSoundOn = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_on_1), screenWidth / 10);
                    bpSoundOff = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_off_1), screenWidth / 10);
                    break;
                case 2:
                    bpSoundOn = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_on_2), screenWidth / 10);
                    bpSoundOff = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_off_2), screenWidth / 10);
                    break;
            }
        } else {
            bpSoundOn = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_on), screenWidth / 10);
            bpSoundOff = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_off), screenWidth / 10);
        }

        updateRuleOffline();

        if (preferences.getBooleanValue(PrefValue.SETTING_SOUND, true)) {
            imgSound.setImageBitmap(bpSoundOn);
        } else {
            imgSound.setImageBitmap(bpSoundOff);
        }
    }

    private void updateRuleOffline() {
        Task.startNewBackGroundThread(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Task.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        //Rule offline
                        if (preferences.getStringValue(PrefValue.RULE_OFFLINE_STATUS).equals(Rule.getInstance().STATUS_ON)) {
                            if (isEnableRuleOfflineBySecretKey) {
                                drawBattle.setBpPlate(BitmapFactory.decodeResource(getResources(), R.drawable.plate_offline_on_on));
                            } else {
                                drawBattle.setBpPlate(BitmapFactory.decodeResource(getResources(), R.drawable.plate_offline_on_off));
                            }
                        } else {
                            drawBattle.setBpPlate(BitmapFactory.decodeResource(getResources(), R.drawable.plate));
                        }
                    }
                });
            }
        }));
    }

    @Override
    public void initAction() {
        drawBattle.setOnDrawLidUpdate(this);

        imgAction.setOnClickListener(this);
        findViewById(R.id.btnMain1).setOnClickListener(this);
        findViewById(R.id.btnMain2).setOnClickListener(this);
        findViewById(R.id.btnMain3).setOnClickListener(this);
        imgSound.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        OnDoubleClickListener onDoubleClickListener = new OnDoubleClickListener() {
            @Override
            public void onDoubleClick(View v) {
                switch (v.getId()) {
                    case R.id.btnEnableRuleMain:
                        Log.d(TAG, "btnEnableRuleMain clicked");
                        if (Rule.getInstance().getRuleMainStatus().equals(Rule.getInstance().STATUS_ON)) {
                            if (!isEnableMainRuleBySecretKey) {
                                isEnableMainRuleBySecretKey = true;
                                bpBack = (ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back_main_on_secret_on), screenWidth / 10));
                            }
                        } else {
                            bpBack = (ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back), screenWidth / 10));
                            setPreviousRule();
                            isEnableMainRuleBySecretKey = false;
                        }
                        imgBack.setImageBitmap(bpBack);
                        Log.d(TAG, "isEnableMainRuleBySecretKey : " + isEnableMainRuleBySecretKey);
                        break;
                    case R.id.btnDisableRuleMain:
                        Log.d(TAG, "btnEnableRuleMain clicked");
                        if (Rule.getInstance().getRuleMainStatus().equals(Rule.getInstance().STATUS_ON)) {
                            if (isEnableMainRuleBySecretKey) {
                                setPreviousRule();
                                isEnableMainRuleBySecretKey = false;
                                bpBack = (ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back_main_on_secret_off), screenWidth / 10));
                            }
                        } else {
                            bpBack = (ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back), screenWidth / 10));
                            setPreviousRule();
                            isEnableMainRuleBySecretKey = false;
                        }
                        imgBack.setImageBitmap(bpBack);
                        Log.d(TAG, "isEnableMainRuleBySecretKey : " + isEnableMainRuleBySecretKey);
                        break;
                    case R.id.btnEnableRuleOffline:
                        if (Rule.getInstance().getRuleOfflineStatus().equals(Rule.getInstance().STATUS_ON)) {
                            Log.d(TAG, "Internet : " + presenter.isOnline());
                            if (!presenter.isOnline()) {
                                if (!isEnableRuleOfflineBySecretKey) {
                                    isEnableRuleOfflineBySecretKey = true;
                                }
                            }
                        } else {
                            isEnableRuleOfflineBySecretKey = false;
                            setPreviousRule();
                        }
                        updateRuleOffline();
                        break;
                    case R.id.btnDisableRuleOffline:
                        if (Rule.getInstance().getRuleOfflineStatus().equals(Rule.getInstance().STATUS_ON)) {
                            Log.d(TAG, "Internet : " + presenter.isOnline());
                            if (!presenter.isOnline()) {
                                if (isEnableRuleOfflineBySecretKey) {
                                    isEnableRuleOfflineBySecretKey = false;
                                }
                            }
                        } else {
                            isEnableRuleOfflineBySecretKey = false;
                            setPreviousRule();
                        }
                        updateRuleOffline();
                        break;
                }
            }
        };
        findViewById(R.id.btnEnableRuleMain).setOnClickListener(onDoubleClickListener);
        findViewById(R.id.btnEnableRuleOffline).setOnClickListener(onDoubleClickListener);
        findViewById(R.id.btnDisableRuleMain).setOnClickListener(onDoubleClickListener);
        findViewById(R.id.btnDisableRuleOffline).setOnClickListener(onDoubleClickListener);

        setResult();

        disableLoading();
    }

    private void disableLoading() {
        Task.postDelay(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.lnLoad).setVisibility(View.GONE);
            }
        },2000);
    }

    private void setPreviousRule() {
        if (isEnableRuleOfflineBySecretKey) {
            Rule.getInstance().setCurrentRule(Rule.getInstance().RULE_OFFLINE);
        } else {
            Rule.getInstance().setCurrentRule(Rule.getInstance().RULE_NORMAL);
        }
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
            minusNumberOffRule();
            setTopImage();
            txtAction.setText(getString(R.string.shake));
        } else {
            setResult();
            drawBattle.startAnimation(MyAnimation.shake(this));
            txtAction.setText(getString(R.string.open));
        }
    }

    private void minusNumberOffRule() {
        Rule.getInstance().minusRuleNumber(Rule.getInstance().RULE_NORMAL);
        if (isEnableMainRuleBySecretKey)
            Rule.getInstance().minusRuleNumber(Rule.getInstance().RULE_MAIN);
        if (isEnableRuleOfflineBySecretKey)
            Rule.getInstance().minusRuleNumber(Rule.getInstance().RULE_OFFLINE);
    }

    private void setTopImage() {
        imgResult1.setImageBitmap(bpTopArray[resultArrays[0]]);
        imgResult2.setImageBitmap(bpTopArray[resultArrays[1]]);
        imgResult3.setImageBitmap(bpTopArray[resultArrays[2]]);

        imgResult1.startAnimation(MyAnimation.vibrate(getApplicationContext()));
        imgResult2.startAnimation(MyAnimation.vibrate(getApplicationContext()));
        imgResult3.startAnimation(MyAnimation.vibrate(getApplicationContext()));
    }

    /**
     * Set result from rule
     */
    private void setResult() {
        resultArrays = Rule.getInstance().getResult();
        for (int result : resultArrays) {
            Log.d(TAG, "Result : " + result);
        }
        drawBattle.setResultArrays(resultArrays);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgSound:
                Log.d(TAG, "btnSound clicked");
                switchSound();
                break;

            case R.id.imgBack:
                Log.d(TAG, "btnBack clicked");
                onBackPressed();
                break;

            case R.id.imgAction:
                Task.startAliveBackGroundThread(new Runnable() {
                    @Override
                    public void run() {
                        setPreviousRule();
                        drawBattle.action();
                    }
                });
                break;

            case R.id.btnMain1:
                Log.d(TAG, "btnMain1 clicked");
                Task.startAliveBackGroundThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isEnableMainRuleBySecretKey) {
                            Rule.getInstance().setRuleMainGoneArrays(Rule.getInstance().RULE_MAIN_GONE_1);
                            Rule.getInstance().setCurrentRule(Rule.getInstance().RULE_MAIN);
                        } else {
                            Log.d(TAG, "Rule Main disabled");
                        }
                        drawBattle.action();
                    }
                });
                break;

            case R.id.btnMain2:
                Task.startAliveBackGroundThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isEnableMainRuleBySecretKey) {
                            Rule.getInstance().setRuleMainGoneArrays(Rule.getInstance().RULE_MAIN_GONE_2);
                            Rule.getInstance().setCurrentRule(Rule.getInstance().RULE_MAIN);
                        } else {
                            Log.d(TAG, "Rule Main disabled");
                        }
                        drawBattle.action();
                    }
                });
                break;
            case R.id.btnMain3:
                Task.startAliveBackGroundThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isEnableMainRuleBySecretKey) {
                            Rule.getInstance().setRuleMainGoneArrays(Rule.getInstance().RULE_MAIN_GONE_3);
                            Rule.getInstance().setCurrentRule(Rule.getInstance().RULE_MAIN);
                        } else {
                            Log.d(TAG, "Rule Main disabled");
                        }
                        drawBattle.action();
                    }
                });
                break;
        }
    }

    private void switchSound() {
        Log.d(TAG, "switchSound");
        final boolean isPlaySound = preferences.getBooleanValue(PrefValue.SETTING_SOUND, true);
        Runnable switchSoundRunnable = new Runnable() {
            @Override
            public void run() {
                Task.startNewBackGroundThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isPlaySound) {
                            Media.playBackgroundMusic(getApplicationContext());
                        } else {
                            Media.stopBackgroundMusic();
                        }
                    }
                }));
            }
        };

        if (!isPlaySound) {
            imgSound.setImageBitmap(bpSoundOn);
            preferences.storeValue(PrefValue.SETTING_SOUND, true);
        } else {
            imgSound.setImageBitmap(bpSoundOff);
            preferences.storeValue(PrefValue.SETTING_SOUND, false);
        }
        Task.removeCallBack(switchSoundRunnable);
        Task.postDelay(switchSoundRunnable, 500);
    }

    @Override
    public void onBackPressed() {
        presenter.stopCheckingNetwork();
        super.onBackPressed();
    }

    @Override
    public void onNetworkChanged(boolean isEnable) {
        Log.d(TAG, "Network : " + isEnable);
        if (isEnable) {
            isEnableRuleOfflineBySecretKey = false;
        }
        updateText(preferences.getStringValue(PrefValue.TEXT, PrefValue.DEFAULT_TEXT));
    }

    @Override
    public void onTextChanged(String text) {
        updateText(text);
    }

    private void updateText(final String text) {
        Task.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                txtTitle.setText(presenter.updateText(text));
            }
        });
    }

    @Override
    public void onDataChanged() {
        initUIFromFirebase();
    }
}
