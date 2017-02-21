package com.ysh.appmarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysh.appmarket.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/18 22:38
 * Description:
 */

public class TopsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tops, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        List<Pair<Fragment, String>> pairList = new ArrayList<>();
        pairList.add(new Pair<Fragment, String>(new TopsDownloadFragment(), "飙升榜"));
        pairList.add(new Pair<Fragment, String>(new TopsRiseFragment(), "飙升榜"));
        pairList.add(new Pair<Fragment, String>(new TopsPraiseFragment(), "好评榜"));
        viewPager.setAdapter(new TopsFragmentAdapter(getChildFragmentManager(), pairList));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        return view;
    }

    private class TopsFragmentAdapter extends FragmentPagerAdapter {

        List<Pair<Fragment, String>> mPairList;

        public TopsFragmentAdapter(FragmentManager fm, List<Pair<Fragment, String>> pairList) {
            super(fm);
            mPairList = pairList;
        }

        @Override
        public Fragment getItem(int position) {
            return mPairList.get(position).first;
        }

        @Override
        public int getCount() {
            return mPairList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPairList.get(position).second;
        }
    }
}
