package com.gzeinnumer.gzndirectory.helper;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FunctionGlobalDir {

    public final static String getStorageCard = Environment.getExternalStorageDirectory().toString();
    public static String appFolder = "";

    public static void logSystemFunctionGlobal(String function, String msg) {
        Log.d("MyLibDirectory_Debug", function + "_" + msg);
    }

    public static void myLogD(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void initExternalDirectoryName(String appFolder) {
        if (!appFolder.substring(0, 1).equals("/")) {
            appFolder = "/" + appFolder;
        }
        FunctionGlobalDir.appFolder = appFolder;
    }

    public static boolean initFolder(String... folderName) {
        if (appFolder.length()==0){
            logSystemFunctionGlobal("initFolder", "Folder External untuk aplikasi belum di deklarasi");
            return false;
        }
        File folder;

        // create folder
        folder = new File(getStorageCard + appFolder);
        if (!folder.exists()) {
            if (!creatingFolder(folder)){
                logSystemFunctionGlobal("initFolder", "Gagal membuat direktory External untuk aplikasi");
                return false;
            }
        }
        for (String s : folderName){
            if (!s.substring(0,1).equals("/")){
                s = "/"+s;
            }
            folder = new File(getStorageCard + appFolder + s);
            if (!folder.exists()) {
                if (!creatingFolder(folder)){
                    logSystemFunctionGlobal("initFolder", "Gagal membuat direktory " + s);
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean creatingFolder(File folder){
        try{
            if (folder.mkdirs()){
                logSystemFunctionGlobal("creatingFolder", "Success menjalankan mkdirs direktory External untuk aplikasi ");
            }
        } catch (Exception e){
            logSystemFunctionGlobal("creatingFolder", "Gagal menjalankan mkdirs direktory External untuk aplikasi " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean isFileExists(String path){
        File file = new File(getStorageCard + appFolder + path);
        return file.exists();
    }
}
