<h1 align="center">
  MyLibDirectory
</h1>

<div align="center">
    <a><img src="https://img.shields.io/badge/Version-0.0.9-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <p>Kumpulan function untuk membuat folder dan file yang sering dipakai dalam development program android, dokumen ini dibuat berdasarkan pengalaman saya, kasih masukan kalau ada yang kurang. terimakasih karna sudah berkunjung</p>
</div>

---

### Feature List
- [x] Contoh Multi Check Permissions
- [x] Create Folder
- [x] Cek file exists
- [x] Create file dan menyimpan text
- [x] Membaca text dalam file
- [x] Buat file Zip dari Base64 dan MD5

### Tech stack and 3rd library
- MVVM ([docs](https://developer.android.com/jetpack/guide))

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

**Step 2.** tambahkan depedensi ke build.gradle (Module) :
```gradle
dependencies {
  implementation 'com.github.gzeinnumer:MyLibDirectory:versi'
}
```

---



---

```
Copyright 2020 M. Fadli Zein
```
