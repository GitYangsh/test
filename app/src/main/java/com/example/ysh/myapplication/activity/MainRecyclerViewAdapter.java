package com.example.ysh.myapplication.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ysh.myapplication.R;

import java.util.List;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @date 2016/09/17
 */

class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>
        implements View.OnClickListener {
    private Context mContext;
    private List<String> mData;
    private OnChildClickerListener mOnChildClickerListener;
    private RecyclerView mRecyclerView;

    MainRecyclerViewAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
    }

    public void setOnChildClickerListener(OnChildClickerListener listener) {
        mOnChildClickerListener = listener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("==onCreateViewHolder(ViewGroup parent, int viewType)");
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_main, parent, false);
        view.setOnClickListener(this);
        return new MainViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        System.out.println("==onAttachedToRecyclerView(RecyclerView recyclerView)");
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        System.out.println("==onDetachedFromRecyclerView(RecyclerView recyclerView)");
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        System.out.println("==onBindViewHolder(MainViewHolder holder, int position)");
        holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mRecyclerView != null && mOnChildClickerListener != null) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            mOnChildClickerListener.onChildClick(mRecyclerView, v, position, mData.get(position));
        }
    }

    public void removeChild(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void addChild(int position, String data) {
        mData.add(position, data);
        notifyItemInserted(position);
    }

    public void changeChild(int position, String data) {
        mData.remove(position);
        mData.add(position, data);
        notifyItemChanged(position);
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        MainViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

    interface OnChildClickerListener {
        void onChildClick(RecyclerView parent, View view, int position, String data);
    }
}
