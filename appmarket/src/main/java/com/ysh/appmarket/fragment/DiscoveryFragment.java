package com.ysh.appmarket.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.app.market.idata.card.Card;
import com.jy.app.market.idata.data.PageCard;
import com.ysh.appmarket.R;
import com.ysh.appmarket.adapter.DiscoveryAdapter;
import com.ysh.appmarket.network.NetClient;
import com.ysh.appmarket.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/18 22:36
 * Description:
 */

public class DiscoveryFragment extends Fragment {
    private static final String TAG = DiscoveryFragment.class.getSimpleName();

    private List<Card> mCardArray = new ArrayList<>();
    private DiscoveryAdapter mDiscoveryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mDiscoveryAdapter = new DiscoveryAdapter(getActivity(), mCardArray);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mDiscoveryAdapter);

        Call<PageCard> call = NetClient.apiService().discovery();
        call.enqueue(new Callback<PageCard>() {
            @Override
            public void onResponse(Call<PageCard> call, Response<PageCard> response) {
                if (response.body() != null) {
                    PageCard data = response.body();
                    List<Card> cards = data.getCards();
                    mDiscoveryAdapter.setData(cards);
                }
            }

            @Override
            public void onFailure(Call<PageCard> call, Throwable t) {
                LogUtil.w(TAG, "failure:" + t.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.d(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d(TAG, "onDetach");
    }
}
