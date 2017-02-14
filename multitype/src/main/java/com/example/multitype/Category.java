package com.example.multitype;

import android.support.annotation.NonNull;

import me.drakeet.multitype.Item;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @date 2016/11/10
 */

public class Category implements Item {

    @NonNull public String text;

    public Category(@NonNull final String text) {
        this.text = text;
    }

}
