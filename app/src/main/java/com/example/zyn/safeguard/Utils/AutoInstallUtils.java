package com.example.zyn.safeguard.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by zyn on 2016/7/16.
 */
public class AutoInstallUtils {
    private static String mUrl;
    private static Context mContext;
    private static Activity mActivity;

    /**
     * 外部传进来的url以便定位需要安装的APK
     *
     * @param url
     */
    public static void setUrl(String url) {
        mUrl = url;
    }

    /**
     * 安装
     *
     * @param activity
     *            接收外部传进来的context
     */
    public static void install(Activity activity) {
        mActivity = activity;
        int requestcode = 0;
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        intent.setDataAndType(Uri.fromFile(new File(mUrl)),
                "application/vnd.android.package-archive");
//        mContext.startActivity(intent);
        startActivityForResult(mActivity,intent,requestcode,null);

    }


}
