<h1 align="center">
  MyLibDirectory
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-1.1.6-brightgreen.svg?style=flat"></a>
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

**Manifest.** Tambahkan permition ke file manifest. Zein sarankan untuk menambahkan requestLegacyExternalStorage=true jika android kamu sudah android 10.

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
**DEBUG.** Jika kamu menemukan masalah pada sistem, kamu bisa debug dengan cara sperti ini.
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/debug.jpg)|
|--|

#
- **Step 1.** 
\
Kamu harus mendeklarasi dulu folder name yang akan kamu pakai di external :
\
**notes.** Zein sarankan untuk mendeklarasi dulu Folder Name, cukup 1 kali saja di onCreate activity yang pertama kali dipanggil contohnya "SplashScreenActivity atau MainActivity"  :

```java
public class MainActivity extends AppCompatActivity {
    
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //gunakan function ini cukup satu kali saja pada awal activity
        String externalFolderName = getApplication().getString(R.string.app_name); //   /storage/emulated/0/MyLibsTesting
        FunctionGlobalDir.initExternalDirectoryName(externalFolderName);
    }

    ...

}
```

**notes.** pada tutorial ini, variable `externalFolderName` akan berisi `MyLibsTesting`, semua file dan folder yang akan kita buat di bawah akan ada didalam direktory atau path `/storage/emulated/0/MyLibsTesting`

#
**Step 2.** 
\
Tambahkan array permition yang dibutuhkan : \
**First Activity.** letakan permition pada saat awal activity dimulai, disini Zein meletakannya di MainActivity.

```java
public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    
    ...

}
```

#
**Step 3.** 
\
Tambahkan function untuk mengecek permition apps apakah semua permition sudah diberikan izinkan :

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

#
**Step 4.**
\
Jika belum diberikan izin maka akan keluar popup :

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

#
**Step 5.** 
\
Jika permition sudah diizinkan, buat dan panggil function "onSuccessCheckPermitions" untuk membuat folder :

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

#
**Step 6.**
\
Jika onRequestPermissionsResult sudah mendapat permition yang dibutuhkan, maka kita akan membuat function "onSuccessCheckPermitions":\
**notes.** Jika kamu mau membuat folder dalam folder, pastikan value variable "folders" di awali dengan folder parent nya dulu.\
**example.** kamu mau membuat folder "folder1" yang di isi folder "folder1_1", pastikan kamu menulis dulu "folder1" baru setelahnya "folder1_1". seperti di bawah

```java
public class MainActivity extends AppCompatActivity {
    
    ...

    //cara penulisan 1
    private void onSuccessCheckPermitions() {
        //   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        //   /storage/emulated/0/MyLibsTesting/folder2
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
        //   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        //   /storage/emulated/0/MyLibsTesting/folder2
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
        //   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        //   /storage/emulated/0/MyLibsTesting/folder2
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

**notes.** ada 3 cara penulisan yang bisa kamu pilih.

#
**Step 7.**
\
Tambahkan function di onCreate agar setiap activity dijalankan maka akan selalu mengecek apakah izin sudah diberikan :

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

#
**Step 8.**
\
Fullcode akan tampak seperti ini :

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
        String externalFolderName = getApplication().getString(R.string.app_name); //   /storage/emulated/0/MyLibsTesting
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
        //   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        //   /storage/emulated/0/MyLibsTesting/folder2
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

#
**Step 9.**
\
Jika sukses maka akan tampil seperti ini :
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example1.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example3.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example4.jpg)|
|--|--|--|--|
|Request Permition |Folder MyLibsTesting sudah dibuat|`folder1` dan `folder2` sudah terbuat|`folder1_1` yang berada didalam `folder1` sudah dibuat|

---

## Function Global File
**CRUD File.** Lanjutan pada step 9 sebelumnya, disini kita akan mencoba membuat file dengan lebih simple dan cepat :

#
**Step 10.**
\
Pada function "onSuccessCheckPermitions" kita bisa membuat file dengan memastikan kalau permition sudah di berikan, ikuti Step 1 - Step 8 :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //buat file dalam folder App
        //cara 1
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};
        //cara 2
        //String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
    
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        if(FunctionGlobalFile.initFile("/MyFile.txt",data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```

