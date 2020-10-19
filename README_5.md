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

## Function Global Image Galery
**Library ini membutuhkan permition terlebih dahulu. kamu bisa pakai cara kamu, atau kamu bisa pakai cara yang selalu Zein pakai**.
\
**Contoh Multi Check Permissions. Kamu bisa lihat contohnya disini MultiPermition ([docs](https://github.com/gzeinnumer/MultiPermition)) (Ikuti Step 1 - Step 9)** :
\
**Mengambil foto dari galery.** Disini kita akan mencoba mengambil foto dari galery, lalu mengcomress dengan mempertahankan kualitasnya lalu menyimpannya ke folder aplikasi yang sudah kita buar sebelumnya , dengan cepat dan mudah :

#
**Step 19. Take Image From Galery**
Hampir sama dengan **Step 17**, hanya saja berbeda action Intent dan prosess pengcrompressan Image, Tambahkan kode seperti berikut :

```java
public class MainActivity extends AppCompatActivity {

    ...

    //1
    static final int REQUEST_GALLERY_PHOTO = 3;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onSuccessCheckPermitions() {
        //2
        btnCamera = findViewById(R.id.btn_camera);

        imageView = findViewById(R.id.img);

        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini 

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryIntent();
            }
        });
    }

    //3
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    //4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(FGFile.getRealPathFromUri(getApplicationContext(),selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
            }
        }
    }
    
    ...

}
```

#
**Step 20.**
\
FullCode akan tampak seperti ini ([example](https://github.com/gzeinnumer/MyLibDirectory/blob/master/example/TakeImageFromGalery/MainActivity.java)).
\
Jika sukses maka akan tampil seperti ini :

|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example17.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example18.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example19.jpg)|
|--|--|--|
|Tampilan awal, tekan Button untuk membuka galery|Pilih media|Pilih foto|
|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example20.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example13.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example21.jpg)|![](https://github.com/gzeinnumer/MyLibDirectory/blob/master/assets/example22.jpg)|
|Preview foto|Folder foto otomatis terbuat|Foto sudah ada pada directory yang sudah diset|Detail pada galery|

---