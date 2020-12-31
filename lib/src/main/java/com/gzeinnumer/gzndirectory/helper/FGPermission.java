package com.gzeinnumer.gzndirectory.helper;

import android.app.Activity;
import android.util.Log;

import com.gzeinnumer.gzndirectory.helper.model.PermissionsResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rebus.permissionutils.AskAgainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.PermissionUtils;

public class FGPermission {
    private static final String TAG = "Gbl_";

    public static void checkPermissions(Activity activity, PermissionEnum[] permissionEnums) {
        ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>(Arrays.asList(permissionEnums));
        PermissionManager.Builder()
                .permissions(permissionEnumArrayList)
                .askAgain(true)
                .askAgainCallback(new AskAgainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        Log.d(TAG, "showRequestPermission: "+response.toString());
                    }
                })
                .callback(new FullCallback() {
                    @Override
                    public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {
//                        Log.d(TAG, "result1: "+permissionsGranted.get(0).toString());
//                        Log.d(TAG, "result2: "+permissionsDenied.get(0).toString());
//                        Log.d(TAG, "result3: "+permissionsDeniedForever.get(0).toString());
//                        Log.d(TAG, "result4: "+permissionsAsked.get(0).toString());
                    }
                })
                .ask(activity);
    }

    public static boolean getPermissionResult(Activity activity, PermissionEnum[] permissionEnums) {
        ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>(Arrays.asList(permissionEnums));
        ArrayList<PermissionsResult> list = new ArrayList<>();
        for (int i = 0; i < permissionEnumArrayList.size(); i++) {
            PermissionEnum permissionEnum = permissionEnumArrayList.get(i);
            boolean granted = PermissionUtils.isGranted(activity, permissionEnum);
            list.add(new PermissionsResult(String.valueOf(granted), permissionEnum.toString()));
        }

        int countFalse = 0;
        int countTrue = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isGranted().equals("false")){
                countFalse++;
            } else if (list.get(i).isGranted().equals("true")){
                countTrue++;
            }
        }

        boolean status = false;
        if (countFalse>0){
            status = false;
            Log.d(TAG, "oncheckPermittion: "+"G");
        } else if(countFalse==0 && countTrue>0) {
            status = true;
        }
        return status;
    }

    public static void getPermissionResult(Activity activity, PermissionEnum[] permissionEnums, CallBackPermission callBackPermission) {
        ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>(Arrays.asList(permissionEnums));
        ArrayList<PermissionsResult> list = new ArrayList<>();

        for (int i = 0; i < permissionEnumArrayList.size(); i++) {
            PermissionEnum permissionEnum = permissionEnumArrayList.get(i);
            boolean granted = PermissionUtils.isGranted(activity, permissionEnum);
            if (granted){
                list.add(new PermissionsResult("Granted", permissionEnum.toString()));
            } else {
                list.add(new PermissionsResult("Denied", permissionEnum.toString()));
            }
        }

        int countFalse = 0;
        int countTrue = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isGranted().equals("Denied")){
                countFalse++;
            } else if (list.get(i).isGranted().equals("Granted")){
                countTrue++;
            }
        }

        boolean status = false;
        if (countFalse>0){
            status = false;
        } else if(countFalse==0 && countTrue>0) {
            status = true;
        }
        callBackPermission.result(status, list);
    }

    public interface CallBackPermission{
        void result(boolean isAllGranted, List<PermissionsResult> list);
    }
}