**notes.** pada code dibawah, kamu bisa membuat file baru dengan value yang sudah kamu set, file akan dibuat dan text akan dimasukan ke file dengan urutan sesuai index array di bawah.

```java
//cara 1
String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};
//cara 2
//String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
```

#
**Step 11.**
\
Fullcode akan tampak seperti ini :

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
        String externalFolderName = getApplication().getString(R.string.app_name); //   /storage/emulated/0/MyLibsTesting
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

        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        if(FunctionGlobalFile.initFile("/MyFile.txt",data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }
}
```

#
**Step 12.** 
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|
|--|--|--|
|Folder MyLibsTesting sudah dibuat|`MyFile.txt` yang berada didalam MyLibsTesting sudah dibuat|Isi dari `MyFile.txt`|

#
**Step 13.**
\
Setelah file dibuat, kita bisa membaca file dengan code sebagai berikut :

```java
public class MainActivity extends AppCompatActivity {

    private void onSuccessCheckPermitions() {
        
        ...
            //   /storage/emulated/0/MyLibsTesting/MyFile.txt
            List<String> list = FunctionGlobalFile.readFile("/MyFile.txt");
            String value_0 = list.get(0);
            Toast.makeText(this, "Jumlah baris : "+list.size() , Toast.LENGTH_SHORT).show();
        ...

    }
}
```

**notes.** hasil akan barupa "List<String>", dan kamu bisa ambil datanya sesuai index.

#
**Step 14.**
\
Code akan tampak seperti ini :

```java
public class MainActivity extends AppCompatActivity {
    
    //sama seperti STEP 11. hanya saja isi dari function onSuccessCheckPermitions berbeda
    ... 

