<h1 align="center">
    MyLibDirectory - Function Global Image Galery
</h1>

**Take foto from galery.** Take foto from galery and save it with small size and keep it HD.

#
### Function Global File
> Example : FGFile.readFile(valueString);

| Name                        | Return    | Parameter                                                                             | Keterangan                                                               |
|:----------------------------|:----------|:--------------------------------------------------------------------------------------|:-------------------------------------------------------------------------|
| `initFileImageFromInternet` | `boolean` | `String imgUrl, String saveTo, String filename, ImageView sendImageTo, boolean isNew` | To download image and save to external                                   |
| `createImageFile`           | `File`    | `Context context, String fileName`                                                    | To save file image as temporary file before save to your external folder |
| `getRealPathFromUri`        | `String`  | `Context context, Uri contentUri`                                                     | Get name of file from path/url                                           |

---
### Image Galery
#### Step 1. Enable Fitur.
Add 2 code on your `onCreate`. you need to declaration `Folder Name`
that you will use as you Folder Name in external. Now i am ussing
`MyLibsTesting`.

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
  - I suggest you to declaration `Folder Name` first, just **One Time** in your first activity inside function `onCreate`. example `SplashScreenActivity` or `MainActivity`.
  - In this tutorial, i will put every file and folder in `/storage/emulated/0/MyLibsTesting`.

#
#### Step 2. Take Image From Camera And Compress
Make View on `xml`

**activity_main.xml**
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
#### Step 3. Take Image From Galery

**MainActivity.java**
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
#### Step 4.
[FullCode](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/TakeImageFromGalery/MainActivity.java) Preview :

| ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example17.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example18.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example19.jpg) |
|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| First Preview                                                                      | Choise media                                                                       | Pick foto                                                                          |

| ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example20.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example21.jpg) | ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example22.jpg) |
|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| Preview foto                                                                       | Folder `foto` has created                                                          | Foto inside directory that you set before                                          | Detail image on galery                                                             |
