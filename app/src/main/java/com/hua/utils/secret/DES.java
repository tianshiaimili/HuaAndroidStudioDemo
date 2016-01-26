package com.hua.utils.secret;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sundh on 2016/1/22.
 */
public class DES {
    private static byte[] iv = {1,2,3,4,5,6,7,8};
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
//      IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
//        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        byte[] keyData = encryptKey.getBytes();
        SecretKeySpec key = new SecretKeySpec(keyData, 0, keyData.length, "DES");
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        return Base64.encode(encryptedData);
    }
    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        byte[] byteMi = new Base64().decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
//      IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
//        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");

        byte[] keyData = decryptKey.getBytes();
        SecretKeySpec key = new SecretKeySpec(keyData, 0, keyData.length, "DES");

//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

    public static void test(){


//        String key = "12345678";
//        String text = "12345678";

        String key = "AWS13789";
        String text = "5BBKoY/ru95YH6KVPViDBQ==";

        try {
            String result1 = DES.encryptDES(text,key);
            String result2 = DES.decryptDES(text, key);



            Log.i("DES sexret", result1);
            Log.i("DES sexret", result2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}