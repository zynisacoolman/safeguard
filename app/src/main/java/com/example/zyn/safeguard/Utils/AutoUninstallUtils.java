package com.example.zyn.safeguard.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by zyn on 2016/7/16.
 */
public class AutoUninstallUtils {
//    private static String mUrl;
    private static Context mContext;

    /**
     * 外部传进来的url以便定位需要安装的APK
     *
     * @param url
     */


    /**
     * 安装
     *
     * @param context
     *            接收外部传进来的context
     */
    public static void uninstall(Context context) {
        mContext = context;
        // 核心是下面几句代码
        Uri packageURI = Uri.parse("package:com.example.zyn.safeguard");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        mContext.startActivity(uninstallIntent);
    }
}
