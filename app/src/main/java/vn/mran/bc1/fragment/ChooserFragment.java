package vn.mran.bc1.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.mran.bc1.R;
import vn.mran.bc1.base.BaseFragment;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.TouchEffect;

public class ChooserFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = "ChooserActivity";
    private ImageView imgPlay;
    private ImageView imgBattle;
    private ImageView imgExit;

    @Override
    public void initLayout() {
        imgPlay = v.findViewById(R.id.imgPlay);
        imgBattle = v.findViewById(R.id.imgBattle);
        imgExit = v.findViewById(R.id.imgExit);
    }

    @Override
    public void initValue() {
        TouchEffect.addAlpha(imgPlay);
        TouchEffect.addAlpha(imgBattle);
        TouchEffect.addAlpha(imgExit);

        setVersion();
    }

    private void setVersion() {
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            ((TextView) v.findViewById(R.id.txtVersion)).setText("v" + pInfo.versionName);
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
        return R.layout.fragment_chooser;
    }

    private void startAnimation() {
        imgPlay.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgBattle.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgExit.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPlay:
                goTo(new PlayFragment());
                break;
            case R.id.imgBattle:
                goTo(new BattleFragment());
                break;
            case R.id.imgExit:
                finish();
                getMedia().stopBackgroundMusic();
                break;
        }
    }
}
