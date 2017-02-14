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

public class Song implements Item{

    @NonNull public String name;
    @NonNull public int imgId;

    public Song(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }
}
