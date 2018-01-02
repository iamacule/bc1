package vn.mran.bc1.widget;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import vn.mran.bc1.R;
import vn.mran.bc1.util.ResizeBitmap;

/**
 * Created by Mr An on 01/01/2018.
 */

public class AnimalChooserLayout implements View.OnClickListener {

    public interface OnAnimalChooseListener {
        void onChoose(int[] valueArrays);
    }

    private OnAnimalChooseListener onAnimalChooseListener;

    private View view;

    private ImageView imgNai;
    private ImageView imgBau;
    private ImageView imgGa;
    private ImageView imgCa;
    private ImageView imgCua;
    private ImageView imgTom;

    private CustomTextView txtNai;
    private CustomTextView txtBau;
    private CustomTextView txtGa;
    private CustomTextView txtCa;
    private CustomTextView txtCua;
    private CustomTextView txtTom;

    private int valueNai = 0;
    private int valueBau = 0;
    private int valueGa = 0;
    private int valueCa = 0;
    private int valueCua = 0;
    private int valueTom = 0;

    private int maxValue = 0;
    private int currentValue = 0;

    public void setOnAnimalChooseListener(OnAnimalChooseListener onAnimalChooseListener) {
        this.onAnimalChooseListener = onAnimalChooseListener;
    }

    public AnimalChooserLayout(View view, int screenWidth) {
        this.view = view;

        imgNai = view.findViewById(R.id.imgNai);
        imgBau = view.findViewById(R.id.imgBau);
        imgGa = view.findViewById(R.id.imgGa);
        imgCa = view.findViewById(R.id.imgCa);
        imgCua = view.findViewById(R.id.imgCua);
        imgTom = view.findViewById(R.id.imgTom);

        txtNai = view.findViewById(R.id.txtNai);
        txtBau = view.findViewById(R.id.txtBau);
        txtGa = view.findViewById(R.id.txtGa);
        txtCa = view.findViewById(R.id.txtCa);
        txtCua = view.findViewById(R.id.txtCua);
        txtTom = view.findViewById(R.id.txtTom);

        imgNai.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.nai), screenWidth * 28 / 100));
        imgBau.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.bau), screenWidth * 28 / 100));
        imgGa.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.ga), screenWidth * 28 / 100));
        imgCa.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.ca), screenWidth * 28 / 100));
        imgCua.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.cua), screenWidth * 28 / 100));
        imgTom.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(view.getResources(), R.drawable.tom), screenWidth * 28 / 100));

        imgNai.setOnClickListener(this);
        imgBau.setOnClickListener(this);
        imgGa.setOnClickListener(this);
        imgCa.setOnClickListener(this);
        imgCua.setOnClickListener(this);
        imgTom.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (currentValue < maxValue) {
            currentValue = currentValue++;
            switch (view.getId()) {
                case R.id.imgNai:
                    valueNai = valueNai++;
                    txtNai.setText(addDisplayValue(valueNai));

                    break;
                case R.id.imgBau:
                    valueBau = valueBau++;
                    txtBau.setText(addDisplayValue(valueBau));

                    break;
                case R.id.imgGa:
                    valueGa = valueGa++;
                    txtGa.setText(addDisplayValue(valueGa));

                    break;
                case R.id.imgCa:
                    valueCa = valueCa++;
                    txtCa.setText(addDisplayValue(valueCa));

                    break;
                case R.id.imgCua:
                    valueCua = valueCua++;
                    txtCua.setText(addDisplayValue(valueCua));

                    break;
                case R.id.imgTom:
                    valueTom = valueTom++;
                    txtCua.setText(addDisplayValue(valueTom));

                    break;
            }
            onAnimalChooseListener.onChoose(new int[]{valueBau, valueCua, valueTom, valueCa, valueGa, valueNai});
        }
    }

    public void reset(int maxValue) {
        txtNai.setText("");
        txtBau.setText("");
        txtGa.setText("");
        txtCa.setText("");
        txtCua.setText("");
        txtTom.setText("");

        valueBau = 0;
        valueCa = 0;
        valueCua = 0;
        valueGa = 0;
        valueNai = 0;
        valueTom = 0;

        currentValue = 0;
        this.maxValue = maxValue;
    }

    private String addDisplayValue(int value) {
        return "x" + value;
    }
}
