package vn.mran.bc1.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import vn.mran.bc1.R;
import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.TouchEffect;

/**
 * Created by Mr An on 01/01/2018.
 */

public class MoneyLayout implements View.OnClickListener {

    public interface OnMoneyChanged {
        void onMoneyChanged(int value);

        void onErrorPickMoney();
    }

    private View view;
    private ImageView img100;
    private ImageView img200;
    private ImageView img500;

    private ImageView imgCheck100;
    private ImageView imgCheck200;
    private ImageView imgCheck500;


    private OnMoneyChanged onMoneyChanged;

    private int currentMoney = 0;
    private int currentPick = 0;

    public void setOnMoneyChanged(OnMoneyChanged onMoneyChanged) {
        this.onMoneyChanged = onMoneyChanged;
    }

    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
        if (currentMoney < currentPick) {
            imgCheck100.setVisibility(View.GONE);
            imgCheck200.setVisibility(View.GONE);
            imgCheck500.setVisibility(View.GONE);
        }
    }

    public MoneyLayout(View view, int screenWidth) {
        this.view = view;

        img100 = view.findViewById(R.id.img100);
        img200 = view.findViewById(R.id.img200);
        img500 = view.findViewById(R.id.img500);

        imgCheck100 = view.findViewById(R.id.imgCheck100);
        imgCheck200 = view.findViewById(R.id.imgCheck200);
        imgCheck500 = view.findViewById(R.id.imgCheck500);

        img100.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.vnd_100), screenWidth * 22 / 100));
        img200.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.vnd_200), screenWidth * 22 / 100));
        img500.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.vnd_500), screenWidth * 22 / 100));

        Bitmap bpCheck = ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.check), screenWidth / 15);
        imgCheck100.setImageBitmap(bpCheck);
        imgCheck200.setImageBitmap(bpCheck);
        imgCheck500.setImageBitmap(bpCheck);

        img100.setOnClickListener(this);
        img200.setOnClickListener(this);
        img500.setOnClickListener(this);

        TouchEffect.addAlpha(img100);
        TouchEffect.addAlpha(img200);
        TouchEffect.addAlpha(img500);
    }

    @Override
    public void onClick(View view) {
        if (currentMoney < 100) {
            onMoneyChanged.onErrorPickMoney();
        } else {
            switch (view.getId()) {
                case R.id.img100:
                    imgCheck100.setVisibility(View.VISIBLE);
                    imgCheck200.setVisibility(View.GONE);
                    imgCheck500.setVisibility(View.GONE);
                    currentPick = 100;
                    break;
                case R.id.img200:
                    imgCheck100.setVisibility(View.GONE);
                    imgCheck200.setVisibility(View.VISIBLE);
                    imgCheck500.setVisibility(View.GONE);
                    currentPick = 200;
                    break;
                case R.id.img500:
                    imgCheck100.setVisibility(View.GONE);
                    imgCheck200.setVisibility(View.GONE);
                    imgCheck500.setVisibility(View.VISIBLE);
                    currentPick = 500;
                    break;
            }
            onMoneyChanged.onMoneyChanged(currentPick);
        }
    }
}
