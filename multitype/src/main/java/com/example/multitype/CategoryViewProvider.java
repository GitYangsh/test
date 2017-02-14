package com.example.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @date 2016/11/10
 */

public class CategoryViewProvider extends ItemViewProvider<Category, CategoryViewProvider.ViewHolder> {

    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull Category category) {
        holder.category.setText(category.text);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull private final TextView category;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.category = (TextView) itemView.findViewById(R.id.category);
        }

    }

}