    private void onSuccessCheckPermitions() {
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};

        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        if(FunctionGlobalFile.initFile("/MyFile.txt",data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();
           
            //READ MyFile.txt
            //   /storage/emulated/0/MyLibsTesting/MyFile.txt
            List<String> list = FunctionGlobalFile.readFile("/MyFile.txt");
            String value_0 = list.get(0);
            Toast.makeText(this, "Jumlah baris : "+list.size() , Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }
}
```

**notes.** Pastikan File sudah dibuat.

---

## Function Global Zip
**String BASE64 ke ZIP.** Lanjutan pada step 9 sebelumnya, disini kita akan mencoba membuat file zip dan lansung diextrack ke folder yang kira mau dengan cepat dan mudah :

#
**Step 15.**
\
Pada function "onSuccessCheckPermitions" kita bisa membuat file zip dengan memastikan kalau permition sudah di berikan, ikuti STEP 1 - STEP 8 : \
disini kita akan mendeklarasikan
\ 
1. `fileName` untuk nama file sebelum diencode dengan Base64. 
2. `base64EncodeFromFile` file zip yang sudah diencode jadi Base64,
3. `md5EncodeFromFile` file zip yang sudah diencode jadi Md5 untuk memastikan Base64 tidak corrupt
4. `zipLocation` file zip yang diextract akan meletakan semua filenya ke direcotry yang dibuat disini
\
jika 3 hal tersebut sudah dideklarasi, maka silahkan gunakan function seperti dibawah :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //   /storage/emulated/0/MyLibsTesting/ExternalBase64Md5ToZip.zip
        String fileName = "/ExternalBase64Md5ToZip.zip";

        //dari file zip diubah jadi base64
        //https://base64.guru/converter/encode/file
        String base64EncodeFromFile = "UEsDBBQAAAAIAJK6+FDfGqHQdAEAAABAAAAZAAAARXh0ZXJuYWxCYXNlNjRNZDVUb1ppcC5kYu3aQU7CQBQG4BlKgJJgWZh042JsQgIBTNQLiKYhRCgIJYqbZqRj0tgWgXIAbuQJvIk3MC516xRMhLowLiX/l2nmvXnpm25f0sFV24sEu5/MAh6xU1IklJIzxgghqnzS5Fs6kVPyO5UcHTwVZKDsPRPN03S5AQAAAAAAAPzRtZLR63U6jvidL3joziae6wQi4i6PeDJPX/TNhm0yu3HeNlmyysr+ZMx9wWzzxq70Uhm9WqWjVeP51JczsjMX04UIx8lU2WqbKJZDHoiazCrLfZrVSyW6fFj35MGjL5wfcWqrm7FZMlg5rxqea6gtyzabZp9ZXZtZw3a7Js/jiww1/vjN416/1Wn0R+zSHJXjV1ljaHdblrykY1p2JV9ZzebaC9HetVe5AQAAAAAAAMB/U1AUcti8FV5oLQIxy6UUosfZSY5+Rcfx/E+1NyIXAAAAAAAAAOyEIlVKdPOfAmU9/38QuQAAAAAAAABgt2RpShehMxx8AlBLAQI/ABQAAAAIAJK6+FDfGqHQdAEAAABAAAAZACQAAAAAAAAAgAAAAAAAAABFeHRlcm5hbEJhc2U2NE1kNVRvWmlwLmRiCgAgAAAAAAABABgAgEsYXNZh1gH+eSEy1mHWASKHEDLWYdYBUEsFBgAAAAABAAEAawAAAKsBAAAAAA==";

        //dari file zip diubah jadi md5
        //https://emn178.github.io/online-tools/md5_checksum.html
        String md5EncodeFromFile = "966af03a49f85b0df0afd3d9a42d0264";
        
        //   /storage/emulated/0/MyLibsTesting/zipLocation
        String zipLocation = "/zipLocation";

        //   /storage/emulated/0/MyLibsTesting/zipLocation
        if (FunctionGlobalZip.initFileFromStringToZipToFile(fileName, zipLocation ,base64EncodeFromFile,md5EncodeFromFile, true)){
            Toast.makeText(this, "Success load data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gagal load data", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```

**notes.** pastikan pada fileName adalah nama asli dari file yang sudah diencode dengan base64 dan md5, jika berbeda maka akan dapat lemparan error.

#
**Step 16.**
\
Code akan tampak seperti ini :

```java
public class MainActivity extends AppCompatActivity {
    
    //sama seperti STEP 11. hanya saja isi dari function onSuccessCheckPermitions berbeda
    ... 

    private void onSuccessCheckPermitions() {
        String fileName = "/ExternalBase64Md5ToZip.zip";

        //dari file zip diubah jadi base64
        //https://base64.guru/converter/encode/file
        String base64EncodeFromFile = "UEsDBBQAAAAIAJK6+FDfGqHQdAEAAABAAAAZAAAARXh0ZXJuYWxCYXNlNjRNZDVUb1ppcC5kYu3aQU7CQBQG4BlKgJJgWZh042JsQgIBTNQLiKYhRCgIJYqbZqRj0tgWgXIAbuQJvIk3MC516xRMhLowLiX/l2nmvXnpm25f0sFV24sEu5/MAh6xU1IklJIzxgghqnzS5Fs6kVPyO5UcHTwVZKDsPRPN03S5AQAAAAAAAPzRtZLR63U6jvidL3joziae6wQi4i6PeDJPX/TNhm0yu3HeNlmyysr+ZMx9wWzzxq70Uhm9WqWjVeP51JczsjMX04UIx8lU2WqbKJZDHoiazCrLfZrVSyW6fFj35MGjL5wfcWqrm7FZMlg5rxqea6gtyzabZp9ZXZtZw3a7Js/jiww1/vjN416/1Wn0R+zSHJXjV1ljaHdblrykY1p2JV9ZzebaC9HetVe5AQAAAAAAAMB/U1AUcti8FV5oLQIxy6UUosfZSY5+Rcfx/E+1NyIXAAAAAAAAAOyEIlVKdPOfAmU9/38QuQAAAAAAAABgt2RpShehMxx8AlBLAQI/ABQAAAAIAJK6+FDfGqHQdAEAAABAAAAZACQAAAAAAAAAgAAAAAAAAABFeHRlcm5hbEJhc2U2NE1kNVRvWmlwLmRiCgAgAAAAAAABABgAgEsYXNZh1gH+eSEy1mHWASKHEDLWYdYBUEsFBgAAAAABAAEAawAAAKsBAAAAAA==";

        //dari file zip diubah jadi md5
        //https://emn178.github.io/online-tools/md5_checksum.html
        String md5EncodeFromFile = "966af03a49f85b0df0afd3d9a42d0264";
        
        //   /storage/emulated/0/MyLibsTesting/zipLocation
        String zipLocation = "/zipLocation";

        if (FunctionGlobalZip.initFileFromStringToZipToFile(fileName, zipLocation,base64EncodeFromFile,md5EncodeFromFile, true)){
            Toast.makeText(this, "Success load data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gagal load data", Toast.LENGTH_SHORT).show();
        }
    }
}
```

#
**Step 17.**
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example7.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example8.jpg)|
|--|--|--|
|Folder MyLibsTesting sudah dibuat|`ExternalBase64Md5ToZip.zip` yang berada didalam MyLibsTesting sudah dibuat dari string base64, `/zipLocation` adalah folder yang dibuat untuk file hasil extract dari Zip|`ExernalBase64Md5ToZip.db` adalah file hasil extract dari file `ExternalBase64Md5ToZip.zip`|

#
**Step 18.**
\
Pada function "onSuccessCheckPermitions" kita bisa membuat file text dan menambahkan text setelah file itu dibuat, atau bisa disebut appentText, dengan memastikan kalau permition sudah di berikan, ikuti STEP 1 - STEP 8 : \
disini kita akan mendeklarasikan

1. `onSuccessCheckPermitions` itu adalah function yang sama dengan yang ada di Step 14, tambahkan function `onAppentText` untuk menambahkan jumlah text dalam file. 
2. `onAppentText`->`path` adalah path dari lokasi file sebelumnya yang sudah kita buat pada point NO 1,
3. `messages` pada variable ini kamu bisa masukan text yang mau kamu kirim ke file, text akan ditambahkan sesuai index

jika 3 hal tersebut sudah dideklarasi, maka silahkan gunakan function seperti dibawah :

```java
public class MainActivity extends AppCompatActivity {

    //sama seperti STEP 11. hanya saja isi dari function onSuccessCheckPermitions berbeda
    ...

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
    
    ...

}
```

#
**Step 20.**
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example9.jpg)|
|--|--|--|--|
|Folder MyLibsTesting sudah dibuat|`MyFile.txt` yang berada didalam MyLibsTesting sudah dibuat|`MyFile.txt` file dibuat dengan text yang sudah diset pertama kali|`MyFile.txt` new line ditambah ke file|

