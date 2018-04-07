package vn.mran.bc1.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.mran.bc1.activity.MainActivity;
import vn.mran.bc1.instance.Media;
import vn.mran.bc1.util.Preferences;
import vn.mran.bc1.util.ScreenUtil;

/**
 * Created by Mr An on 23/03/2018.
 */

public abstract class BaseFragment extends Fragment {

    protected float screenWidth;
    protected float screenHeight;
    protected Preferences preferences;

    protected View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(setLayout(), container, false);
        initBaseValue();
        initLayout();
        initValue();
        initAction();
        return v;
    }

    private void initBaseValue() {
        screenWidth = ScreenUtil.getScreenWidth(getActivity().getWindowManager());
        screenHeight = ScreenUtil.getScreenHeight(getActivity().getWindowManager());
        preferences = new Preferences(getContext());
    }


    public abstract void initLayout();

    public abstract void initValue();

    public abstract void initAction();

    protected abstract int setLayout();


    protected void goTo(BaseFragment fragment) {
        ((MainActivity) getActivity()).goTo(fragment);
    }

    protected void finish() {
        ((MainActivity) getActivity()).finish();
    }

    protected void hideStatusBar() {
        ((MainActivity) getActivity()).hideStatusBar();
    }

    protected void onBackPressed() {
        getActivity().onBackPressed();
    }

    protected Media getMedia() {
        return ((MainActivity) getActivity()).media;
    }
}
