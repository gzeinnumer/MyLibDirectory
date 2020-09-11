package com.gzeinnumer.mylibstesting.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.gzeinnumer.gzndirectory.helper.FunctionGlobalDir;
import com.gzeinnumer.gzndirectory.helper.FunctionGlobalFile;
import com.gzeinnumer.mylibstesting.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gunakan function ini cukup satu kali saja pada awal activity
        String externalFolderName = getApplication().getString(R.string.app_name); //MyLibsTesting
        FunctionGlobalDir.initExternalDirectoryName(externalFolderName);

        if (checkPermissions()) {
            Toast.makeText(this, "Izin sudah diberikan", Toast.LENGTH_SHORT).show();
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Berikan izin untuk membuat folder terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    int MULTIPLE_PERMISSIONS = 1;

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // tambahkan ini
                onSuccessCheckPermitions();

            } else {
                StringBuilder perStr = new StringBuilder();
                for (String per : permissions) {
                    perStr.append("\n").append(per);
                }
            }
        }
    }


    private void onSuccessCheckPermitions() {
        //buat file dalam folder App
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};

        if(FunctionGlobalFile.initFile("/MyFile.txt",data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();
            List<String> list = FunctionGlobalFile.readFile("/MyFile.txt");
            Toast.makeText(this, "Jumlah baris sebelum ditambahkan: "+list.size() , Toast.LENGTH_SHORT).show();

            //tambahkan fuction ini untuk menambahkan text pada file yang sudah dibuat
            onAppentText();

        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }

    private void onAppentText() {
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String path = "/MyFile.txt";
        if (FunctionGlobalDir.isFileExists(path)){
            String[] messages = {"Pesan ini akan ditambahkan ke file di line baru 1","Pesan ini akan ditambahkan ke file di line baru 2"};

            //function untuk menambah text ke file yang sudah dibuat sebelumnya
            if(FunctionGlobalFile.appentText(path, messages)){
                Toast.makeText(this, "Line baru ditambah ke file", Toast.LENGTH_SHORT).show();
                List<String> list = FunctionGlobalFile.readFile("/MyFile.txt");
                Toast.makeText(this, "Jumlah baris setelah ditambahkan: "+list.size() , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ada error ketika add pesan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}