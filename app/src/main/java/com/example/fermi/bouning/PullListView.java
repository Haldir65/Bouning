package com.example.fermi.bouning;

/**
 * Created by Fermi on 2016/6/13.
 * Time: 21:32
 * Author: mjw090608@gmail.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 自定义阻尼效果列表
 **/
public class PullListView extends ListView implements Runnable, AbsListView.OnScrollListener {
    private float mLastDownY = 0f;
    private int mDistance = 0;
    private int mStep = 0;
    private boolean mPositive = false;
    private String Tag = "PullListview";
    private onListViewScrollListener onListViewScrollListener;

    private onPullUpListener onPullUpListener;

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    public PullListView(Context context) {
        super(context);
        init();

    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if (!(l instanceof PullListView)) {
            throw new IllegalStateException("onScrollListener已经被本ListView自己处理了，不要另外设置，谢谢！！！");
        }
        super.setOnScrollListener(l);
    }

    private void init() {
        setOnScrollListener(this);
    }

    private int curTouchState;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //手指按下时触发
                //     Log.d(Tag,"ActionDown");
                LogUtil.e("ActionDown!!!!!");
                curTouchState = 0;
                if (mLastDownY == 0f && mDistance == 0) {
                    //stopScroll();
                    if (CurrentScrollState == SCROLL_STATE_FLING) {
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (curTouchState == 0) {
                                    //stopScroll();
                                }
                            }
                        }, 1000);
                    }
                    mLastDownY = event.getY();
                    //     Log.d(Tag,"mLastDownY赋值"+mLastDownY);
                    return true;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP: //手指抬起之后触发
                curTouchState = 2;

                //   Log.d(Tag,"ActionUP");
                LogUtil.e("ActionUpUpYpUpUp!!!!!");
                if (mDistance != 0) {
                    //  System.out.println("---post");
                    mStep = 1;
                    mPositive = (mDistance >= 0);
                    this.post(this);
                    return true;
                }
                mLastDownY = 0f;
                mDistance = 0;
                break;

            case MotionEvent.ACTION_MOVE:  //手指按下之后滑动触发
                curTouchState = 1;
                //   Log.d(Tag,"ActionMove");
                if (mLastDownY != 0f) {
                    mDistance = (int) (mLastDownY - event.getY());//mDistance表示的是总的向下滑动的距离
                    // LogUtil.e("mLastDownY = " + mLastDownY + " mDistance " + mDistance + " event.getY() =" + event.getY() + " mDistance = " + mDistance);
                    if (onListViewScrollListener != null) {
                        onListViewScrollListener.onDistanceChanged(mDistance);
                    }
                    if ((mDistance < 0 && getFirstVisiblePosition() == 0 &&
                            getChildAt(0).getTop() == 0) || (mDistance > 0 &&
                            getLastVisiblePosition() == getCount() - 1)) {//这段就是弹簧的效果！！！
                        //第一个位置并且是想下拉，就滑动或者最后一个位置向上拉
                        //这个判断的作用是在非顶端的部分不会有此滚动
                        // mDistance /= 2; //这里是为了减少滚动的距离
                        if (onPullUpListener != null) {
                            onPullUpListener.onPullUp(mDistance);
                        }
                        scrollTo(0, mDistance); //滚动
                        return true;
                    }
                }
                mDistance = 0;
                break;
        }
        return super.onTouchEvent(event);
    }

    public void run() {
        //  Log.d(Tag,"ActionUP调用post");
        mDistance += mDistance > 0 ? -mStep : mStep;
        scrollTo(0, mDistance);
        if ((mPositive && mDistance <= 0) || (!mPositive && mDistance >= 0)) {//这里其实listView的onScrollListener收不到坚挺
            scrollTo(0, 0);
            hasLastScrollFinished = true;
            mDistance = 0;
            mLastDownY = 0f;
            //   Log.d(Tag,"post中置0");
            return;
        }
        mStep += 1;
        // this.postDelayed(this, 10);
        this.post(this);
    }

    private boolean hasLastScrollFinished;

    public int getmDistance() {
        return mDistance;
    }

    public void setCurrentScrollState(int state) {
        CurrentScrollState = state;
    }

    private int CurrentScrollState;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                //  LogUtil.e("Flinging");
                setCurrentScrollState(SCROLL_STATE_FLING);
                break;
            case SCROLL_STATE_IDLE:
                // LogUtil.e("IDLE!!");
                setCurrentScrollState(SCROLL_STATE_IDLE);
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                setCurrentScrollState(SCROLL_STATE_TOUCH_SCROLL);
                //  LogUtil.e("TOUCH SCROLL!");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //  LogUtil.e("firstVisibleItem= " + firstVisibleItem + " visibleItemCount " + visibleItemCount + " totalItemCount " + totalItemCount);
        LogUtil.e("ListView" + ((curTouchState == 1) ? "被用户拖拽滑动" : "自己随着惯性滑动"));
    }

    public interface onPullUpListener {
        void onPullUp(int distance);//这个随着手指向上是一直变大的
    }

    public interface onListViewScrollListener {
        void onDistanceChanged(int distance);
    }

    public void setPullUpListener(onPullUpListener listener) {
        this.onPullUpListener = listener;
    }

    public void setOnListViewScrollListener(PullListView.onListViewScrollListener onListViewScrollListener) {
        this.onListViewScrollListener = onListViewScrollListener;
    }

    public void stopScroll() {
        //  removeCallbacks(this);
        smoothScrollBy(0, 0);
    }
}
