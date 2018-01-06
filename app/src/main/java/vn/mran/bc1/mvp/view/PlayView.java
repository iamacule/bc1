package vn.mran.bc1.mvp.view;

/**
 * Created by Mr An on 29/11/2017.
 */

public interface PlayView {
    void onNetworkChanged(boolean isEnable);

    void onTimeChanged(String value);

    void onResultExecute(int tong);

    void onAddMoney();
}
