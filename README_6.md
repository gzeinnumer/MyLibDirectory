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