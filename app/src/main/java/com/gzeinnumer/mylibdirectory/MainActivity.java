package com.gzeinnumer.mylibdirectory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gzeinnumer.gzndirectory.helper.FGPermission;
import com.gzeinnumer.gzndirectory.helper.model.PermissionsResult;

import java.util.List;

import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class MainActivity extends AppCompatActivity {

    PermissionEnum[] permissionEnumArrayList = new PermissionEnum[]{
            PermissionEnum.WRITE_EXTERNAL_STORAGE,
            PermissionEnum.READ_EXTERNAL_STORAGE,
            PermissionEnum.CAMERA
    };

    private static final String TAG = "MainActivity_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FGPermission.checkPermissions(this, permissionEnumArrayList);

        oncheckPermittion();
    }

    private void oncheckPermittion() {
        boolean list = FGPermission.getPermissionResult(this, permissionEnumArrayList);

        if (list){
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
        }

        FGPermission.getPermissionResult(this, permissionEnumArrayList, new FGPermission.CallBackPermission() {
            @Override
            public void result(boolean isAllGranted, List<PermissionsResult> list) {
                Log.d(TAG, "result: "+isAllGranted);

                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "result: "+list.toString());
                }
            }
        });
    }

    private void onSuccessCheckPermitions() {
        Log.d(TAG, "onSuccessCheckPermitions: S");
//        startActivity(new Intent(getApplicationContext(), SecondActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);

        oncheckPermittion();
    }
}