package com.gzeinnumer.gzndirectory.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.gzeinnumer.gzndirectory.R;
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

public class FunctionGlobalFile {

    //create file
    public static boolean initFile(String fileName,String... text) {
        if (FunctionGlobalDir.appFolder.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External untuk aplikasi belum di deklarasi");
            return false;
        }
        if (!FunctionGlobalDir.isFileExists("")) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External untuk aplikasi tidak di temukan");
            if (FunctionGlobalDir.initFolder("")) {
                FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External sudah dibuat");
            } else {
                FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External gagal dibuat");
                return false;
            }
        }
        if (fileName.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "FileName tidak boleh kosong");
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
            FunctionGlobalDir.logSystemFunctionGlobal("processFile", "Gagal membuat file " + e.getMessage());
            return false;
        }
    }

    public static List<String> readFile(String path){
        List<String> list = new ArrayList<String>();
        if (FunctionGlobalDir.appFolder.length()==0){
            FunctionGlobalDir.logSystemFunctionGlobal("readFile", "Folder External untuk aplikasi belum dideklarasi");
            return list;
        }
        if (path.length()==0){
            FunctionGlobalDir.logSystemFunctionGlobal("readFile", "Path tidak boleh kosong");
            return list;
        }
        if (!path.substring(0, 1).equals("/")) {
            path = "/" + path;
        }
        if (!FunctionGlobalDir.isFileExists(path)) {
            FunctionGlobalDir.logSystemFunctionGlobal("readFile", "File tidak ditemukan");
            return list;
        }

        File file = new File(FunctionGlobalDir.getStorageCard+ FunctionGlobalDir.appFolder+ path);
        Scanner input;

        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            FunctionGlobalDir.logSystemFunctionGlobal("readFile", "Gagal membaca file");
            return list;
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }
        return list;
    }

    public static boolean appentText(String path, String... msg) {
        if (FunctionGlobalDir.appFolder.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "Folder External untuk aplikasi belum dideklarasi");
            return false;
        }
        if (!FunctionGlobalDir.isFileExists("")) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External untuk aplikasi tidak di temukan");
            if (FunctionGlobalDir.initFolder("")) {
                FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External sudah dibuat");
            } else {
                FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External gagal dibuat");
                return false;
            }
        }
        if (path.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "Path tidak boleh kosong");
            return false;
        }
        if (!path.substring(0, 1).equals("/")) {
            path = "/" + path;
        }
        if (!FunctionGlobalDir.isFileExists(path)) {
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "File tidak ditemukan");
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
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "Gagal mengapent text ke file " + e.getMessage());
            return false;
        }
    }

    public static void initFileImageFromInternet(final String imgUrl, final String saveTo, final String filename, final ImageView sendImageTo, final boolean isNew) {
        if (FunctionGlobalDir.appFolder.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "Folder External untuk aplikasi belum dideklarasi");
        }
        if (!FunctionGlobalDir.isFileExists("")) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External untuk aplikasi tidak di temukan");
            if (FunctionGlobalDir.initFolder("")) {
                FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External sudah dibuat");
            } else {
                FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External gagal dibuat");
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
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  try {
                                      if (!finalMyDir.exists() || isNew) {
                                          //jika isNew true maka foto lama akan dihapus dan diganti dengan yang baru
                                          //jika file tidak ditemukan maka file akan dibuat
                                          FunctionGlobalDir.logSystemFunctionGlobal("initFileImage", "Foto baru disimpan ke penyimpanan");
                                          FileOutputStream out = new FileOutputStream(finalMyDir);
                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                          out.flush();
                                          out.close();
                                      } else {
                                          //jika isNew false maka akan load file lama di penyimpanan
                                          FunctionGlobalDir.logSystemFunctionGlobal("initFileImage", "Foto lama di load dari penyimpanan");
                                          bitmap = BitmapFactory.decodeFile(finalMyDir.getAbsolutePath());
                                      }
                                      sendImageTo.setImageBitmap(bitmap);
                                  } catch (Exception e) {
                                      FunctionGlobalDir.logSystemFunctionGlobal("initFileImage", e.getMessage());
                                  }
                              }

                              @Override
                              public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                  FunctionGlobalDir.logSystemFunctionGlobal("initFileImage", e.getMessage());
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {
                              }
                          }
                    );
        } else {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileImage", "Foto lama di load dari penyimpanan");
            Bitmap bitmap = BitmapFactory.decodeFile(myDir.getAbsolutePath());
            sendImageTo.setImageBitmap(bitmap);
        }
    }
}
