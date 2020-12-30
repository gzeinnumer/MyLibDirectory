package com.gzeinnumer.mylibdirectory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gzeinnumer.gzndirectory.helper.FGDir;
import com.gzeinnumer.gzndirectory.helper.FGFile;
import com.gzeinnumer.gzndirectory.helper.imagePicker.FileCompressor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String externalFolderName = getApplication().getString(R.string.app_name);
        FGDir.initExternalDirectoryName(externalFolderName);

        imageView = findViewById(R.id.img);
        textView = findViewById(R.id.tv);

        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";
        String saveTo = "/Foto_Download";
        String fileName = "file name.jpg";

        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, true, new FGFile.ImageLoadCallBack() {
            @Override
            public void onBitmapReturn(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}