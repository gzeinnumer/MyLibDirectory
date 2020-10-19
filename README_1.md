<h1 align="center">
    MyLibDirectory - Function Global Directory
</h1>

**CRUD Directory.** now we will make `Directory` with simple and fast.
#
### Function Global Directory
> Example : FGDir.initExternalDirectoryName(valueString);

| Name                        | Return    | Parameter              | Keterangan                                           |
|:----------------------------|:----------|:-----------------------|:-----------------------------------------------------|
| `initExternalDirectoryName` | `void`    | `String appFolder`     | Function to declare your own app folder on external  |
| `initFolder`                | `boolean` | `String... folderName` | Make folder in own app folder in external            |
| `isFileExists`              | `boolean` | `String path`          | To check is `directory` or `file` has created or not |

---
### Create Folder
#### Step 1. Enable Fitur.
Add 2 code on your `onCreate`. you need to declaration `Folder Name` that you will use as you Folder Name in external. Now i am using `MyLibsTesting`.

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
#### Step 2. Create Folder.
If you have granted your permission, Run function
`onSuccessCheckPermitions` inside `onRequestPermissionsResult`. **Only
Need 1 Time in FirstActivity** :

```java
public class MainActivity extends AppCompatActivity {

    ...

    private void onSuccessCheckPermitions() {
        // /storage/emulated/0/MyLibsTesting/folder1
        // /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        // /storage/emulated/0/MyLibsTesting/folder2
        
        // String[] folders = {"/folder1","/folder1/folder1_1","/folder2"};
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

#
#### Step 3.
[FullCode](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/CreateFolder/MainActivity.java) Preview :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example1.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example2.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example3.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example4.jpg)|
|--|--|--|--|
|Request Permition |Folder MyLibsTesting created|`folder1` dan `folder2` created|`folder1_1` inside `folder1` has created|
