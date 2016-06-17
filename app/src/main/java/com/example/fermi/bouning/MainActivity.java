package com.example.fermi.bouning;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  PullListView.onPullUpListener, PullListView.onListViewScrollListener, View.OnFocusChangeListener {

    static final String[] SAMPLE_DATAS = new String[] { "a", "b", "c", "d",
            "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    private PullListView listview;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (PullListView)findViewById(R.id.list);
        editText = (EditText) findViewById(R.id.et);
        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,getData()));

        listview.setPullUpListener(this);
        listview.setOnListViewScrollListener(this);
        editText.setOnFocusChangeListener(this);
    }
    private List<String> getData(){
        List<String> data = new ArrayList<String>();

        for (int i = 0; i < 100; i++) {
            data.add("阻尼效果测试数据 "+i);
        }

        return data;
    }




    private boolean isKeyBoardShown ;

    @Override
    public void onPullUp(int distance) {
        LogUtil.e("onPullUp! " + distance + " isKeyBoardOn " + isKeyBoardShown);
        if (distance > 200 &&!isKeyBoardShown) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.showKeyBoard(MainActivity.this, editText);
                    isKeyBoardShown = true;
                }
            }, 200);

        }


    }

    @Override
        public void onDistanceChanged(int distance) {
        LogUtil.e("ListView随着手指Move的距离是 " + distance);
        if (distance < -100) {
            if (isKeyBoardShown) {
                Utils.hideKeyBoard(this, editText);
                isKeyBoardShown = false;
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        LogUtil.e("Focus has changed to " + hasFocus);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            //Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
            ToastUtil.showTextShort(this,"keyboard visible");
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            //Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
            ToastUtil.showTextShort(this,"keyboard hidden");
        }
    }
}
