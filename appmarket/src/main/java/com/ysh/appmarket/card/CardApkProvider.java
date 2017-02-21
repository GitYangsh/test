package com.ysh.appmarket.card;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jy.app.market.idata.card.CardApk;
import com.jy.app.market.idata.data.Apk;
import com.ysh.appmarket.R;
import com.ysh.appmarket.util.IdataUtils;

import me.drakeet.multitype.ItemViewProvider;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/21 16:28
 * Description:
 */

public class CardApkProvider extends ItemViewProvider<CardApk, CardApkProvider.ViewHolder> {

    private Fragment mFragment;

    public CardApkProvider(Fragment fragment) {
        super();
        mFragment = fragment;
    }

    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.app_card_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CardApk cardApk) {
        final Apk apk = cardApk.getApk();
        String iconUrl = IdataUtils.getIconUrl(apk.getIcons());
        Glide.with(mFragment)
                .load(iconUrl)
                .centerCrop()
                .placeholder(R.drawable.default_loading_icon)
                .crossFade()
                .into(holder.appIcon);

        holder.appName.setText(apk.getTitle());

        holder.appDownload.setBackgroundResource(R.drawable.download_button_blue);
        holder.appDownload.setText(R.string.download);
        holder.appDownload.setClickable(true);

        Drawable drawableLeft = mFragment.getResources().getDrawable(R.drawable.download_info);
        holder.appInfo.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        holder.appInfo.setText(apk.getDownloadCountStr() + "人安装  "
                + Formatter.formatFileSize(mFragment.getActivity(), apk.getBytes()));

        String detailStr = apk.getRecommend();
        if (TextUtils.isEmpty(detailStr)) {
            detailStr = apk.getTagline();
            if (TextUtils.isEmpty(detailStr)) {
                detailStr = apk.getDescription();
            }
        }
        holder.appDesc.setText(Html.fromHtml(detailStr));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView appIcon;
        private final TextView appName;
        private final Button appDownload;
        private final TextView appInfo;
        private final TextView appDesc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.appIcon = (ImageView) itemView.findViewById(R.id.app_card_icon);
            this.appName = (TextView) itemView.findViewById(R.id.app_card_name);
            this.appDownload = (Button) itemView.findViewById(R.id.app_card_download_button);
            this.appInfo = (TextView) itemView.findViewById(R.id.app_card_info);
            this.appDesc = (TextView) itemView.findViewById(R.id.app_card_desc);

            this.appDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

    }
}
