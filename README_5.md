<h1 align="center">
    MyLibDirectory - Function Global Image Galery
</h1>

**Take foto from galery.** Take foto from galery and save it with small size and keep it HD.

This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition2) or use your own.

#
### Function Global Image Galery
> Example : FGFile.createImageFile(context, valueString);

| Name                 | Return        | Parameter                           | Description                                                              |
|:---------------------|:--------------|:------------------------------------|:-------------------------------------------------------------------------|
| `FileCompressor`     | `Constructor` | `Context context`                   | Constructor default                                                      |
| `FileCompressor`     | `Constructor` | `Context context, int quality`      | Constructor with custom quality compress                                 |
| `createImageFile`    | `File`        | `Context context, String fileName`  | To save file image as temporary file before save to your external folder |
| `getRealPathFromUri` | `String`      | `Context context, Uri contentUri`   | Get name of file from path/url                                           |

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

        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_provider_paths" />
        </provider>

        ...

    </application>

</manifest>
```
**notes.**
  - In this tutorial, i will put every file and folder in `/storage/emulated/0/MyLibsTesting`.

---
### Step 2. USE
#### Take Image From Galery And Compress
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
* Intent To **Galery**

In function `dispatchGalleryIntent` start your request to open camera, call that function in `onClick`:

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

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryIntent();
            }
        });
    }

    //3
    private void dispatchGalleryIntent() {
        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        // int quality = 50;
        // mCompressor = new FileCompressor(this, quality);
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini

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
                    mPhotoFile = mCompressor.compressToFile(selectedImage);
                    Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
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
[FullCode](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/TakeImageFromGalery/MainActivity.java) Preview :

| ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example17.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example18.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example19.jpg) |
|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| First Preview                                                                      | Choise media                                                                       | Pick foto                                                                          |

| ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example20.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example21.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example22.jpg) |
|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| Preview foto                                                                       | Folder `foto` has created                                                          | Foto inside directory that you set before                                          | Detail image on galery                                                             |
