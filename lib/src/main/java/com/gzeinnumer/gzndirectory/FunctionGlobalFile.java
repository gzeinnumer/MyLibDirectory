package com.gzeinnumer.gzndirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        if (text.length==0){
            return false;
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

    public static List<String> readFile(String path){
        List<String> list = new ArrayList<String>();
        if (FunctionGlobalDir.appFolder.length()==0){
            return list;
        }
        if (path.length()==0){
            return list;
        }
        if (!path.substring(0,1).equals("/")){
            path = "/"+path;
        }
        if (!FunctionGlobalDir.isFileExists(path)){
            return list;
        }

        File file = new File(FunctionGlobalDir.getStorageCard+ path);
        Scanner input;

        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return list;
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }
        return list;
    }
}
