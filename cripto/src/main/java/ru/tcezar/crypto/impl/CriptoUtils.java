package ru.tcezar.crypto.impl;

import ru.tcezar.crypto.api.ICriptoUtils;
import ru.tcezar.crypto.api.IPairKeys;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class CriptoUtils implements ICriptoUtils {

    @Override
    public IPairKeys generateKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return new PairKeys(keyPair.getPublic(), keyPair.getPrivate());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("При генерации пары ключей произошла ошибка!");
            e.printStackTrace();
            return null;
        }
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
}
