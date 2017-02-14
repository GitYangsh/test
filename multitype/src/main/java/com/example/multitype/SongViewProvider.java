package com.example.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @date 2016/11/10
 */

public class SongViewProvider extends ItemViewProvider<Song, SongViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View rootView = inflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull Song song) {
        holder.songName.setText(song.name);
        holder.songImg.setImageResource(song.imgId);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull private final TextView songName;
        @NonNull private final ImageView songImg;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.songName = (TextView) itemView.findViewById(R.id.name);
            this.songImg = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
