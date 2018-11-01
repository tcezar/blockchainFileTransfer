package ru.tcezar.crypto.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CryptoHelper {

    public static final String publicKeyFilename = "publicKey";
    public static final String privateKeyFilename = "privateKey";

    public static void generateKeys() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        FileOutputStream fileOutputStream = new FileOutputStream(publicKeyFilename);
        fileOutputStream.write(publicKeyBytes);
        fileOutputStream.close();

        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        fileOutputStream = new FileOutputStream(privateKeyFilename);
        fileOutputStream.write(privateKeyBytes);
        fileOutputStream.close();
    }

    public static PublicKey getPublicKeyFromFile(String fileName) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(getDataFromFileFile(fileName));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static PrivateKey getPrivateKeyFromFile(String fileName) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(getDataFromFileFile(fileName));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    private static byte[] getDataFromFileFile(String fileName) throws Exception {
        File file = new File(fileName);
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        byte[] keyBytes = new byte[(int) file.length()];
        dataInputStream.readFully(keyBytes);
        dataInputStream.close();
        return keyBytes;
    }
}
