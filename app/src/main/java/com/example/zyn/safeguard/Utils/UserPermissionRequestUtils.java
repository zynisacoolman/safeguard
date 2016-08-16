package com.example.zyn.safeguard.Utils;

/**
 * Created by zyn on 2016/8/10.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
     * 检查权限的工具类
     * <p/>
     * Created by wangchenlong on 16/1/26.
     */
    public class UserPermissionRequestUtils {

        // 判断是否缺少权限集合
        public static boolean lacksPermissions(Context mContext,String... permissions) {
            for (String permission : permissions) {
                if (lacksPermission(mContext,permission)) {
                    return true;
                }
            }
            return false;
        }



    // 判断是否缺少权限组
        public static boolean lacksPermission(Context mContext,String permission) {
            return ContextCompat.checkSelfPermission(mContext, permission) ==
                    PackageManager.PERMISSION_DENIED;
        }
    }
