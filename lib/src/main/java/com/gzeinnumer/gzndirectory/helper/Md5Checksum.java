package com.gzeinnumer.gzndirectory.helper;

import java.io.InputStream;
import java.security.MessageDigest;

public class Md5Checksum {
    private static char[] hexDigits = "0123456789abcdef".toCharArray();

    public static String md5(InputStream is) {
        String md5 = "";

        try {
            byte[] bytes = new byte[4096];
            int read = 0;
            MessageDigest digest = MessageDigest.getInstance("MD5");

            while ((read = is.read(bytes)) != -1) {
                digest.update(bytes, 0, read);
            }

            byte[] messageDigest = digest.digest();

            StringBuilder sb = new StringBuilder(32);

            for (byte b : messageDigest) {
                sb.append(hexDigits[(b >> 4) & 0x0f]);
                sb.append(hexDigits[b & 0x0f]);
            }

            md5 = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            FunctionGlobalDir.logSystemFunctionGlobal("md5", "Gagal Md5Checksum Exception " + e.getMessage());
        }

        return md5;
    }
}
