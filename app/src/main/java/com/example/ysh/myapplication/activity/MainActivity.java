package com.example.ysh.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ysh.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainRecyclerViewAdapter.OnChildClickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        List<String> data = new ArrayList<>();
        data.add("NumberProgressBar");
        data.add("Edit");
        data.add("Read");
        data.add("Camera");
        data.add("Camera GLSurface");
        data.add("OpenGL");
        data.add("Short Video");
        data.add("Play Video");

        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this, data);
        adapter.setOnChildClickerListener(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onChildClick(RecyclerView parent, View view, int position, String data) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, NumberProgressBarActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, EditActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ReadActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, CameraActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, CameraGLSurfaceActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, OpenGLActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, ShortVideoActivity.class));
                break;
            case 7:
                Intent intent = new Intent(this, PlayVideoActivity.class);
                intent.putExtra(PlayVideoActivity.EXTRA_VIDEO_PATH, ShortVideoActivity.COMBINE_VIDEO_PATH);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
