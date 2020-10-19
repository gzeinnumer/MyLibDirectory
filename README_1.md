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

This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition) or use your own.

**DEBUG.** If you find some trouble you can trace with this step.
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/debug.jpg)|
|--|

#
**Step 1. Create Folder** 
\
If you have grand your permission, add 2 code on your `onCreate`. you need to declaration `Folder Name` that you will use as you Folder Name in external. Now i am ussing `MyLibsTesting`.

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
**Step 2.**
Run function `onSuccessCheckPermitions`  inside `onRequestPermissionsResult`. **Only Need 1 Time in FirstActivity**:

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //   /storage/emulated/0/MyLibsTesting/folder1
        //   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        //   /storage/emulated/0/MyLibsTesting/folder2
        String[] folders = new String[]{"/folder1","/folder1/folder1_1","/folder2"};
        if (FGDir.initFolder(folders)){
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }

    ...

}
```

Result will be like this
```
|-- external
    |-- MyLibsTesting
        |-- folder1
            |-- folder1_1
        |-- folder2
```
3 way that you can use.

```java
//   /storage/emulated/0/MyLibsTesting/folder1
//   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
//   /storage/emulated/0/MyLibsTesting/folder2

// 1
String[] folders = {"/folder1","/folder1/folder1_1","/folder2"};
FGDir.initFolder(folders);

// 2
String[] folders = new String[]{"/folder1","/folder1/folder1_1","/folder2"};
FGDir.initFolder(folders);

// 3
FGDir.initFolder("/folder1","/folder1/folder1_1","/folder2");

```

#
**Step 3.**
FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/CreateFolder/MainActivity.java)).

Preview :
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example1.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example3.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example4.jpg)|
|--|--|--|--|
|Request Permition |Folder MyLibsTesting created|`folder1` dan `folder2` created|`folder1_1` inside `folder1` has created|
