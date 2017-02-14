package com.example.multitype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends AppCompatActivity {

    private MultiTypeAdapter mMultiTypeAdapter;
    private Items mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mItems = new Items();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);

        /* 注册类型和 View 的对应关系 */
        mMultiTypeAdapter.register(Category.class, new CategoryViewProvider());
        mMultiTypeAdapter.register(Song.class, new SongViewProvider());

        /* 模拟加载数据，也可以稍后再加载，然后使用
         * adapter.notifyDataSetChanged() 刷新列表 */
        for (int i = 0; i < 20; i++) {
            mItems.add(new Category("Songs"));
            mItems.add(new Song("小艾大人", R.mipmap.ic_launcher));
            mItems.add(new Song("许岑", R.mipmap.ic_launcher));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mMultiTypeAdapter);

    }
}
