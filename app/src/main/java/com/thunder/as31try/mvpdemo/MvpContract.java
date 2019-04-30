package com.thunder.as31try.mvpdemo;

public interface MvpContract {

    interface View{

        void showProcess(int processs);
    }

    interface Presenter{
        void loadMore();
    }

}
