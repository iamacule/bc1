package vn.mran.bc1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.mran.bc1.util.ResizeBitmap;
import vn.mran.bc1.util.ScreenUtil;
import vn.mran.bc1.widget.Cube;

public class MainActivity extends AppCompatActivity {
    private Cube cube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cube = findViewById(R.id.cube);

        int screenW = (int) ScreenUtil.getScreenWidth(getWindowManager());
        Bitmap bauRight = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.bau_right), screenW / 2);
        Bitmap cuaRight = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.cua_right), screenW / 2);
        Bitmap tomRight = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.tom_right), screenW / 2);
        Bitmap caRight = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ca_right), screenW / 2);
        Bitmap gaRight = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ga_right), screenW / 2);
        Bitmap naiRight = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.nai_right), screenW / 2);

        Bitmap bauLeft = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.bau_left), screenW / 2);
        Bitmap cuaLeft = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.cua_left), screenW / 2);
        Bitmap tomLeft = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.tom_left), screenW / 2);
        Bitmap caLeft = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ca_left), screenW / 2);
        Bitmap gaLeft = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ga_left), screenW / 2);
        Bitmap naiLeft = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.nai_left), screenW / 2);


        cube.setAnimal(new Bitmap[]{bauRight, cuaRight, tomRight, caRight, gaRight, naiRight},
                new Bitmap[]{bauLeft, cuaLeft, tomLeft, caLeft, gaLeft, naiLeft});
        cube.setPosAnimalVisible(new int[]{5, 1, 2});
    }
}
