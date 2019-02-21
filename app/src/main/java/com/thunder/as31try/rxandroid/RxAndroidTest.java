package com.thunder.as31try.rxandroid;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Author: heaven
 * Time: 2019/2/21  15:00
 * Description:
 */
public class RxAndroidTest {

    public static void main(String[] args) {
        Observable.just("hello")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s.toUpperCase();
                    }
                }).subscribe(new io.reactivex.functions.Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        User user = new User();
        List<User.Addr> addrs = new ArrayList<>();
        addrs.add(new User.Addr("beijing", "chaoyang"));
        addrs.add(new User.Addr("hebei", "shijiazhuang"));
        addrs.add(new User.Addr("henan", "zhenzhou"));

        user.setAddrs(addrs);
        Observable.just(user)
                //concatMap只要把flatMap替换掉就可以
                .flatMap(new Function<User, ObservableSource<User.Addr>>() {
                    @Override
                    public ObservableSource<User.Addr> apply(User user) throws Exception {
                        return Observable.fromIterable(user.getAddrs());
                    }
                }).subscribe(new io.reactivex.functions.Consumer<User.Addr>() {
            @Override
            public void accept(User.Addr addr) throws Exception {
                System.out.println(addr);
            }
        });
    }
}
