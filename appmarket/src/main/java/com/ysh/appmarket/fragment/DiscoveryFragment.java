package com.ysh.appmarket.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.app.market.idata.card.Card;
import com.jy.app.market.idata.card.CardApk;
import com.jy.app.market.idata.data.PageCard;
import com.ysh.appmarket.R;
import com.ysh.appmarket.card.CardApkProvider;
import com.ysh.appmarket.network.NetClient;
import com.ysh.appmarket.util.LogUtil;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
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

public class DiscoveryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = DiscoveryFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MultiTypeAdapter mMultiTypeAdapter;
    private Items mItems;
    private int mPageNo = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);

        mItems = new Items();
        mMultiTypeAdapter = new MultiTypeAdapter(mItems);
        mMultiTypeAdapter.register(CardApk.class, new CardApkProvider(this));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mMultiTypeAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisiblePosition;
            int refreshPosition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisiblePosition == refreshPosition) {
                    getMoreData();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
                refreshPosition = adapter.getItemCount() - 1;
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
        return view;
    }

    @Override
    public void onRefresh() {
        mPageNo = 1;
        getMoreData();
    }

    private void getMoreData() {
        mSwipeRefreshLayout.setRefreshing(true);
        Call<PageCard> call = NetClient.apiService().discovery(mPageNo);
        call.enqueue(new Callback<PageCard>() {
            @Override
            public void onResponse(Call<PageCard> call, Response<PageCard> response) {
                if (response.body() != null) {
                    PageCard data = response.body();
                    List<Card> cards = data.getCards();
                    if (mPageNo == 1) {
                        mItems.clear();
                    }
                    for (Card card : cards) {
                        if (card.getType().equals("CardApk")){
                            mItems.add(card);
                        }
                    }
                    mPageNo++;
                } else {
                    LogUtil.w(TAG, "response.body() is null");
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PageCard> call, Throwable t) {
                LogUtil.w(TAG, "failure:" + t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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
