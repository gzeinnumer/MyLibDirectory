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