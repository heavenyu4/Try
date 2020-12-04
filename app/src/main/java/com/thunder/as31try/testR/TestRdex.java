package com.thunder.as31try.testR;

class TestRdex {

    public static void main(String[] args) {
        String [] packs = {
                "com.mobgi.tool.adtrack",
                "com.bytedance.applog",
                "union.plugin",
                "game_sdk.packers.rocket_sdk",
                "android.support.v7.recyclerview",

                "com.just.agentweb",

                "android.support.v4",
                "android.support.fragment",
                "android.support.coreui",


                "com.bumptech.glide",
                "me.drakeet.multitype",
                "android.support.graphics.drawable" ,
                "android.support.v7.appcompat",
                "android.support.v7.cardview",
                "android.support.v7.viewpager",
                "android.support.asynclayoutinflater",
                "android.support.coordinatorlayout" ,
                "android.support.customview",
                "android.support.design",
                "android.support.drawerlayout",
                "android.support.loader",
                "androidx.navigation.common",
                "androidx.navigation.fragment",
                "androidx.navigation",
                "androidx.navigation.ui",
                "android.arch.lifecycle.extensions" ,
                "android.arch.persistence.room",
                "android.support.slidingpanelayout" ,
                "android.support.compat",
                "android.support.mediacompat",
                "android.support.graphics.drawable" ,
                "android.support.swiperefreshlayout",
                "android.support.transition"
        };

        buildRPackage(packs);
    }

    private static void buildRPackage(String[] packs) {
        StringBuilder builder = new StringBuilder();
//        <attr name="package10" value="android.support.graphics.drawable" />
        for (int i = 0; i < packs.length; i++) {
            builder.append("<attr name=\"package").append(i+1).append("\" value=\"")
                    .append(packs[i]).append("\" />\n");
        }
        System.out.println(builder.toString());

    }
}
