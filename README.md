<h1 align="center">
  MyLibDirectory
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-1.5.0-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/Java-Suport-green?logo=java&style=flat"></a>
    <a><img src="https://img.shields.io/badge/Koltin-Suport-green?logo=kotlin&style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <br>
    <p>Simple way to make <b>Directory</b>, <b>File</b>, and <b>Zip</b>.</p>
</div>

---

## Download
Add maven `jitpack.io` and `dependencies` in `build.gradle (Project)` :
```gradle
// build.gradle project
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

// build.gradle app/module
dependencies {
  ...
  implementation 'com.github.gzeinnumer:MyLibDirectory:version'
}
```

---

This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition) or use your own.

## Feature List
- [x] [Function Global Directory](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_1.md)
  - [Create Folder](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_1.md#create-folder)
  - [Is File Exists](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_1.md#is-file-exists)
  - [Delete Folder](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_1.md#delete-folder)
- [x] [Function Global File](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_2.md)
  - [Create File](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_2.md#create-file)
  - [Read File](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_2.md#step-4-read-file)
  - [AppentText](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_2.md#step-6-appenttext)
- [x] [Function Global Zip](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_3.md)
  - [Encode Base64/Md5 to Zip](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_3.md#base64-to-zip)
- [x] [Function Global Image Camera](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_4.md)
  - [Take Image From Camera And Compress](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_4.md#image-camera)
- [x] [Function Global Image Galery](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_5.md)
  - [Take Image From Galery And Compress](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_5.md#image-galery)
- [x] [Function Global Image Internet](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_6.md)
  - [Load Image From Internet and Save](https://github.com/gzeinnumer/MyLibDirectory/blob/master/README_6.md#image-internet)
- [x] Cek File Exists

### Function Global Directory
> Example : FGDir.initExternalDirectoryName(valueString);

| Name                        | Return    | Parameter              | Description                                           |
|:----------------------------|:----------|:-----------------------|:-----------------------------------------------------|
| `initExternalDirectoryName` | `void`    | `String appFolder`     | Function to declare your own app folder on external  |
| `initFolder`                | `boolean` | `String... folderName` | Make folder in own app folder in external            |
| `isFileExists`              | `boolean` | `String path`          | To check is `directory` or `file` has created or not |

### Function Global File
> Example : FGFile.readFile(valueString);

| Name         | Return    | Parameter                                        | Description                                   |
|:-------------|:----------|:-------------------------------------------------|:----------------------------------------------|
| `initFile`   | `boolean` | `String fileName, String saveTo, String... text` | To make file `MyFile.txt` and put value to it |
| `readFile`   | `boolean` | `String path`                                    | To read value from file txt                   |
| `appentText` | `boolean` | `String path, String... msg`                     | Add new line to existing file                 |

### Function Global Zip
> Example : FGZip.initFileFromStringToZipToFile(valueString, valueString, valueString, valueString, valueBoolean);

| Name                            | Return    | Parameter                                                                                                   | Description                                                          |
|:--------------------------------|:----------|:------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------|
| `initFileFromStringToZipToFile` | `boolean` | `String fileName, String zipLocation, String base64EncodeFromFile, String md5EncodeFromFile, boolean isNew` | Make file ZIP from Base64 and extract it to your destination folder |

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

#
### Function Global Image Galery
> Example : FGFile.readFile(valueString);

| Name                 | Return        | Parameter                          | Description                                                              |
|:---------------------|:--------------|:-----------------------------------|:-------------------------------------------------------------------------|
| `FileCompressor`     | `Constructor` | `Context context`                  | Constructor default                                                      |
| `FileCompressor`     | `Constructor` | `Context context, int quality`     | Constructor with custom quality compress                                 |
| `createImageFile`    | `File`        | `Context context, String fileName` | To save file image as temporary file before save to your external folder |
| `getRealPathFromUri` | `String`      | `Context context, Uri contentUri`  | Get name of file from path/url                                           |

#
### Function Global Image Internet
> Example : FGFile.readFile(valueString);

| Name                        | Return    | Parameter                                                                             | Description                            |
|:----------------------------|:----------|:--------------------------------------------------------------------------------------|:---------------------------------------|
| `initFileImageFromInternet` | `boolean` | `String imgUrl, String saveTo, String filename, ImageView sendImageTo, boolean isNew` | `Deprecated` To download image and save to external |
| `initFileImageFromInternet` | `boolean` | `String imgUrl, String saveTo, String filename, boolean isNew, CallBack callBack`     | To download image and save to external |

---

## Tech stack and 3rd library
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

**Debug** if you find trouble and function doesn't work you can trace with this

| ![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/debug.jpg) |
|:-------------------------------------------------------------------------------|
| Example `Logcat`                                                               |

---

### Version
- **1.4.3**
  - First Release
- **1.5.0**
  - Add Feature Delete Dir or File

---

### Contribution
You can sent your constibution to `branche` `open-pull`.

---

```
Copyright 2020 M. Fadli Zein
```
