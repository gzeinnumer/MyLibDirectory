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

//        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";
//        String saveTo = "/Foto_Download";
//        String fileName = "file name.jpg";
//
////        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, true);
//
////        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, false);
//
////        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, true, new FGFile.ImageLoadCallBack() {
////            @Override
////            public void onBitmapReturn(Bitmap bitmap) {
////                imageView.setImageBitmap(bitmap);
////            }
////        });
//
//        FGFile.initFileImageFromInternet(imgUrl, saveTo, fileName, false, new FGFile.ImageLoadCallBack() {
//            @Override
//            public void onBitmapReturn(Bitmap bitmap) {
//                imageView.setImageBitmap(bitmap);
//            }
//        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dispatchTakePictureIntent();
                dispatchGalleryIntent();
            }
        });
    }

    static final int REQUEST_TAKE_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;

    private void dispatchTakePictureIntent() {
        mCompressor = new FileCompressor(this);
        mCompressor.setDestinationDirectoryPath("/Foto");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                String fileName = "File_Name";
                photoFile = FGFile.createImageFile(getApplicationContext(), fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    static final int REQUEST_GALLERY_PHOTO = 3;
    private void dispatchGalleryIntent() {
        mCompressor = new FileCompressor(this);
        mCompressor.setDestinationDirectoryPath("/Foto");

        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    //4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    Glide.with(this).load(mPhotoFile).into(imageView);
                    Toast.makeText(this, "Image Path : "+mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
                    textView.setText(mPhotoFile.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(selectedImage);
                    Glide.with(this).load(mPhotoFile).into(imageView);
                    Toast.makeText(this, "Image Path : "+mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
                    textView.setText(mPhotoFile.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}