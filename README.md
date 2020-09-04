<h1 align="center">
  MyLibDirectory
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-0.0.9-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <p>Kumpulan function untuk membuat folder dan file yang sering dipakai dalam development program android, dokumen ini dibuat berdasarkan pengalaman Zein, kasih masukan kalau ada yang kurang. terimakasih karna sudah berkunjung</p>
</div>

---

### Feature List
- [x] Contoh Multi Check Permissions
- [x] Create Folder
- [x] Cek file exists
- [x] Create file dan menyimpan text
- [x] Membaca text dalam file
- [x] Buat file Zip dari Base64 dan MD5

### Tech stack and 3rd library
- MVVM ([docs](https://developer.android.com/jetpack/guide))

---

## Download

Minimum Android SDK Version 16

#### Gradle
**Step 1.** tambahkan maven jitpack.io ke build.gradle (Project) :
```gradle
allprojects {
  repositories {
    google()
    jcenter()
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** tambahkan depedensi ke build.gradle (Module) :
```gradle
dependencies {
  implementation 'com.github.gzeinnumer:MyLibDirectory:versi'
}
```

---

## Function Global Directory
**Contoh Multi Check Permissions.** Request permition secara bersamaan, Zein sarankan untuk requestnya dijalankan di activity yang pertama aktif, disini Zein masukan ke MainActivity :

**Manifest** Tambahkan permition ke file manifest. Zein sarankan untuk menambahkan requestLegacyExternalStorage=true jika android kamu sudah android 10.
```xml
<manifest >

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <application
        android:requestLegacyExternalStorage="true">

    </application>

</manifest>
```

\
**First Activity** letakan permition pada saat awal activity dimulai, disini Zein meletakannya di MainActivity.


**DEBUG** Jika kamu menemukan masalah pada sistem, kamu bisa debug dengan cara sperti ini.
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/debug.jpg)|
|--|


**Step 1.** Kamu harus mendeklarasi dulu folder name yang akan kamu pakai di external :
**notes** Zein sarankan untuk mendeklarasi dulu Folder Name, cukup 1 kali saja di onCreate activity yang pertama kali dipanggil contohnya "SplashScreenActivity atau MainActivity"  :
```java
public class MainActivity extends AppCompatActivity {
    
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //gunakan function ini cukup satu kali saja pada awal activity
        String externalFolderName = getApplication().getString(R.string.app_name);
        FunctionGlobalDir.initExternalDirectoryName(externalFolderName);
    }

    ...

}
```
\
**Step 2.** tambahkan array permition yang dibutuhkan :
```java
public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    
    ...

}
```
\
**Step 3.** tambahkan function untuk mengecek permition apps apakah semua permition sudah diberikan izinkan :
```java

public class MainActivity extends AppCompatActivity {
    
    ...

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
    
    ...

}
```
\
**Step 4.** jika belum diberikan izin maka akan keluar popup :
```java

public class MainActivity extends AppCompatActivity {
    
    ...

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //aksi setealh semua permition diberikan
            } else {
                StringBuilder perStr = new StringBuilder();
                for (String per : permissions) {
                    perStr.append("\n").append(per);
                }
            }
        }
    }
    
    ...

}
```
\
**Step 5.** Jika permition sudah diizinkan, buat dan panggil function "onSuccessCheckPermitions" untuk membuat folder :
```java

public class MainActivity extends AppCompatActivity {
    
    ...

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onSuccessCheckPermitions();
            } else {
                StringBuilder perStr = new StringBuilder();
                for (String per : permissions) {
                    perStr.append("\n").append(per);
                }
            }
        }
    }
    
    ...

}
```
\
**Step 6.** Jika onRequestPermissionsResult sudah mendapat permition yang dibutuhkan, maka kita akan membuat function "onSuccessCheckPermitions":\
**notes** Jika kamu mau membuat folder dalam folder, pastikan value variable "folders" di awali dengan folder parent nya dulu.\
**example** kamu mau membuat folder "folder1" yang di isi folder "folder1_1", pastikan kamu menulis dulu "folder1" baru setelahnya "folder1_1". seperti di bawah\
```java
public class MainActivity extends AppCompatActivity {
    
    ...

    //cara penulisan 1
    private void onSuccessCheckPermitions() {
        String[] folders = {"/folder1","/folder1/folder1_1","/folder2"};
        if (FunctionGlobalDir.initFolder(folders)){
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }

    //cara penulisan 2
    private void onSuccessCheckPermitions() {
        String[] folders = new String[]{"/folder1","/folder1/folder1_1","/folder2"};
        if (FunctionGlobalDir.initFolder(folders)){
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }

    //cara penulisan 3
    private void onSuccessCheckPermitions() {
        String[] folders = {"/folder1","/folder1/folder1_1","/folder2"};
        if (FunctionGlobalDir.initFolder(folders)){
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }
}
```
\
**Step 7.** tambahkan function di onCreate agar setiap activity dijalankan maka akan selalu mengecek apakah izin sudah diberikan :
```java
public class MainActivity extends AppCompatActivity {

    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ...

        if (checkPermissions()) {
            Toast.makeText(this, "Izin sudah diberikan", Toast.LENGTH_SHORT).show();
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Berikan izin untuk membuat folder terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```
\
**Step 8.** Fullcode akan tampak seperti ini :
```java
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
        String externalFolderName = getApplication().getString(R.string.app_name);
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
        String[] folders = {"/folder1","/folder1/folder1_1","/folder2"};
        if (FunctionGlobalDir.initFolder(folders)){
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }
}
```

\
**Step 9.** Jika sukses maka akan tampil seperti ini :
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example1.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example3.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example4.jpg)|
|--|--|--|--|
|Request Permition |Folder MyLibsTesting sudah dibuat|'folder1' dan 'folder2' sudah terbuat|'folder1_1' yang berada didalam 'folder1' sudah dibuat|

---

## Function Global File
**CRUD File.** Lanjutan pada step 9 sebelumnya, disini kita akan mencoba membuat file dengan lebih simple dan cepat :

\
**Step 10.** pada function "onSuccessCheckPermitions" kita bisa membuat file dengan memastikan kalau permition sudah di berikan, ikuti STEP 1 - STEP 8 :
```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //buat file dalam folder App
        //cara 1
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};
        //cara 2
        //String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
    
        if(FunctionGlobalFile.initFile("/MyFile.txt",data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```

**notes** pada code dibawah, kamu bisa membuat file baru dengan value yang sudah kamu set, file akan dibuat dan text akan dimasukan ke file dengan urutan sesuai index array di bawah.
```java
//cara 1
String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};
//cara 2
//String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
```

\
**Step 11.** Fullcode akan tampak seperti ini :
```java
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
        String externalFolderName = getApplication().getString(R.string.app_name);
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
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};

        if(FunctionGlobalFile.initFile("/MyFile.txt",data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }
}
```

\
**Step 12.** Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|
|--|--|--|
|Folder MyLibsTesting sudah dibuat|'MyFile.txt' yang berada didalam MyLibsTesting sudah dibuat|Isi dari 'MyFile.txt'|



---

```
Copyright 2020 M. Fadli Zein
```
