package com.example.fermi.bouning;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Harris on 2016/4/18.
 */
public class LogUtil {
    private static final boolean DEBUG = true;

    public static void d(String TAG, String method, String msg) {
        Log.d(TAG, "[" + method + "]" + msg);
    }

    public static void d(String TAG, String msg){
        if (DEBUG) {
            Log.d(TAG, "[" + getFileLineMethod() + "]" + msg);
        }
    }

    public static void d(String TAG, String msg,Exception e){
        if (DEBUG) {
            Log.d(TAG, "[" + getFileLineMethod() + "]" + msg, e);
        }
    }

    public static void d(String msg){
        if (DEBUG) {
            Log.d(_FILE_(), "[" + getLineMethod() + "]" + msg);
        }
    }


    public static void e(String msg){
        if (DEBUG) {
            Log.e(_FILE_(), getLineMethod() + msg);
        }
    }

    public static void e(String TAG, String msg){
        if (DEBUG) {
            Log.e(TAG, getLineMethod() + msg);
        }
    }

    public static void e(String TAG, String msg,Exception e){
        if (DEBUG) {
            Log.e(TAG, getLineMethod() + msg,e);
        }
    }

    public static void p(String msg){
        if (DEBUG) {
            Log.d(_FILE_(), "[" + getLineMethod() + "] 当前线程Id为 "+Thread.currentThread().getId() +"  "+ msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG) Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG) Log.w(tag, msg);
    }
    public static void w(String tag, String msg,Exception e) {
        if (DEBUG) Log.w(tag, msg,e);
    }


    public static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ")
                .append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }

    public static String getLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(traceElement.getLineNumber()).append(" | ")
                .append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }

    public static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return traceElement.getFileName();
    }

    public static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    public static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }

    public static String _TIME_() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }


    /**
     * @return 调用的class的类名
     */
    private static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }
}