package com.hua.utils.secret;

import android.util.Log;

import com.hua.activity.pay.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 * Created by sundh on 2016/1/22.
 */
public class DESSecret {


    public static String encryptDES(byte[] datasource, String password) {
        try {
            String encryptedData = null;
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            // 加密，并把字节数组编码成字符串
            return Base64.encode(cipher.doFinal(datasource));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decryptDES(String src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        byte[] byteMi = new Base64().decode(src);
        return new String(cipher.doFinal(byteMi));
    }

    public static void testSecret() {

        //待加密内容
//        String str = "测试内容";
        String str = "64616";

        //密码，长度要是8的倍数
//        String password = "12345678";
//        String password = "AWS137896ED0GUJ632OKOJ19UY";
        String password = "AWS13789";
        String result = DESSecret.encryptDES(str.getBytes(), password);
        Log.d("DES", "加密后内容为：" + (result));

        //直接将如上内容解密
//        String temp = "nkszi8A1vu8=";
        String temp = "5BBKoY/ru95YH6KVPViDBQ==";
        try {
            String decryResult = DESSecret.decryptDES(temp, password);
            Log.d("DES", "解密后内容为：" + (decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
            Log.d("DES", "err");
        }

    }

}