---

## Function Global Image
**Mengambil foto dengan camera.** Lanjutan pada step 9 sebelumnya, disini kita akan mencoba membuat file image yang kita ambil dari camera dengan mempertahankan kualitas gambar dan menyimpannya lansung ke external, dengan cepat dan mudah :

#
**Step 21.**
\
Pada function "onSuccessCheckPermitions" kita bisa mengatifkan fitur ini agar bisa mengambil gambar dengan jernih, pastikan dulu kalau permition sudah di berikan, ikuti STEP 1 - STEP 8 : \

jika Step 1- Step 8 sudah diselesaikan :

**activity_main.xml** tambahkan kode berikut:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/btn_camera"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Take Foto From Camera" />

    <ImageView
        android:id="@+id/img"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:layout_weight="1"
        android:adjustViewBounds="true"
        android:src="@mipmap/ic_launcher" />
</LinearLayout>
```

**manifest.xml** tambahkan uses-permission CAMERA lalu uses-feature camera, autofocus, flash pada file manifest, 
\
lalu didalam application tambahkan tag provider untuk memberikan izin pada sistem menyimpan image secara temporary untuk proses compress image:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.gzeinnumer.mylibstesting">

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.CAMERA" />

    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-feature android:name="android.hardware.camera.flash" />

    <application>

        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_provider_paths" />
        </provider>

    </application>

</manifest>
```

