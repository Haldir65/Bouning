package com.example.fermi.bouning;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.OverScroller;

import java.util.LinkedList;

/**
 * Created by Fermi on 2016/6/13.
 * Time: 21:11
 * Author: mjw090608@gmail.com
 */
public class BounceListView extends AdapterView<ListAdapter> implements View.OnTouchListener {

    /** Used to identify the source of a log message. */
    static final String TAG = BounceListView.class.getName();

    /** The overshoot the bounds of a scrolling operation. */
    OverScroller mScroller;

    /** The adapter with all the data. */
    ListAdapter mAdapter;

    /** Detects various gestures and events using the supplied MotionEvents. */
    GestureDetector mGestureDetector;

    /** A list of cached (re-usable) item views. */
    final LinkedList<View> mCachedItemViews = new LinkedList<View>();

    /** The adaptor position of the first visible item. */
    int mCurrentTopItemPosition;

    /** The adaptor position of the last visible item. */
    int mCurrentLastItemPosition;

    /**
     * Constructor.
     *
     * @param context
     */
    public BounceListView(Context context) {
        this(context, null);
    }

    /**
     * Constructor.
     *
     * @param context
     * @param attrs
     */
    public BounceListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor.
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public BounceListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        mScroller = new OverScroller(getContext());
        mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        setOnTouchListener(this);
    }

    @Override
    public ListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override
    public void setSelection(int position) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        Log.d(TAG, "computeScroll");
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        Log.d(TAG, "onLayout changed:: " + changed);
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * Checks if there is a cached view that can be used.
     *
     * @return A cached view or, if none was found, null
     */
    View getCachedView() {
        if (mCachedItemViews.size() != 0) {
            mCachedItemViews.removeFirst();
        }
        return null;
    }

    /**
     * Fling the this view.
     *
     * @param velocityY
     */
    void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0,
                Integer.MAX_VALUE);
        invalidate();
    }

    /**
     * Scroll the this view.
     *
     * @param distanceY
     */
    void scroll(int distanceY) {
        scrollBy(0, distanceY);
    }

    /**
     * A convenience class to extend when you only want to listen for a subset
     * of all the gestures.
     */
    final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown y:: " + e.getY());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            Log.d(TAG, "onFling velocityY:: " + velocityY);
            fling((int) velocityY);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            Log.d(TAG, "onScroll distanceY:: " + distanceY);
            scroll((int) distanceY);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp");
            return super.onSingleTapUp(e);
        }
    };
}