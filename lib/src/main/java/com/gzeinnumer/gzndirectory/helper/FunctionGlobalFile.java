package com.gzeinnumer.gzndirectory.helper;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.squareup.picasso.Target;

import static com.gzeinnumer.gzndirectory.helper.FunctionGlobalDir.logSystemFunctionGlobal;

public class FunctionGlobalFile {

    //create file
    public static boolean initFile(String fileName,String... text) {
        if (fileName == null) {
            logSystemFunctionGlobal("initFile", "FileName tidak boleh null");
            return false;
        }
        if (text == null) {
            logSystemFunctionGlobal("initFile", "Text tidak boleh null");
            return false;
        }
        if (FunctionGlobalDir.appFolder.length() == 0) {
            logSystemFunctionGlobal("initFile", "Folder External untuk aplikasi belum di deklarasi");
            return false;
        }
        if (!FunctionGlobalDir.isFileExists("")) {
            logSystemFunctionGlobal("initFile", "Folder External untuk aplikasi tidak di temukan");
            if (FunctionGlobalDir.initFolder("")) {
                logSystemFunctionGlobal("initFile", "Folder External sudah dibuat");
            } else {
                logSystemFunctionGlobal("initFile", "Folder External gagal dibuat");
                return false;
            }
        }
        if (fileName.length() == 0) {
            logSystemFunctionGlobal("initFile", "FileName tidak boleh kosong");
            return false;
        }
        if (!fileName.substring(0, 1).equals("/")) {
            fileName = "/" + fileName;
        }
        File file = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + fileName);

