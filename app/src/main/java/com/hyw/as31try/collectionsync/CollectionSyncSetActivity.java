package com.hyw.as31try.collectionsync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hyw.as31try.R;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CollectionSyncSetActivity extends AppCompatActivity {

    String TAG = "CollectionSyncSetActivity";

    Set<Integer> tmpSet = Collections.synchronizedSet(new HashSet<Integer>());
    class AThread extends  Thread{
        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 10; i++) {
                tmpSet.add(i);
                Log.d(TAG, "run: add " + i);
            }
        }
    }
    class BThread extends  Thread{
        @Override
        public void run() {
            super.run();
//            for (int i = 9; i >=0; i=i-2) {
            for (int i = 0; i <10; i=i+2) {
                boolean contains = tmpSet.remove(i);
                Log.d(TAG, "run: remove " + i + " contains : " + contains);
//                Log.d(TAG, "run: " + i + " exist: " + contains);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_sync_set);

        AThread threadA = new AThread();
        BThread threadB = new BThread();
        threadA.start();
        threadB.start();
    }
}
