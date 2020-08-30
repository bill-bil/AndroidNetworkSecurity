package com.yinlei.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class DH {

    // 满足原根关系
    private static final int dhP = 23;
    private static final int dhG = 5;

    private int mPriKey;

    public DH() {
        Random r = new Random();
        mPriKey = r.nextInt(10);
        System.out.println("dh prikey is:"+mPriKey);
    }

    public int getPublicKey(){
        // 使用公钥计算公式，计算出自己的公钥。用来交换
        return (int) (Math.pow(dhG, mPriKey) % dhP);
    }

    // 接收对方的公钥，与自己的私钥通过密钥公式产生密钥，需要作为AES的密钥，需要转换为byte[]
    public byte[] getSecretKey(int publicKey) {
        int buf = (int) (Math.pow(publicKey, mPriKey) % dhP);
        return sha256(buf);
    }

    // 将dh交换后的双方相同的密钥做一个hash转换，转换为byte[256]类型做AES密钥
    private byte[] sha256(int data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(DataUtils.int2Byte(data));
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[]{-1};
    }



}