        return processFile(file, text);
    }

    private static boolean processFile(File file, String... text) {
        try{
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(f);

            if (text.length > 0) {
                for (String d : text) {
                    writer.println(d);
                }
            }

            writer.flush();
            writer.close();
            f.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            logSystemFunctionGlobal("processFile", "Gagal membuat file " + e.getMessage());
            return false;
        }
    }

    public static List<String> readFile(String path) {
        List<String> list = new ArrayList<>();
        if (path == null) {
            logSystemFunctionGlobal("readFile", "Path tidak boleh null");
            return list;
        }
        if (FunctionGlobalDir.appFolder.length() == 0) {
            logSystemFunctionGlobal("readFile", "Folder External untuk aplikasi belum dideklarasi");
            return list;
        }
        if (path.length() == 0) {
            logSystemFunctionGlobal("readFile", "Path tidak boleh kosong");
            return list;
        }
        if (!path.substring(0, 1).equals("/")) {
            path = "/" + path;
        }
        if (!FunctionGlobalDir.isFileExists(path)) {
            logSystemFunctionGlobal("readFile", "File tidak ditemukan");
            return list;
        }

        File file = new File(FunctionGlobalDir.getStorageCard+ FunctionGlobalDir.appFolder+ path);
        Scanner input;

        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logSystemFunctionGlobal("readFile", "Gagal membaca file");
            return list;
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }
        return list;
    }

    public static boolean appentText(String path, String... msg) {
        if (path == null) {
            logSystemFunctionGlobal("appentText", "Path tidak boleh null");
            return false;
        }
        if (msg == null) {
            logSystemFunctionGlobal("appentText", "Path tidak boleh null");
            return false;
        }
        if (FunctionGlobalDir.appFolder.length() == 0) {
            logSystemFunctionGlobal("appentText", "Folder External untuk aplikasi belum dideklarasi");
            return false;
        }
        if (!FunctionGlobalDir.isFileExists("")) {
            logSystemFunctionGlobal("appentText", "Folder External untuk aplikasi tidak di temukan");
            if (FunctionGlobalDir.initFolder("")) {
                logSystemFunctionGlobal("appentText", "Folder External sudah dibuat");
            } else {
                logSystemFunctionGlobal("appentText", "Folder External gagal dibuat");
                return false;
            }
        }
        if (path.length() == 0) {
            logSystemFunctionGlobal("appentText", "Path tidak boleh kosong");
            return false;
        }
        if (!path.substring(0, 1).equals("/")) {
            path = "/" + path;
        }
        if (!FunctionGlobalDir.isFileExists(path)) {
            logSystemFunctionGlobal("appentText", "File tidak ditemukan");
            return false;
        }
        FileWriter fw;
        try {
            fw = new FileWriter(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + path, true);
            if (msg.length > 0) {
                for (String d : msg) {
                    fw.write(d + "\n");
                }
            }
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logSystemFunctionGlobal("appentText", "Gagal mengapent text ke file " + e.getMessage());
            return false;
        }
    }

    public static void initFileImageFromInternet(final String imgUrl, final String saveTo, final String filename, final ImageView sendImageTo, final boolean isNew) {
        if (imgUrl == null) {
            logSystemFunctionGlobal("initFileImageFromInternet", "ImgUrl tidak boleh null");
            return;
        }
        if (saveTo == null) {
            logSystemFunctionGlobal("initFileImageFromInternet", "SaveTo tidak boleh null");
            return;
        }
        if (filename == null) {
            logSystemFunctionGlobal("initFileImageFromInternet", "Filename tidak boleh null");
            return;
        }
        if (sendImageTo == null) {
            logSystemFunctionGlobal("initFileImageFromInternet", "SendImageTo tidak boleh null");
            return;
        }
        if (FunctionGlobalDir.appFolder.length() == 0) {
            logSystemFunctionGlobal("initFileImageFromInternet", "Folder External untuk aplikasi belum dideklarasi");
        }
        if (!FunctionGlobalDir.isFileExists("")) {
            logSystemFunctionGlobal("initFileImageFromInternet", "Folder External untuk aplikasi tidak di temukan");
            if (FunctionGlobalDir.initFolder("")) {
                logSystemFunctionGlobal("initFileImageFromInternet", "Folder External sudah dibuat");
            } else {
                logSystemFunctionGlobal("initFileImageFromInternet", "Folder External gagal dibuat");
            }
        }
        File myDir = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + saveTo);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        if (filename.length() > 0) {
            myDir = new File(myDir, filename);
        } else {
            myDir = new File(myDir, new Date().toString() + ".jpg");
        }
        if (!myDir.exists() || isNew) { // file tidak ada or isNew : True
            final File finalMyDir = myDir;
            Picasso.get().load(imgUrl)
                    .placeholder(com.gzeinnumer.gzndirectory.R.drawable.ic_baseline_sync_24)
                    .error(com.gzeinnumer.gzndirectory.R.drawable.ic_baseline_broken_image_24)
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  try {
                                      if (!finalMyDir.exists() || isNew) {
                                          //jika isNew true maka foto lama akan dihapus dan diganti dengan yang baru
                                          //jika file tidak ditemukan maka file akan dibuat
                                          logSystemFunctionGlobal("initFileImageFromInternet", "Foto baru disimpan ke penyimpanan");
                                          FileOutputStream out = new FileOutputStream(finalMyDir);
                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                          out.flush();
                                          out.close();
                                      } else {
                                          //jika isNew false maka akan load file lama di penyimpanan
                                          logSystemFunctionGlobal("initFileImageFromInternet", "Foto lama di load dari penyimpanan");
                                          bitmap = BitmapFactory.decodeFile(finalMyDir.getAbsolutePath());
                                      }
                                      sendImageTo.setImageBitmap(bitmap);
                                  } catch (Exception e) {
                                      logSystemFunctionGlobal("initFileImageFromInternet", e.getMessage());
                                  }
                              }

                              @Override
                              public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                  logSystemFunctionGlobal("initFileImageFromInternet", e.getMessage());
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {
                              }
                          }
                    );
        } else {
            logSystemFunctionGlobal("initFileImageFromInternet", "Foto lama di load dari penyimpanan");
            Bitmap bitmap = BitmapFactory.decodeFile(myDir.getAbsolutePath());
            sendImageTo.setImageBitmap(bitmap);
        }
    }

    //simpan data di dalam root folder sebagai temporary
    public static File createImageFile(Context context, String fileName) throws IOException {
        String mFileName = "JPEG_" + fileName + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        return File.createTempFile(mFileName, ".jpg", storageDir);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
