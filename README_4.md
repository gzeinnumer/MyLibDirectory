<h1 align="center">
    MyLibDirectory - Function Global Directory
</h1>

### Function Global Directory
> Example : FGDir.initExternalDirectoryName(valueString);

| Name                        | Return    | Parameter              | Keterangan                                                                               |
|:----------------------------|:----------|:-----------------------|:-----------------------------------------------------------------------------------------|
| `initExternalDirectoryName` | `void`    | `String appFolder`     | Function untuk mendeklarasi folder nama yang akan dibuat di external                     |
| `initFolder`                | `boolean` | `String... folderName` | Membuat folder pada direktori yang sudah dideklarasi di atas `initExternalDirectoryName` |
| `isFileExists`              | `boolean` | `String path`          | untuk mengecek apakah File/Folder sudah ada atau belum                                   |

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