package com.gzeinnumer.gzndirectory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class FunctionGlobalFile {

    //create file
    public static boolean initFile(String fileName,String... text) {
        if (FunctionGlobalDir.appFolder.length()==0){
            return false;
        }
        if (fileName.length()==0){
            return false;
        }
        if (!fileName.substring(0,1).equals("/")){
            fileName = "/"+fileName;
        }
        File file = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + fileName);

        return  processFile(file, text);
    }

    private static boolean processFile(File file, String... text) {
        try{
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(f);
            for (String d: text){
                writer.println(d);
            }
            writer.flush();
            writer.close();
            f.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
