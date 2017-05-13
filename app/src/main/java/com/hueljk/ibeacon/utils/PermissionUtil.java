package com.hueljk.ibeacon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;



/**
 * 用于6.0以上添加动态权限
 */

public class PermissionUtil {

    public static void addPermission(Activity mActivity){
        if (Build.VERSION.SDK_INT>=23){
            PermissionChecker permissionChecker = new PermissionChecker(mActivity);
            if (permissionChecker.lacksPermissions(WarningPermission.ACCESS_COARSE_LOCATION,WarningPermission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(mActivity,new String[]{WarningPermission.ACCESS_COARSE_LOCATION,WarningPermission.ACCESS_FINE_LOCATION},123);
            }
        }
    }

    /**
     * 权限判断类
     */

    public static class PermissionChecker {
        private Context mContext;

        public PermissionChecker(Context mContext) {
            this.mContext = mContext;
        }

        /**
         * 判断权限集合
         * @param permission
         * @return 权限集合是否缺失
         */
        public boolean lacksPermissions(String...permission){
            for (String permissions:permission) {
                if (lacksPermission(permissions)){
                    return true;
                }
            }
            return false;
        }

        /**
         * 判断是是否存在该权限
         * @param permissions
         * @return 是否缺少该权限
         */
        private boolean lacksPermission(String permissions) {

            int i = ContextCompat.checkSelfPermission(mContext, permissions);
            int permissionDenied = PackageManager.PERMISSION_DENIED;
            return i==permissionDenied;
        }

    }
}
