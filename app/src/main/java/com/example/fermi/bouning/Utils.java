package com.example.fermi.bouning;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Harris on 2016/6/14.
 */

public class Utils {
    //显示键盘
    public static void showKeyBoard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //隐藏键盘
    public static void hideKeyBoard(Activity activity, View view) {
        //1.得到InputMethodManager对象
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //2.调用hideSoftInputFromWindow方法隐藏软键盘
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
}
