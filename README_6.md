<h1 align="center">
    MyLibDirectory - Function Global Image Internet
</h1>

**Download and Save** download image and save it to directory that you want.

This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition2) or use your own.

#
### Function Global Image Internet
> Example : FGFile.initFileImageFromInternet(stringValue, stringValue, stringValue, ImageView, booleanValue);

| Name                        | Return    | Parameter                                                                             | Description                            |
|:----------------------------|:----------|:--------------------------------------------------------------------------------------|:---------------------------------------|
| `initFileImageFromInternet` | `boolean` | `Context context, String imgUrl, String saveTo, String filename, ImageView sendImageTo, boolean isNew` | To download image and save to external |
| `initFileImageFromInternet` | `boolean` | `Context context, String imgUrl, String saveTo, String filename, boolean isNew, CallBack callBack`     | To download image and save to external |

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
#### Download Image From Internet
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
<manifest >

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <application >
        ...
    </application>

</manifest>
```

#
* Load Image From Internet and Save
Make function `onSuccessCheckPermitions` you need to declare :

1. `imgUrl` is link image.
2. `saveTo` your foto will save in this folder.

Now execute the code :

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

         boolean overwriteExistingFiles =  true;

        //pilih 1 atau 2
        //1. jika isNew true maka file lama akan dihapus dan diganti dengan yang baru.
        FGFile.initFileImageFromInternet(getApplicationContext(), imgUrl, saveTo, fileName, overwriteExistingFiles, new FGFile.ImageLoadCallBack() {
            @Override
            public void onBitmapReturn(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        //2. jika isNew false maka akan otomatis load file dan disimpan, tapi jika file belum ada, maka akan tetap didownload.
        FGFile.initFileImageFromInternet(getApplicationContext(), imgUrl, saveTo, fileName, overwriteExistingFiles, new FGFile.ImageLoadCallBack() {
            @Override
            public void onBitmapReturn(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
    
    ...

}
```

#
[FullCode](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/LoadImageFromInternetandSave/MainActivity.java)

[Example Project](https://github.com/gzeinnumer/SimpleDownloadImageMyLibDirectory)

Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example23.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example24.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example25.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example26.jpg)|
|--|--|--|--|
|Request Permision|Success download image|Folder `Foto_Download` created|Folder `file name.jpg` has downloaded and save|

