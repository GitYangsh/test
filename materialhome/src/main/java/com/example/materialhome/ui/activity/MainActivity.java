package com.example.materialhome.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.materialhome.R;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.content_layout, new MainFragment()).commit();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_e_book:
                break;
            case R.id.nav_bookshelf:
                break;
            case R.id.nav_tools:
                break;
            case R.id.nav_theme:
                break;
            case R.id.nav_send:
                break;
        }
    //    mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
