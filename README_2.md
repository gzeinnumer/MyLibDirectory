<h1 align="center">
    MyLibDirectory - Function Global File
</h1>

**CRUD File.** now we will make `Directory` and `File` with simple and fast.
#
### Function Global File
> Example : FGFile.readFile(valueString);

| Name                        | Return    | Parameter                                                                             | Description                                                               |
|:----------------------------|:----------|:--------------------------------------------------------------------------------------|:-------------------------------------------------------------------------|
| `initFile`                  | `boolean` | `String fileName, String saveTo, String... text`                                      | To make file `MyFile.txt` and put value to it                            |
| `isFileExists`              | `boolean` | `String path`                                                                         | To check is `directory` or `file` has created or not |
| `deleteDir`                 | `boolean` | `String path`                                                                         | To Delete `directory` or `file`                       |
| `readFile`                  | `boolean` | `String path`                                                                         | To read value from file txt                                              |
| `appentText`                | `boolean` | `String path, String... msg`                                                          | Add new line to existing file                                            |

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
### Step 2. USE.
#### Create File
If you has granted your permission, now run function
`onSuccessCheckPermitions` inside `onRequestPermissionsResult` :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};
   
        //buat file dalam folder App
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt 
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if(FGFile.initFile(fileName, saveTo,data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```
#
#### is File Exists.

```java
boolean isExists = FGFile.isFileExists("/MyFile.txt");
```

#
#### Delete File.

```java
boolean isDeleted = FGFile.deleteDir("/MyFile.txt");
```

#
[FullCode](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/CreateFile/MainActivity.java) Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|
|--|--|--|
|Folder `MyLibsTesting` created|`MyFile.txt` inside `MyLibsTesting` created|`MyFile.txt`|

---
#### Read File
You can read the file that you created, here is the code.

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        
        if(FGFile.initFile(fileName, saveTo, data)){

            //   /storage/emulated/0/MyLibsTesting/MyFile.txt
            List<String> list = FGFile.readFile("/MyFile.txt");
            String value_0 = list.get(0);
            Toast.makeText(this, "Jumlah baris : "+list.size() , Toast.LENGTH_SHORT).show();
        } else {
            
        }

    }
    
    ...

}
```

#
[FullCode](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/ReadFile/MainActivity.java)

---
#### AppentText
Run function `onSuccessCheckPermitions` inside
`onRequestPermissionsResult` to add new line string in file or `appent
text`, parameters that you need to declaration:

1. `onSuccessCheckPermitions` same like [**Create File**](#create-file), add function
   `onAppentText` to excecute the process.
2. `onAppentText`->`String path` path file that you want to add new value in new line.
3. `messages` put your value here.

Now execute the code :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        
        ...
        
        if(FGFile.initFile(fileName, saveTo,data)){
            
            ...
            
            //tambahkan fuction ini untuk menambahkan text pada file yang sudah dibuat
            onAppentText();
            
        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }

    private void onAppentText() {
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String path = "/MyFile.txt";
        if (FGDir.isFileExists(path)){
            String[] messages = {"Pesan ini akan ditambahkan ke file di line baru 1","Pesan ini akan ditambahkan ke file di line baru 2"};

            //function untuk menambah text ke file yang sudah dibuat sebelumnya
            if(FGFile.appentText(path, messages)){
                Toast.makeText(this, "Line baru ditambah ke file", Toast.LENGTH_SHORT).show();

                List<String> list = FGFile.readFile(path);
                Toast.makeText(this, "Jumlah baris setelah ditambahkan: "+list.size() , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ada error ketika add pesan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
    
    ...

}
```

#
[FullCode](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/AppentText/MainActivity.java) Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example9.jpg)|
|--|--|--|--|
|Folder `MyLibsTesting` created|`MyFile.txt` inside `MyLibsTesting` has created|`MyFile.txt` before `appent text`|`MyFile.txt` after `appent text`|
