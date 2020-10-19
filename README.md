<h1 align="center">
  MyLibDirectory
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-1.3.9-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <br>
    <a><img src="https://img.shields.io/badge/Java-Suport-green?logo=java&style=flat"></a>
    <a><img src="https://img.shields.io/badge/Koltin-Suport-green?logo=kotlin&style=flat"></a>
    <p>Kumpulan function untuk membuat folder dan file yang sering dipakai dalam development program android, dokumen ini dibuat berdasarkan pengalaman Zein, kasih masukan kalau ada yang kurang. terimakasih karna sudah berkunjung</p>
</div>

---

### Feature List
- [x] [Function Global Directory](#function-global-directory)
  - Contoh Multi Check Permissions
  - Manifest
  - DEBUG
  - Step 1. Create Folder
- [x] [Function Global File](#function-global-directory)
  - Step 4. Create File
  - Step 6. Read File
  - Step 8. AppentText
- [x] [Function Global Zip](#function-global-zip)
  - Step 10. Encode Base64/Md5 to Zip
- [x] [Function Global Image Camera](#function-global-image-camera)
  - Step 12. Take Image From Camera And Compress
- [x] [Function Global Image Galery](#function-global-image-galery)
  - Step 19. Take Image From Galery
- [x] [Function Global Image Internet](#function-global-image-internet)
  - Step 23. Load Image From Internet and Save
- [x] Cek file exists

### Tech stack and 3rd library
- MultiPermition ([docs](https://github.com/gzeinnumer/MultiPermition))
- Androidwave ([docs](https://androidwave.com/))
- Glide ([docs](https://github.com/bumptech/glide))
- Picasso ([docs](https://github.com/square/picasso))
- Permissions ([docs](https://developer.android.com/guide/topics/permissions/overview))
- File ([docs](https://developer.android.com/reference/java/io/File))
- File Provider ([docs](https://developer.android.com/training/secure-file-sharing/setup-sharing?hl=id))
- Mengambil Foto ([docs](https://developer.android.com/training/camera/photobasics?hl=id))
- Intent Galery ([docs](https://developer.android.com/guide/components/intents-common?hl=id))
- Android Internet ([docs](https://developer.android.com/training/basics/network-ops/connecting))
- RxJava/RxAndroid ([docs](https://github.com/ReactiveX/RxJava))
- Dexter ([docs](https://github.com/Karumi/Dexter))

### Function Global Directory
> Example : FGDir.initExternalDirectoryName(valueString);

| Name                        | Return    | Parameter              | Keterangan                                                                               |
|:----------------------------|:----------|:-----------------------|:-----------------------------------------------------------------------------------------|
| `initExternalDirectoryName` | `void`    | `String appFolder`     | Function untuk mendeklarasi folder nama yang akan dibuat di external                     |
| `initFolder`                | `boolean` | `String... folderName` | Membuat folder pada direktori yang sudah dideklarasi di atas `initExternalDirectoryName` |
| `isFileExists`              | `boolean` | `String path`          | untuk mengecek apakah File/Folder sudah ada atau belum                                   |

### Function Global File
> Example : FGFile.readFile(valueString);

| Name                        | Return    | Parameter                                                                             | Keterangan                                                                                                       |
|:----------------------------|:----------|:--------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------|
| `initFile`                  | `boolean` | `String fileName, String saveTo, String... text`                                      | Untuk membuat file baru lansung dengan text yang akan dimasukan ke file, seperti `MyFile.txt`                    |
| `readFile`                  | `boolean` | `String path`                                                                         | Untuk membaca isi dari file text yang sudah dibuat, pastikan file sudah dibuat terlebih dahulu dengan `initFile` |
| `appentText`                | `boolean` | `String path, String... msg`                                                          | Untuk menambah new line text ke file txt yang sudah dibuat sebelumnya dengan `initFile`                          |
| `initFileImageFromInternet` | `boolean` | `String imgUrl, String saveTo, String filename, ImageView sendImageTo, boolean isNew` | Untuk mendownload image dari internet dan menyimpannya ke penyimpanan                                            |
| `createImageFile`           | `File`    | `Context context, String fileName`                                                    | Untuk menyimpan data secara temporary sebelum di copy ke tujuan yang sudah diset                                 |
| `getRealPathFromUri`        | `String`  | `Context context, Uri contentUri`                                                     | Untuk mendapatkan nama asli dari file yang dipilih                                                               |

### Function Global Zip
> Example : FGZip.initFileFromStringToZipToFile(valueString, valueString, valueString, valueString, valueBoolean);

| Name                            | Return    | Parameter                                                                                                   | Keterangan                                                                                                            |
|:--------------------------------|:----------|:------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------|
| `initFileFromStringToZipToFile` | `boolean` | `String fileName, String zipLocation, String base64EncodeFromFile, String md5EncodeFromFile, boolean isNew` | Mendecode String Base64 hingga menjadi file Zip mengekstraknya serta meletakan hasil ektrack ke direktory yang dituju |

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

**Step 2.** tambahkan dependensi ke build.gradle (Module) :

```gradle
dependencies {
  implementation 'com.github.gzeinnumer:MyLibDirectory:versi'
}
```

---

## Function Global Directory
This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition) or use your own.

**DEBUG.** If you find some trouble you can trace with this step.
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/debug.jpg)|
|--|

#
**Step 1. Create Folder** 
\
If you have grand your permission, add 2 code on your `onCreate`. you need to declaration `Folder Name` that you will use as you Folder Name in external. Now i am ussing `MyLibsTesting`.

```java
public class MainActivity extends AppCompatActivity {
    
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //gunakan function ini cukup satu kali saja pada awal activity
        String externalFolderName = getApplication().getString(R.string.app_name); //MyLibsTesting
        FGDir.initExternalDirectoryName(externalFolderName);
        
        ...

    }

    ...
}
```

**notes.** 
  - I suggest you to decalre `Folder Name` first, just **One Time** in your first activity inside function `onCreate`. example `SplashScreenActivity` or `MainActivity`.
  - In this tutorial, i will put every file and folder in `/storage/emulated/0/MyLibsTesting`.

#
**Step 2.**
Run function `onSuccessCheckPermitions`  inside `onRequestPermissionsResult`. **Only Need 1 Time in FirstActivity**:

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //   /storage/emulated/0/MyLibsTesting/folder1
        //   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        //   /storage/emulated/0/MyLibsTesting/folder2
        String[] folders = new String[]{"/folder1","/folder1/folder1_1","/folder2"};
        if (FGDir.initFolder(folders)){
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }

    ...

}
```

Result will be like this
```
|-- external
    |-- MyLibsTesting
        |-- folder1
            |-- folder1_1
        |-- folder2
```
3 way that you can use.

```java
//   /storage/emulated/0/MyLibsTesting/folder1
//   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
//   /storage/emulated/0/MyLibsTesting/folder2

// 1
String[] folders = {"/folder1","/folder1/folder1_1","/folder2"};
FGDir.initFolder(folders);

// 2
String[] folders = new String[]{"/folder1","/folder1/folder1_1","/folder2"};
FGDir.initFolder(folders);

// 3
FGDir.initFolder("/folder1","/folder1/folder1_1","/folder2");

```

#
**Step 3.**
FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/CreateFolder/MainActivity.java)).

Preview :
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example1.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example3.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example4.jpg)|
|--|--|--|--|
|Request Permition |Folder MyLibsTesting created|`folder1` dan `folder2` created|`folder1_1` inside `folder1` has created|

---

## Function Global File
This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition) or use your own.

**CRUD File.** now we will make `Directory` and `File` with simple and fast :

**Step 4. Create File**  
If you has grand your permission, now run function `onSuccessCheckPermitions` inside `onRequestPermissionsResult` :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};
   
        //buat file dalam folder App
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt 
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if(FGFile.initFile(fileName, saveTo,data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```

#
**Step 5.** 

FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/CreateFile/MainActivity.java)).

Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|
|--|--|--|
|Folder `MyLibsTesting` created|`MyFile.txt` inside `MyLibsTesting` created|`MyFile.txt`|

#
**Step 6. Read File**
You can read the file that you created, here is the code.

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};

        //membaca file dalam folder App
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if(FGFile.initFile(fileName, saveTo,data)){

            //   /storage/emulated/0/MyLibsTesting/MyFile.txt
            List<String> list = FGFile.readFile("/MyFile.txt");
            String value_0 = list.get(0);
            Toast.makeText(this, "Jumlah baris : "+list.size() , Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }

    }
    
    ...

}
```

#
**Step 7.**
\
FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/ReadFile/MainActivity.java)).

#
**Step 8. AppentText**
Run function `onSuccessCheckPermitions` inside `onRequestPermissionsResult` to add new line string in file or `appent text`, parameters that you need to declaration:

1. `onSuccessCheckPermitions` same like **Step 7**, add function `onAppentText` to excecute the process.
2. `onAppentText`->`String path` path file that you want to add new value in new line.
3. `messages` put your value here, .

Now execute the code :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //buat file dalam folder App
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};

        //Buat File
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if(FGFile.initFile(fileName, saveTo,data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();

            String path = "/MyFile.txt";
            List<String> list = FGFile.readFile(path);
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
        if (FGDir.isFileExists(path)){
            String[] messages = {"Pesan ini akan ditambahkan ke file di line baru 1","Pesan ini akan ditambahkan ke file di line baru 2"};

            //function untuk menambah text ke file yang sudah dibuat sebelumnya
            if(FGFile.appentText(path, messages)){
                Toast.makeText(this, "Line baru ditambah ke file", Toast.LENGTH_SHORT).show();

                List<String> list = FGFile.readFile(path);
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

**notes.** 
  - Make sure you craeted file in **Step 4**.
  
#
**Step 9.**
FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/AppentText/MainActivity.java)).

Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example9.jpg)|
|--|--|--|--|
|Folder `MyLibsTesting` created|`MyFile.txt` inside `MyLibsTesting` has created|`MyFile.txt` before `appent text`|`MyFile.txt` after `appent text`|

---

## Function Global Zip
This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition) or use your own.

**String Base64 ke Zip.** we will make file zip from Base64 dan extract it to your folder fast and simple :

#
**Step 10. Encode Base64/Md5 to Zip**
Run function `onSuccessCheckPermitions`  di dalam `onRequestPermissionsResult`, make sure you have grant permission,
declaration variable :
1. `fileName` real file ZIP name.
2. `base64EncodeFromFile` from ZIP to Base64.
3. `md5EncodeFromFile` ZIP to Md5 to validate Base64 not corrupt.
4. `zipLocation` you extract will be in this folder.

now excecure the code :

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
        //atau
        //   /storage/emulated/0/MyLibsTesting/
        //String zipLocation = "/"; // jika tidak mau diletakan dalam folder

        //decode string menjadi file dan extrack ke tujuan zipLocation
        //   /storage/emulated/0/MyLibsTesting/zipLocation
        if (FGZip.initFileFromStringToZipToFile(fileName, zipLocation ,base64EncodeFromFile,md5EncodeFromFile, true)){
            Toast.makeText(this, "Success load data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gagal load data", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```

**notes.**
  - Make sure `fileName` is real name from file zip that you decode to Base64 and Md5.

#
**Step 11.**
\
FullCode akan tampak seperti ini ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/EncodeBase64Md5toZip/MainActivity.java)).
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example7.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example8.jpg)|
|--|--|--|
|Folder `MyLibsTesting` sudah dibuat|`ExternalBase64Md5ToZip.zip` yang berada didalam `MyLibsTesting` sudah dibuat dari string Base64|`ExernalBase64Md5ToZip.db` adalah file hasil extract dari file `ExternalBase64Md5ToZip.zip`|

---

## Function Global Image Camera
**Library ini membutuhkan permition terlebih dahulu. kamu bisa pakai cara kamu, atau kamu bisa pakai cara yang selalu Zein pakai**.
\
**Contoh Multi Check Permissions. Kamu bisa lihat contohnya disini MultiPermition ([docs](https://github.com/gzeinnumer/MultiPermition)) (Ikuti Step 1 - Step 9)** :
\
**Mengambil foto dengan camera.** Disini kita akan mencoba membuat file image yang kita ambil dari camera dengan mempertahankan kualitas gambar dan menyimpannya lansung ke external, dengan cepat dan mudah :

#
**Step 12. Take Image From Camera And Compress**
\
Jika sudah mengikuti cara **MultiPermition ([docs](https://github.com/gzeinnumer/MultiPermition)) (Ikuti Step 1 - Step 9)** dan `onRequestPermissionsResult` sudah mendapat permition yang dibutuhkan, maka kita akan membuat dan menjalankan function `onSuccessCheckPermitions` di dalam `onRequestPermissionsResult`.

**Step 13.**
\
**activity_main.xml**
\
Tambahkan kode berikut:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
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

**Step 14.**
\
**manifest.xml**
\
Tambahkan Permition, lalu didalam `application` tambahkan tag `provider` untuk memberikan izin pada sistem menyimpan image secara temporary untuk proses compress image :

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

**Step 15.**
\
**file_provider_paths.xml**
\
Pada directory `res` buat folder `xml` dan buat file dengan nama `file_provider_paths.xml`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path
        name="my_images"
        path="Android/data/com.gzeinnumer.mylibstesting/files/DCIM" />
    
        <!-- ganti com.gzeinnumer.mylibstesting dengan package name project kamu-->
</paths>
```

Cara mengetahui package name project:
```java
//ini adalah Package Name Project
package com.gzeinnumer.mylibstesting;

public class MainActivity extends AppCompatActivity {
    
}
```

**Step 16.**
\
**MainActivity.java** 
Tambahkan permition yang dibutuhkan ke array :

```java
public class MainActivity extends AppCompatActivity {

    ...

    //pada contoh yang sudah zein siapkan di https://github.com/gzeinnumer/MultiPermition, tambahkan 1 permition lagi yaitu CAMERA.
    String[] permissions = new String[]{
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    };
    
    ...

}
```

**Step 17.**
\
Pada function `onSuccessCheckPermitions` kita bisa mengatifkan fitur ini agar bisa mengambil gambar dengan jernih, tambahkan kode berikut :

```java
public class MainActivity extends AppCompatActivity {

    ...
    
    //1
    static final int REQUEST_TAKE_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onSuccessCheckPermitions() {
        //2
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

    //3
    //jalankan intent untuk membuka kamera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                photoFile = FGFile.createImageFile(getApplicationContext(), fileName);
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
    
    //4
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
                Toast.makeText(this, "Image Path : "+mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    ...

}
```

#
**Step 18.**
\
FullCode akan tampak seperti ini ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/TakeImageFromCameraAndCompress/MainActivity.java)).
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example10.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example11.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example12.jpg)|
|--|--|--|
|Tampilan awal, tekan Button untuk membuka kamera|Hasil foto yang diambil, tekan oke untuk lanjutkan|Akan muncul Toast untuk memberitahu lokasi foto disimpan|
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example14.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example16.jpg)|
|folder `Foto` otomatis terbuat|File sudah tersimpan pada folder `Foto`|Detail pada galery|Ukuran foto kecil, tapi resolusi dipertahankan tetap besar|-|

---

## Function Global Image Galery
**Library ini membutuhkan permition terlebih dahulu. kamu bisa pakai cara kamu, atau kamu bisa pakai cara yang selalu Zein pakai**.
\
**Contoh Multi Check Permissions. Kamu bisa lihat contohnya disini MultiPermition ([docs](https://github.com/gzeinnumer/MultiPermition)) (Ikuti Step 1 - Step 9)** :
\
**Mengambil foto dari galery.** Disini kita akan mencoba mengambil foto dari galery, lalu mengcomress dengan mempertahankan kualitasnya lalu menyimpannya ke folder aplikasi yang sudah kita buar sebelumnya , dengan cepat dan mudah :

#
**Step 19. Take Image From Galery**
Hampir sama dengan **Step 17**, hanya saja berbeda action Intent dan prosess pengcrompressan Image, Tambahkan kode seperti berikut :

```java
public class MainActivity extends AppCompatActivity {

    ...

    //1
    static final int REQUEST_GALLERY_PHOTO = 3;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onSuccessCheckPermitions() {
        //2
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

    //3
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    //4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(FGFile.getRealPathFromUri(getApplicationContext(),selectedImage)));
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
**Step 20.**
\
FullCode akan tampak seperti ini ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/TakeImageFromGalery/MainActivity.java)).
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example17.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example18.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example19.jpg)|
|--|--|--|
|Tampilan awal, tekan Button untuk membuka galery|Pilih media|Pilih foto|
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example20.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example21.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example22.jpg)|
|Preview foto|Folder foto otomatis terbuat|Foto sudah ada pada directory yang sudah diset|Detail pada galery|

---

## Function Global Image Internet
**Step 21.**
\
**Library ini membutuhkan permition terlebih dahulu. kamu bisa pakai cara kamu, atau kamu bisa pakai cara yang selalu Zein pakai**.
\
**Contoh Multi Check Permissions. Kamu bisa lihat contohnya disini MultiPermition ([docs](https://github.com/gzeinnumer/MultiPermition)) (Ikuti Step 1 - Step 9)** :
\
**Download and Save** Disini kita akan membuat function yang akan mendownload gambar dan menyimpannya ke folder tujuan.

#
**Step 22.**
\
**Manifest.** Tambahkan permition Internet ke file manifest untuk mengizinkan file didownload dari Intenet.

```xml
<manifest >

    <uses-permission android:name="android.permission.INTERNET" />

    <application >
        ...
    </application>

</manifest>
```

**Step 23. Load Image From Internet and Save**
\
Membuat function `onSuccessCheckPermitions` disini kita akan mendeklarasikan :
1. `imgUrl` adalah link image. 
2. `saveTo` lokasi foto akan disimpan.

Jika 2 hal tersebut sudah dideklarasi, maka silahkan gunakan function seperti dibawah.
\
Pada function `onSuccessCheckPermitions` kita bisa mendowload gambar dari internet dan menyimpannya ke direktori yang kita mau, dan jika image sudah didownload, maka image tidak akan di download lagi.

```java
public class MainActivity extends AppCompatActivity {

    ...

    ImageView imageView;
    private void onSuccessCheckPermitions() {
        imageView = findViewById(R.id.img);

        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";
        String saveTo = "/Foto_Download"; //   /storage/emulated/0/MyLibsTesting/Foto_Download
//        String saveTo = "/"; //   /storage/emulated/0/MyLibsTesting/     //Jika tidak mau diletakan dalam folder
        String fileName = "file name.jpg";
 
        // jika file name ada di akhir link seperti dibawah, maka kamu bsa gunakan cara seperti ini
        // imgUrl = "https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg";
        // String fileName = imgUrl.substring(url.lastIndexOf('/') + 1, url.length());

        //pilih 1 atau 2
        //1. jika isNew true maka file lama akan dihapus dan diganti dengan yang baru.
        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, true);
        //2. jika isNew false maka akan otomatis load file dan disimpan, tapi jika file belum ada, maka akan tetap didownload.
        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, false);
    }
    
    ...

}
```

#
**Step 24.**
\
FullCode akan tampak seperti ini ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/LoadImageFromInternetandSave/MainActivity.java)).
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example23.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example24.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example25.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example26.jpg)|
|--|--|--|--|
|Request Permition|Gambar berhasil didownload|Folder `Foto_Download` sudah dibuat|Folder `file name.jpg` sudah didownload dan disimpan|

---

FullCode dari Step 1 - Step 24 dapat dilihat disini ([example](https://github.com/gzeinnumer/MyLibDirectoryExample)).

---

```
Copyright 2020 M. Fadli Zein
```
