package com.ysh.appmarket.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ysh.appmarket.R;
import com.ysh.appmarket.fragment.AppFragment;
import com.ysh.appmarket.fragment.DiscoveryFragment;
import com.ysh.appmarket.fragment.GameFragment;
import com.ysh.appmarket.fragment.TopsFragment;
import com.ysh.appmarket.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initView();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_user);
        setSupportActionBar(toolbar);

        LinearLayout searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        searchLayout.setOnClickListener(this);

    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.action_app_update:
                        break;
                    case R.id.action_pkg_installed:
                        break;
                    case R.id.action_app_installed:
                        break;
                    case R.id.action_feedback:
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mViewPager = (NoScrollViewPager) findViewById(R.id.container);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DiscoveryFragment());
        fragmentList.add(new TopsFragment());
        fragmentList.add(new GameFragment());
        fragmentList.add(new AppFragment());
        mViewPager.setOffscreenPageLimit(fragmentList.size() - 1);
        mViewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.action_discovery:
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.action_tops:
                        mViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.action_game:
                        mViewPager.setCurrentItem(2, true);
                        break;
                    case R.id.action_app:
                        mViewPager.setCurrentItem(3, true);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


    }

    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList;

        public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_download:
                Toast.makeText(this, "下载", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_layout:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
