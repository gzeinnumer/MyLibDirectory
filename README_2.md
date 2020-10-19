<h1 align="center">
    MyLibDirectory - Function Global File
</h1>

### Function Global File
> Example : FGFile.readFile(valueString);

| Name                        | Return    | Parameter                                                                             | Keterangan                                                                                                       |
|:----------------------------|:----------|:--------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------|
| `initFile`                  | `boolean` | `String fileName, String saveTo, String... text`                                      | Untuk membuat file baru lansung dengan text yang akan dimasukan ke file, seperti `MyFile.txt`                    |
| `readFile`                  | `boolean` | `String path`                                                                         | Untuk membaca isi dari file text yang sudah dibuat, pastikan file sudah dibuat terlebih dahulu dengan `initFile` |
| `appentText`                | `boolean` | `String path, String... msg`                                                          | Untuk menambah new line text ke file txt yang sudah dibuat sebelumnya dengan `initFile`                          |
| `initFileImageFromInternet` | `boolean` | `String imgUrl, String saveTo, String filename, ImageView sendImageTo, boolean isNew` | Untuk mendownload image dari internet dan menyimpannya ke penyimpanan                                            |
| `createImageFile`           | `File`    | `Context context, String fileName`                                                    | Untuk menyimpan data secara temporary sebelum di copy ke tujuan yang sudah diset                                 |
| `getRealPathFromUri`        | `String`  | `Context context, Uri contentUri`                                                     | Untuk mendapatkan nama asli dari file yang dipilih                                                               |

---

This library need Permission you can use this step [**MultiPermission**](https://github.com/gzeinnumer/MultiPermition) or use your own.

**CRUD File.** now we will make `Directory` and `File` with simple and fast :

**Step 4. Create File**
If you has grand your permission, now run function `onSuccessCheckPermitions` inside `onRequestPermissionsResult` :

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
**Step 5.**

FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/CreateFile/MainActivity.java)).

Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|
|--|--|--|
|Folder `MyLibsTesting` created|`MyFile.txt` inside `MyLibsTesting` created|`MyFile.txt`|

#
**Step 6. Read File**
You can read the file that you created, here is the code.

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};

        //membaca file dalam folder App
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if(FGFile.initFile(fileName, saveTo, data)){

            //   /storage/emulated/0/MyLibsTesting/MyFile.txt
            List<String> list = FGFile.readFile("/MyFile.txt");
            String value_0 = list.get(0);
            Toast.makeText(this, "Jumlah baris : "+list.size() , Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }

    }
    
    ...

}
```

#
**Step 7.**
FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/ReadFile/MainActivity.java)).

#
**Step 8. AppentText**
Run function `onSuccessCheckPermitions` inside `onRequestPermissionsResult` to add new line string in file or `appent text`, parameters that you need to declaration:

1. `onSuccessCheckPermitions` same like **Step 7**, add function `onAppentText` to excecute the process.
2. `onAppentText`->`String path` path file that you want to add new value in new line.
3. `messages` put your value here, .

Now execute the code :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        //buat file dalam folder App
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating","File Created"};

        //Buat File
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if(FGFile.initFile(fileName, saveTo,data)){
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();

            String path = "/MyFile.txt";
            List<String> list = FGFile.readFile(path);
            Toast.makeText(this, "Jumlah baris sebelum ditambahkan: "+list.size() , Toast.LENGTH_SHORT).show();
            
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

**notes.**
  - Make sure you craeted file in **Step 4**.

#
**Step 9.**
FullCode will be like this ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/AppentText/MainActivity.java)).

Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example5.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example6.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example9.jpg)|
|--|--|--|--|
|Folder `MyLibsTesting` created|`MyFile.txt` inside `MyLibsTesting` has created|`MyFile.txt` before `appent text`|`MyFile.txt` after `appent text`|
