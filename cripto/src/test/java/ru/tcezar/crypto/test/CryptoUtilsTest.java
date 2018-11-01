package ru.tcezar.crypto.test;

import org.junit.Assert;
import org.junit.Test;
import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoUtils;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class CryptoUtilsTest {

    private static final Path publicKeyPath = Paths.get(System.getProperty("user.dir") + "\\publicKey");
    private static final Path privateKeyPath = Paths.get(System.getProperty("user.dir") + "\\privateKey");
    private static final String testMsg = "Hello. It's test message!";

    @Test
    public void simpleCrypto() throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        System.out.println("Start message: " + testMsg);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptBytes = cipher.doFinal(testMsg.getBytes());
        System.out.println("EnCrypt message: " + new String(encryptBytes));

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptBytes = decryptCipher.doFinal(encryptBytes);
        System.out.println("DeCrypt message: " + new String(decryptBytes));
    }


    @Test
    public void test() throws IOException, GeneralSecurityException {
        ICryptoUtils criptoUtils = new CryptoUtils();

        IPairKeys pairKeys = criptoUtils.generateKeys();
        Assert.assertNotNull(pairKeys);

        criptoUtils.saveKeysToFiles(pairKeys, publicKeyPath, privateKeyPath);

        IPairKeys pairKeysFromFiles = criptoUtils.getKeysFromFiles(publicKeyPath, privateKeyPath);
        Assert.assertEquals(pairKeys.getPublicKey(), pairKeysFromFiles.getPublicKey());
        Assert.assertEquals(pairKeys.getPrivateKey(), pairKeysFromFiles.getPrivateKey());
    }

}
