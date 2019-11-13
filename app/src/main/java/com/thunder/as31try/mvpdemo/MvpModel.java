package com.thunder.as31try.mvpdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: heaven
 * Time: 2019/11/13  13:10
 * Description:
 */
public class MvpModel implements MvpContract.Model {
    @Override
    public List<Integer> getData() {
        List<Integer> data;
        data = new ArrayList<>(4);
        data.add(1);
        data.add(10);
        data.add(30);
        data.add(50);
        return data;
    }
}
