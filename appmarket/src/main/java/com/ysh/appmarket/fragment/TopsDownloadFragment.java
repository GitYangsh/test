package com.ysh.appmarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysh.appmarket.R;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/18 22:38
 * Description:
 */

public class TopsDownloadFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);

        return view;
    }
}
