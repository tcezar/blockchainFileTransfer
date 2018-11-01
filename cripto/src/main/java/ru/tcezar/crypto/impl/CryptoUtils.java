package ru.tcezar.crypto.impl;

import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.api.IPairKeys;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CryptoUtils implements ICryptoUtils {

    private KeyPairGenerator keyPairGenerator;
    private KeyFactory keyFactory;

    public CryptoUtils() throws NoSuchAlgorithmException {
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        keyFactory = KeyFactory.getInstance("RSA");
    }

    @Override
    public IPairKeys generateKeys() throws GeneralSecurityException {
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return new PairKeys(keyPair.getPublic(), keyPair.getPrivate());
    }

    @Override
    public void saveKeysToFiles(IPairKeys keys, Path publicKeyPath, Path privateKeyPath) throws IOException {
        byte[] publicKeyBytes = keys.getPublicKey().getEncoded();
        FileOutputStream fileOutputStream = new FileOutputStream(publicKeyPath.toFile());
        fileOutputStream.write(publicKeyBytes);
        fileOutputStream.close();

        byte[] privateKeyBytes = keys.getPrivateKey().getEncoded();
        fileOutputStream = new FileOutputStream(privateKeyPath.toFile());
        fileOutputStream.write(privateKeyBytes);
        fileOutputStream.close();
    }

    @Override
    public IPairKeys getKeysFromFiles(Path publicKeyPath, Path privateKeyPath) throws IOException, GeneralSecurityException {
        PublicKey publicKey = getPublicKeyFromFile(publicKeyPath);
        PrivateKey privateKey = getPrivateKeyFromFile(privateKeyPath);
        return new PairKeys(publicKey, privateKey);
    }

    private PublicKey getPublicKeyFromFile(Path publicKeyPath) throws IOException, InvalidKeySpecException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(getDataFromFile(publicKeyPath));
        return keyFactory.generatePublic(spec);
    }

    public PrivateKey getPrivateKeyFromFile(Path privateKeyPath) throws IOException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(getDataFromFile(privateKeyPath));
        return keyFactory.generatePrivate(spec);
    }

    private byte[] getDataFromFile(Path filePath) throws IOException {
        File file = filePath.toFile();
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        byte[] keyBytes = new byte[(int) file.length()];
        dataInputStream.readFully(keyBytes);
        dataInputStream.close();
        return keyBytes;
    }
}
