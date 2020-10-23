package com.gzeinnumer.mylibdirectory;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzeinnumer.gzndirectory.helper.FGDir;
import com.gzeinnumer.gzndirectory.helper.FGFile;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String externalFolderName = getApplication().getString(R.string.app_name);
        FGDir.initExternalDirectoryName(externalFolderName);

        final ImageView imageView = findViewById(R.id.img);

        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";
        String saveTo = "/Foto_Download";
        String fileName = "file name.jpg";

//        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, true);

//        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, false);

//        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, true, new FGFile.ImageLoadCallBack() {
//            @Override
//            public void onBitmapReturn(Bitmap bitmap) {
//                imageView.setImageBitmap(bitmap);
//            }
//        });

        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, false, new FGFile.ImageLoadCallBack() {
            @Override
            public void onBitmapReturn(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}