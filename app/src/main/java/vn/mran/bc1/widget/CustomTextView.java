package vn.mran.bc1.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by chi on 26/08/15.
 */
public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context,attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context,attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        super.setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle) {
            case Typeface.BOLD:
                return FontCache.getTypeface("fonts/Action_Man_Bold.ttf", context);

            case Typeface.ITALIC:
                return FontCache.getTypeface("fonts/Action_Man_Italic.ttf", context);

            case Typeface.BOLD_ITALIC:
                return FontCache.getTypeface("fonts/Action_Man_Bold_Italic.ttf", context);

            case Typeface.NORMAL:
            default:
                return FontCache.getTypeface("fonts/Action_Man.ttf", context);
        }
    }
}
