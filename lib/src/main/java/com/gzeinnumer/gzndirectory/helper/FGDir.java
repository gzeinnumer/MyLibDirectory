package com.gzeinnumer.gzndirectory.helper;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FGDir {

    public final static String getStorageCard = Environment.getExternalStorageDirectory().toString();
    public static String appFolder = "";

    public static void logSystemFunctionGlobal(String function, String msg) {
        Log.d("MyLibDirectory_Debug", function + "_" + msg);
    }

    public static void myLogD(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void initExternalDirectoryName(String appFolder) {
        if (appFolder == null) {
            logSystemFunctionGlobal("initExternalDirectoryName", "AppFolder tidak boleh null");
            return;
        }
        if (!appFolder.substring(0, 1).equals("/")) {
            appFolder = "/" + appFolder;
        }
        FGDir.appFolder = appFolder;
    }

    public static boolean initFolder(String... folderName) {
        if (folderName == null) {
            logSystemFunctionGlobal("initFolder", "FolderName tidak boleh null");
            return false;
        }
        if (appFolder.length() == 0) {
            logSystemFunctionGlobal("initFolder", "Folder External untuk aplikasi belum dideklarasi");
            return false;
        }
        File folder;

        // create appFolder
        folder = new File(getStorageCard + appFolder);
        if (!folder.exists()) {
            if (!creatingFolder(folder)) {
                logSystemFunctionGlobal("initFolder", "Gagal membuat direktory External untuk aplikasi");
                return false;
            }
        }
        if (folderName.length > 0) {
            for (String s : folderName) {
                if (s.length() > 0) {
                    if (!s.substring(0, 1).equals("/")) {
                        s = "/" + s;
                    }
                    folder = new File(getStorageCard + appFolder + s);
                    if (!folder.exists()) {
                        if (!creatingFolder(folder)) {
                            logSystemFunctionGlobal("initFolder", "Gagal membuat direktory " + s);
                            return false;
                        }
                    }
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

    public static boolean isFileExists(String path) {
        if (path == null) {
            logSystemFunctionGlobal("isFileExists", "Path tidak boleh null");
            return false;
        }
        File file = new File(getStorageCard + appFolder + path);
        return file.exists();
    }

    public static boolean deleteDir(String path){
        return new File(FGDir.getStorageCard+FGDir.appFolder+path).delete();
    }
}
