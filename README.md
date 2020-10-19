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

## Download

Minimum Android SDK Version 16

#### Gradle
**Step 1.** add maven `jitpack.io` to `build.gradle` (Project) :

```gradle
allprojects {
  repositories {
    google()
    jcenter()
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** add dependensi to `build.gradle` (Module) :

```gradle
dependencies {
  implementation 'com.github.gzeinnumer:MyLibDirectory:versi'
}
```

---

This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition) or use your own.

### Feature List
- [x] [Function Global Directory](https://github.com/gzeinnumer/MyLibDirectory/blob/dev-1/README_1.md)
  - [Create Folder](https://github.com/gzeinnumer/MyLibDirectory/blob/dev-1/README_1.md#create-folder)
- [x] [Function Global File](https://github.com/gzeinnumer/MyLibDirectory/blob/dev-1/README_2.md)
  - Step 4. Create File
  - Step 6. Read File
  - Step 8. AppentText
- [x] [Function Global Zip](https://github.com/gzeinnumer/MyLibDirectory/blob/dev-1/README_3.md)
  - Step 10. Encode Base64/Md5 to Zip
- [x] [Function Global Image Camera](https://github.com/gzeinnumer/MyLibDirectory/blob/dev-1/README_4.md)
  - Step 12. Take Image From Camera And Compress
- [x] [Function Global Image Galery](https://github.com/gzeinnumer/MyLibDirectory/blob/dev-1/README_5.md)
  - Step 19. Take Image From Galery
- [x] [Function Global Image Internet](https://github.com/gzeinnumer/MyLibDirectory/blob/dev-1/README_6.md)
  - Step 23. Load Image From Internet and Save
- [x] Cek file exists

### Function Global Directory
> Example : FGDir.initExternalDirectoryName(valueString);

| Name                        | Return    | Parameter              | Keterangan                                           |
|:----------------------------|:----------|:-----------------------|:-----------------------------------------------------|
| `initExternalDirectoryName` | `void`    | `String appFolder`     | Function to declare your own app folder on external  |
| `initFolder`                | `boolean` | `String... folderName` | Make folder in own app folder in external            |
| `isFileExists`              | `boolean` | `String path`          | To check is `directory` or `file` has created or not |

### Function Global File
> Example : FGFile.readFile(valueString);

| Name                        | Return    | Parameter                                                                             | Keterangan                                                               |
|:----------------------------|:----------|:--------------------------------------------------------------------------------------|:-------------------------------------------------------------------------|
| `initFile`                  | `boolean` | `String fileName, String saveTo, String... text`                                      | To make file `MyFile.txt` and put value to it                            |
| `readFile`                  | `boolean` | `String path`                                                                         | To read value from file txt                                              |
| `appentText`                | `boolean` | `String path, String... msg`                                                          | Add new line to existing file                                            |
| `initFileImageFromInternet` | `boolean` | `String imgUrl, String saveTo, String filename, ImageView sendImageTo, boolean isNew` | To download image and save to external                                   |
| `createImageFile`           | `File`    | `Context context, String fileName`                                                    | To save file image as temporary file before save to your external folder |
| `getRealPathFromUri`        | `String`  | `Context context, Uri contentUri`                                                     | Get name of file from path/url                                           |

### Function Global Zip
> Example : FGZip.initFileFromStringToZipToFile(valueString, valueString, valueString, valueString, valueBoolean);

| Name                            | Return    | Parameter                                                                                                   | Keterangan                                                          |
|:--------------------------------|:----------|:------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------|
| `initFileFromStringToZipToFile` | `boolean` | `String fileName, String zipLocation, String base64EncodeFromFile, String md5EncodeFromFile, boolean isNew` | Make file ZIP from Base64 and extract it to your destination folder |

---

**DEBUG.** If you find some trouble you can trace with this step.
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/debug.jpg)|
|--|

---

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

---

FullCode from all fitur ([example](https://github.com/gzeinnumer/MyLibDirectoryExample)).

---

```
Copyright 2020 M. Fadli Zein
```
