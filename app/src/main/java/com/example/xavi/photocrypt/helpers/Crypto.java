package com.example.xavi.photocrypt.helpers;



import android.content.Context;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Crypto {

    private final static int KEY_SIZE = 16;//change to 32
    private final static int BLOCK_SIZE = 8192;
    private static SecretKeySpec secretKey;
    private static IvParameterSpec ivSpec;

    public static void setKey(String newkey, Context context) throws IOException, NoSuchAlgorithmException {
        MessageDigest hash;
        File iv;
        byte[] key;
        byte[] ivArray = new byte[KEY_SIZE];

        if ((iv = new File(context.getFilesDir(), "iv")).exists()) {
            FileInputStream in = new FileInputStream(iv);
            in.read(ivArray, 0, KEY_SIZE);
            ivSpec = new IvParameterSpec(ivArray);

        } else {
            FileOutputStream iv_file = new FileOutputStream(iv);
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(ivArray);
            ivSpec = new IvParameterSpec(ivArray);
            iv_file.write(ivArray);
        }

        if (newkey.length() % KEY_SIZE != 0){
            key = padKey(newkey);
        } else {
            key = newkey.getBytes("UTF-8");
        }

        hash = MessageDigest.getInstance("MD5");
        key = hash.digest(key);
        secretKey = new SecretKeySpec(key, "AES");
    }

    public static byte[] getFile(String location) throws IOException
    {
        FileInputStream in = new FileInputStream(new File(location));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int r;
        byte[] data = new byte[BLOCK_SIZE];

        while ((r = in.read(data, 0, BLOCK_SIZE)) != -1) {
            buffer.write(data, 0, r);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    private static byte[] padKey(String key) throws IOException
    {
        byte[] byteKey = key.getBytes("UTF-8");
        byte[] paddedKey;
        int paddingLength = KEY_SIZE - byteKey.length % KEY_SIZE;
        byte[] pad = new byte[paddingLength];

        for (int i = 0; i < paddingLength; i++){
            pad[i] = (byte) paddingLength;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(byteKey);
        outputStream.write(pad);
        paddedKey = outputStream.toByteArray();

        return paddedKey;
    }

    public static byte[] encrypt(byte[] inputArray) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        return cipher.doFinal(inputArray);
    }

    public static byte[] decrypt(byte[] inputArray) throws GeneralSecurityException{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        return cipher.doFinal(inputArray);
    }

    public static void writeFile(String out_path, byte[] byteArray) throws IOException {
        FileOutputStream out = new FileOutputStream(new File(out_path));
        out.write(byteArray);
    }
}

