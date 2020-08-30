package com.yinlei.server;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.yinlei.crypto.AES;
import com.yinlei.crypto.DH;
import com.yinlei.crypto.RSA;
import com.yinlei.server.http.HttpCallback;
import com.yinlei.server.http.HttpServer;

public class Main {
    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrAXZLvzq5sWod84ltsHXkt++B\n" +
            "hIchDsHj26zlOd6NwmUKKwFT5RLdxME3lYK3XoHTZ6/u+uYBLp1U+/tzxNXZtTYT\n" +
            "Ut+a3LEXOu442/ToXmxR8pEHjegw6x6GUqbmPrP8f3Y6FxA6YlaRn+VkqU4w4X8p\n" +
            "1mqjL3/2as/i3Ty3nwIDAQAB";
    private static final String PRI_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKsBdku/Ormxah3z\n" +
            "iW2wdeS374GEhyEOwePbrOU53o3CZQorAVPlEt3EwTeVgrdegdNnr+765gEunVT7\n" +
            "+3PE1dm1NhNS35rcsRc67jjb9OhebFHykQeN6DDrHoZSpuY+s/x/djoXEDpiVpGf\n" +
            "5WSpTjDhfynWaqMvf/Zqz+LdPLefAgMBAAECgYB4sXR7q8FX9FbwQsXZTDU5M3Tw\n" +
            "VchF1bOVDaOuubRRG0XS8l1iiAhTy52PjI+QujwpKEJtCloxtWPH6n0jzWYctOZJ\n" +
            "pT7Uviet/ySuEn4Ewysq7fyhmP7sp0g8NTN4BUe3G3/W2xw/RtrHtW39KbptgFNL\n" +
            "ayVV7JsUgAeTD+FKQQJBAOH/5DXfO3nOg5nXb4J3gvM8rvmypdJ7sdZjOnLFdFB0\n" +
            "djRIyap7bjadMGCaDu+hTT1K+YEwheSFZlHJTpFG9r8CQQDBtLkA2aRYv73QgVM9\n" +
            "2Y+31v1IqwYIToSMRzqZTOytUBkvQvdvFRBW0NObMP+N2usvtWIGbA7FRRW3rDul\n" +
            "xFchAkEAuSjsEm8ozW0zSRHG3H+KHDr1qMKDNWX+mAWIV4wK9ffU+JVCFJk3/Srf\n" +
            "/eV6oz3miHcq4Hue11GsOQnTgxc/BQJAdhH9bno7Bemh/pTFs36Ibqt56bBtVeTe\n" +
            "QE8udcEAxsd7AmbUQX5aXkCdqwvbyZC2KHdsD7QP8MiiazlthiY+YQJAcLH/R82/\n" +
            "oiGh7X4XtdTe7ynMvjArBtLQ7I4ikQCkhQZm4Sk+9nuwcnVrbZKNaikW+PVK9WEK\n" +
            "9yRsZ/usG6Anrw==";
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {

        DH dhC = new DH();
        DH dhS = new DH();

        // 客户端创建公钥
        int publicKeyC = dhC.getPublicKey();
        // 服务端创建公钥
        int publicKeyS = dhS.getPublicKey();
        byte[] secretC = dhC.getSecretKey(publicKeyS);
        byte[] secretS = dhS.getSecretKey(publicKeyC);
        System.out.println("Client secret :" + new String(secretC));
        System.out.println("Server secret :" + new String(secretS));

//        int content = 123456;
//        String encrypted = RSA.encrypt(content, PUB_KEY);
//        System.out.println(encrypted);
//        System.out.println(RSA.decypt(encrypted, PRI_KEY));
//
//        AES aes = new AES();
//        String content1 = "yinlei is handsome!";
//        byte[] encryptedByte = aes.encrypt(content1);
//        System.out.println(new String(encryptedByte));
//        byte[] decrypted = aes.decrypt(encryptedByte);
//        System.out.println(new String(decrypted));

        HttpServer server = new HttpServer(new HttpCallback() {
            @Override
            public byte[] onResponse(String request) {
                return "yinlei".getBytes();
            }
        });
        server.startHttpServer();
    }
}
