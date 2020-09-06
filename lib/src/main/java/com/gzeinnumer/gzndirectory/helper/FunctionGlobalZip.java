package com.gzeinnumer.gzndirectory.helper;

import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.gzeinnumer.gzndirectory.helper.FunctionGlobalDir.logSystemFunctionGlobal;

public class FunctionGlobalZip {

    //start genFile
    //jika ingin replace file yang sudah ada, maka beri flag true, jika tidak maka false saja
    public static boolean initFileFromStringToZipToFile(String fileName, String zipLocation, String base64EncodeFromFile, String md5EncodeFromFile, boolean isNew) {
        if (fileName == null) {
            logSystemFunctionGlobal("initFileFromStringToZipToFile", "FileName tidak boleh null");
            return false;
        }
        if (zipLocation == null) {
            logSystemFunctionGlobal("initFileFromStringToZipToFile", "ZipLocation tidak boleh null");
            return false;
        }
        if (base64EncodeFromFile == null) {
            logSystemFunctionGlobal("initFileFromStringToZipToFile", "Base64EncodeFromFile tidak boleh null");
            return false;
        }
        if (md5EncodeFromFile == null) {
            logSystemFunctionGlobal("initFileFromStringToZipToFile", "Md5EncodeFromFile tidak boleh null");
            return false;
        }
        if (FunctionGlobalDir.appFolder.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Folder External untuk aplikasi belum dideklarasi");
            return false;
        }
        if (!FunctionGlobalDir.isFileExists("")) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Folder External untuk aplikasi tidak di temukan");
            if (FunctionGlobalDir.initFolder("")) {
                FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Folder External sudah dibuat");
            } else {
                FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Folder External gagal dibuat");
                return false;
            }
        }
        if (fileName.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "FileName tidak boleh kosong");
            return false;
        }
        if (!fileName.substring(0, 1).equals("/")) {
            fileName = "/" + fileName;
        }
        if (zipLocation.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "ZipLocation tidak boleh kosong");
            return false;
        }
        if (!zipLocation.substring(0, 1).equals("/")) {
            zipLocation = "/" + zipLocation;
        }
        if (base64EncodeFromFile.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Base64EncodeFromFile tidak boleh kosong");
            return false;
        }
        if (md5EncodeFromFile.length() == 0) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Md5EncodeFromFile tidak boleh kosong");
            return false;
        }
        if (FunctionGlobalDir.isFileExists(fileName) && !isNew) {
            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "File sudah dibuat");
            return true;
        } else {
            if (converToZip(fileName, base64EncodeFromFile)) {
                if (compareMd5(fileName, md5EncodeFromFile)) {
                    try {
                        if (unzip(fileName, zipLocation)) {
                            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Success membuat file zip");
                            return true;
                        } else {
                            FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Gagal membuat file zip");
                            return false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Gagal membuat file zip " + e.getMessage());
                        return false;
                    }
                } else {
                    FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Gagal compareMd5 ");
                    return false;
                }
            } else {
                FunctionGlobalDir.logSystemFunctionGlobal("initFileFromStringToZipToFile", "Gagal converToZip ");
                return false;
            }
        }
    }

    private static boolean converToZip(String fileName, String base64) {
        final File fileZip = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + fileName);
        byte[] pdfAsBytes;
        try {
            pdfAsBytes = Base64.decode(base64, 0);
            FileOutputStream os;
            try {
                os = new FileOutputStream(fileZip, false);
                os.write(pdfAsBytes);
                os.flush();
                os.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                FunctionGlobalDir.logSystemFunctionGlobal("converToZip", "Gagal converToZip File Not Found " + e.getMessage());
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                FunctionGlobalDir.logSystemFunctionGlobal("converToZip", "Gagal converToZip IOException " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean compareMd5(String fileName, String md5OriginValue) {
        try {
            String md5Origin = md5OriginValue;
            String filePath = FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + fileName;
            FileInputStream fis = new FileInputStream(filePath);
            String md5Checksum = Md5Checksum.md5(fis);
            return md5Checksum.equals(md5Origin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            FunctionGlobalDir.logSystemFunctionGlobal("compareMd5", "Gagal compareMd5 FileNotFoundException " + e.getMessage());
        }
        return false;
    }

    private static boolean unzip(String fileName, String zipLocation) throws IOException {
        String zipFile = FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + fileName;
        String unzipLocation = FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + zipLocation;
        ZipFile archive = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> e = archive.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            File file = new File(unzipLocation, entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                InputStream in = archive.getInputStream(entry);
                OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();

                // write the output file (You have now copied the file)
                out.flush();
                out.close();
                return true;
            }
        }
        return false;
    }
    //end genFile
}
