package com.ysh.appmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jy.app.market.idata.card.Card;
import com.ysh.appmarket.R;

import java.util.List;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/20 10:55
 * Description:
 */

public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.DiscoveryViewHolder>{

    private Context mContext;
    private List<Card> mData;

    public DiscoveryAdapter(Context context, List<Card> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public DiscoveryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_discovery, parent, false);
        return new DiscoveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoveryViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Card> data){
        mData = data;
        notifyDataSetChanged();
    }

    class DiscoveryViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;

        public DiscoveryViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.card_text);
        }
    }
}
