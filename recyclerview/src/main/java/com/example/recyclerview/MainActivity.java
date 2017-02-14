package com.example.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements MyRecyclerViewAdapter.OnChildClickerListener {

    MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(String.format("第%02d项数据%s", i, i % 2 == 0 ? "" : "----\n-------\n-------"));
        }
        mAdapter = new MyRecyclerViewAdapter(this, data);
        mAdapter.setOnChildClickerListener(this);
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 3;
                }
                if (position == 1) {
                    return 2;
                }
                return 1;
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//        defaultItemAnimator.setRemoveDuration(3000);
        MyItemAnimator myItemAnimator = new MyItemAnimator();
        myItemAnimator.setChangeDuration(2000);
        recyclerView.setItemAnimator(myItemAnimator);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onChildClick(RecyclerView parent, View view, int position, String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
//        mAdapter.removeChild(position);
//        mAdapter.addChild(position, data);
        mAdapter.changeChild(position, "数据改变");
    }
}
