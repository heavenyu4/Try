package com.hyw.as31try.testdatabinding;

import android.databinding.ObservableField;
import android.databinding.ObservableFloat;

/**
 * Author: heaven
 * Time: 2019/10/22  15:04
 * Description:
 */
public class ObservableGoods {

    private ObservableField<String> name;

    private ObservableFloat price;


    public ObservableGoods(String name, float price) {
        this.name = new ObservableField<>(name);
        this.price = new ObservableFloat(price);
    }
}
