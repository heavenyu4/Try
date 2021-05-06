package com.hyw.as31try.mvpdemo;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MvpPresenter implements MvpContract.Presenter {
    MvpContract.View view;
    MvpContract.Model mModel;
    String TAG = "MvpPresenter";

    public MvpPresenter(MvpContract.Model model, MvpContract.View view) {
        this.view = view;
        this.mModel = model;
    }

    @Override
    public void loadMore() {
        List<Integer> data = mModel.getData();

//        Observable<Integer> listObservable = Observable.fromIterable(data);
//        Observable<Long> timeObservable = Observable.interval(3000, TimeUnit.SECONDS);
////        Observable observable = Observable.zip(listObservable, timeObservable, new BiFunction<String, Long, String>() {
////            @Override
////            public String apply(String s, Long aLong) throws Exception {
////                return s;
////            }
////
////
////        };
//        Observable.zip(listObservable, timeObservable, new BiFunction<Integer, Long, String>() {
//            @Override
//            public String apply(Integer integer, Long aLong) throws Exception {
//                view.showProcess(integer);
//                return integer + "";
//            }
//        }).subscribe();

        //循环发送数字
//        Observable.interval(0, 1, TimeUnit.SECONDS)
//                .subscribeOn( Schedulers.io())
////                .compose(this.<Long>bindToLifecycle())   //这个订阅关系跟Activity绑定，Observable 和activity生命周期同步
//                .observeOn( AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        view.showProcess(aLong.intValue());
//                    }
//                });

//        Observable.fromIterable(data)
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        view.showProcess(integer);
//                    }
//                });

        Log.d(TAG, "loadMore: ");
        Observable.fromIterable(data)
              .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }
}
