<h1 align="center">
    MyLibDirectory - Function Global Image Camera
</h1>

**Take Foto with Camera.** Take foto with camera and save it with small size and keep it HD.

This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition2) or use your own.

#
### Function Global Image Camera
> Example : FGFile.createImageFile(context,valueString);

| Name                          | Return        | Parameter                          | Description                                                               |
|:------------------------------|:--------------|:-----------------------------------|:-------------------------------------------------------------------------|
| `FileCompressor`              | `Constructor` | `Context context`                  | Constructor default                                                      |
| `FileCompressor`              | `Constructor` | `Context context, int quality`     | Constructor with custom quality compress                                 |
| `setDestinationDirectoryPath` | `void`        | `String location`                  | Set location for your image file                                         |
| `createImageFile`             | `File`        | `Context context, String fileName` | To save file image as temporary file before save to your external folder |
| `compressToFile`              | `File`        | `File file`                        | To compress from realsize to compressed size                             |

---
### Step 1. Enable Fitur.
Make Class `MyApp`, add 2 code on your `onCreate`. you need to declaration `External Folder Name` that you will use as you Folder Name in external. Now i am using `MyLibsTesting`.

```java
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String externalFolderName = getApplication().getString(R.string.app_name); //MyLibsTesting
        FGDir.initExternalDirectoryName(externalFolderName);
    }
}
```
Add `MyApp` to manifest `android:name=".MyApp"`.
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest >

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <application
        android:name=".MyApp"
        ...>

        ...

    </application>

</manifest>
```
**notes.**
  - In this tutorial, i will put every file and folder in `/storage/emulated/0/MyLibsTesting`.

---
### Step 2. USE
#### Take Image From Camera And Compress
* Design XML. Make View on `xml` **activity_main.xml**
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

#
* Add Permission on **manifest.xml**
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

#
* Temp File

In directory `res` make folder `xml` and make file **file_provider_paths.xml**.
```xml
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path
        name="my_images"
        path="Android/data/com.gzeinnumer.mylibstesting/files/DCIM" />

        <!-- ganti com.gzeinnumer.mylibstesting dengan package name project kamu-->
</paths>
```
Here is your project name:
```java
//ini adalah Package Name Project
package com.gzeinnumer.mylibstesting;

public class MainActivity extends AppCompatActivity { }
```

#
* **MainActivity.java** Add permission `CAMERA` :

```java
public class MainActivity extends AppCompatActivity {

    ...

    //pada contoh yang sudah zein siapkan di https://github.com/gzeinnumer/MultiPermition2, tambahkan 1 permition lagi yaitu CAMERA.
    String[] permissions = new String[]{
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    };

    ...

}
```

#
* Intent To **Camera**

In function `dispatchTakePictureIntent` start your request to open camera, call that function in `onClick`:

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
        mCompressor = new FileCompressor(this);
        // int quality = 50;
        // mCompressor = new FileCompressor(this, quality);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini

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
                    Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
                    Toast.makeText(this, "Image Path : "+mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    ...

}
```

#
[Example Project](https://github.com/gzeinnumer/SimpleTakeFotoHDMyLibDirectory)

Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example10.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example11.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example12.jpg)|
|--|--|--|
|First Preview|Foto Captured|Foto location on Toast|
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example14.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example16.jpg)|
|folder `Foto` automatically created|Photo saved in folder `Foto`|Detail on galery|Small size but ke resolution|-|

