package com.gzeinnumer.gzndirectory;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FunctionGlobalDir {

    public final static String getStorageCard = Environment.getExternalStorageDirectory().toString();
    public static String appFolder = "";

    public FunctionGlobalDir(String appFolder) {
        if (!appFolder.substring(0,1).equals("/")){
            appFolder = "/"+appFolder;
        }
        FunctionGlobalDir.appFolder = appFolder;
    }

    private static final String TAG = "FunctionGlobalDir_";

    public static void myLogD(String tag,String msg){
        Log.d(tag, msg);
    }

    public static boolean initFolder(String... folderName) {
        if (appFolder.length()==0){
            return false;
        }
        File folder;

        // create folder
        folder = new File(getStorageCard + appFolder);
        if (!folder.exists()) {
            if (!creatingFolder(folder)){
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
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean creatingFolder(File folder){
        try{
            if (folder.mkdirs()){
                myLogD(TAG, "Folder created");
            }
        } catch (Exception e){
            myLogD(TAG, "Folder not created");
            return false;
        }
        return true;
    }

    public static boolean isFileExists(String path){
        File file = new File(getStorageCard + path);
        return file.exists();
    }
}
