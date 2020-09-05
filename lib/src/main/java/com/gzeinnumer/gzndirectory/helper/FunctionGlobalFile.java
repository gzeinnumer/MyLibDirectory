package com.gzeinnumer.gzndirectory.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FunctionGlobalFile {

    //create file
    public static boolean initFile(String fileName,String... text) {
        if (FunctionGlobalDir.appFolder.length()==0){
            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Folder External untuk aplikasi belum di deklarasi");
            return false;
        }
        if (fileName.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "FileName tidak boleh kosong");
            return false;
        }
        if (!fileName.substring(0, 1).equals("/")) {
            fileName = "/" + fileName;
        }
//        if (text.length==0){
//            FunctionGlobalDir.logSystemFunctionGlobal("initFile", "Tidak ada text yang akan dikirim");
//            return false;
//        }
        File file = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + fileName);

        return processFile(file, text);
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
        if (!path.substring(0,1).equals("/")){
            path = "/"+path;
        }
        if (!FunctionGlobalDir.isFileExists(FunctionGlobalDir.appFolder+path)){
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
        if (path.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "Path tidak boleh kosong");
            return false;
        }
        if (!path.substring(0, 1).equals("/")) {
            path = "/" + path;
        }
        FunctionGlobalDir.myLogD("appentText", FunctionGlobalDir.getStorageCard + path);
        if (!FunctionGlobalDir.isFileExists(path)) {
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "File tidak ditemukan");
            return false;
        }
        if (msg.length == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "Message tidak boleh kosong");
            return false;
        }
        FileWriter fw;
        try {
            fw = new FileWriter(FunctionGlobalDir.getStorageCard + path, true);
            for (String d : msg) {
                fw.write(d + "\n");
            }
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            FunctionGlobalDir.logSystemFunctionGlobal("appentText", "Gagal mengapent text ke file " + e.getMessage());
            return false;
        }
    }
}
