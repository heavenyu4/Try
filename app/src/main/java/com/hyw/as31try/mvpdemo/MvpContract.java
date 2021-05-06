package com.hyw.as31try.mvpdemo;

import java.util.List;

public interface MvpContract {

    interface View{

        void showProcess(int processs);
    }

    interface Presenter{
        void loadMore();
    }

    interface Model {
        List<Integer> getData();
    }

}