**file_provider_paths.xml** pada directory res buat folder xml dan buat file dengan nama file_provider_paths.xml.

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path
        name="my_images"
        path="Android/data/com.gzeinnumer.mylibstesting/files/DCIM" />
    
        <!-- ganti com.gzeinnumer.mylibstesting dengan package name project kamu-->
</paths>
```

**MainActivity** pada function `onSuccessCheckPermitions` tambahkan kode berikut :

tambahkan permition CAMERA ke array :

```java
public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    
    ...

}
```

Tambahkan kode seperti berikut :

```java
public class MainActivity extends AppCompatActivity {

    //sama seperti STEP 11. hanya saja isi dari function onSuccessCheckPermitions berbeda
    ...

    static final int REQUEST_TAKE_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onSuccessCheckPermitions() {
        btnCamera = findViewById(R.id.btn_camera);

        imageView = findViewById(R.id.img);

        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini 

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    //jalankan intent untuk membuka kamera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //simpan data di dalam root folder sebagai temporary
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    //setelah foto diambil, dan tampil di preview maka akan lansung disimpan ke folder yang di sudah diset sebelumnya
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
                Log.d(TAG, "onActivityResult: " + mPhotoFile.toString());
                Toast.makeText(this, "Image Path : "+mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    ...

}
```

#
**Step 22.**
\
Fullcode akan tampak seperti ini :

```java
public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //gunakan function ini cukup satu kali saja pada awal activity
        String externalFolderName = getApplication().getString(R.string.app_name); //   /storage/emulated/0/MyLibsTesting
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

    static final int REQUEST_TAKE_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onSuccessCheckPermitions() {
        btnCamera = findViewById(R.id.btn_camera);

        imageView = findViewById(R.id.img);

        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini 

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    //jalankan intent untuk membuka kamera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //simpan data di dalam root folder sebagai temporary
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    //setelah foto diambil, dan tampil di preview maka akan lansung disimpan ke folder yang di sudah diset sebelumnya
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
                Log.d(TAG, "onActivityResult: " + mPhotoFile.toString());
                Toast.makeText(this, "Image Path : "+mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
```

#
**Step 23.**
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example10.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example11.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example12.jpg)|
|--|--|--|
|Tampilan awal, tekan Button untuk membuka kamera|Hasil foto yang diambil, tekan oke untuk lanjutkan|Akan muncul Toast untuk memberitahu lokasi foto disimpan|
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example14.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example16.jpg)|
|folder `Foto` otomatis terbuat|File sudah tersimpan pada folder `Foto`|Detail pada galery|Ukuran foto kecil, tapi resolusi dipertahankan tetap besar|-|


#
**Step 24.**
\
Tambahkan kode seperti berikut :

```java
public class MainActivity extends AppCompatActivity {

    //sama seperti STEP 11. hanya saja isi dari function onSuccessCheckPermitions berbeda
    ...

    static final int REQUEST_GALLERY_PHOTO = 3;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onSuccessCheckPermitions() {
        btnCamera = findViewById(R.id.btn_camera);

        imageView = findViewById(R.id.img);

        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini 

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryIntent();
            }
        });
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
            }
        }
    }
    
    ...

}
```

#
**Step 25.**
\
Fullcode akan tampak seperti ini :

```java
public class MainActivity extends AppCompatActivity {

    //sama seperti STEP 11. hanya saja isi dari function onSuccessCheckPermitions berbeda
    ...

    static final int REQUEST_GALLERY_PHOTO = 3;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onSuccessCheckPermitions() {
        btnCamera = findViewById(R.id.btn_camera);

        imageView = findViewById(R.id.img);

        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini 

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryIntent();
            }
        });
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
            }
        }
    }
    
    ...

}
```

#
**Step 25.**
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example17.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example18.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example19.jpg)|
|--|--|--|
|Tampilan awal, tekan Button untuk membuka galery|Pilih media|Pilih foto|
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example20.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example21.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example22.jpg)|
|Preview foto|Folder foto otomatis terbuat|Foto sudah ada pada directory yang sudah diset|Detail pada galery|


---

```
Copyright 2020 M. Fadli Zein
```
