package com.yinlei.crypto;

import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import java.nio.ByteBuffer;

public class DataUtils {

    /**
     * Base64编码后的字符串，返回解码后的byte[]
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] base64Decode(String data) {
        // java类提供的base64不太好用，android提供的好用
        try{
            return Base64.decode(data, Base64.NO_WRAP);
        }catch (Exception e) {
            // 当运行过程中找不到该函数表示在服务端运行
            return java.util.Base64.getMimeDecoder().decode(data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String base64Encode(byte[] data) {
        // 功能及接口设计与解码类似
        try {
            return Base64.encodeToString(data, Base64.NO_WRAP);
        }catch (Exception e){
            return java.util.Base64.getEncoder().encodeToString(data);
        }
    }

    //将int转换为byte[]数组，一个int对应4个byte
    public static byte[] int2Byte(int data) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(data);
        return buffer.array();
    }

}
