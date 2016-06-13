package com.example.fermi.bouning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MeScrollView listView;

    static final String[] SAMPLE_DATAS = new String[] { "a", "b", "c", "d",
            "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PullListView listview = (PullListView)findViewById(R.id.list);
        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,getData()));
    }
    private List<String> getData(){
        List<String> data = new ArrayList<String>();

        for (int i = 0; i < 100; i++) {
            data.add("阻尼效果测试数据 "+i);
        }

        return data;
    }


}
