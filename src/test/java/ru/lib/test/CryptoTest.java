package ru.lib.test;

import org.junit.Test;
import ru.lib.impl.CryptoHelper;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class CryptoTest {

    private static final String testMsg = "Hello. It's test message!";

    @Test
    public void test1() throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
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
    public void test2() throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        CryptoHelper.generateKeys();

        Key publicKey = CryptoHelper.getPublicKeyFromFile(CryptoHelper.publicKeyFilename);
        Key privateKey = CryptoHelper.getPrivateKeyFromFile(CryptoHelper.privateKeyFilename);
        System.out.println("Start message: " + testMsg);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptBytes = cipher.doFinal(testMsg.getBytes());
        System.out.println("EnCrypt message: " + new String(encryptBytes));

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptBytes = decryptCipher.doFinal(encryptBytes);
        System.out.println("DeCrypt message: " + new String(decryptBytes));
    }
}

/*
        1.Generate Symmetric Key (AES with 128 bits)
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();

        2.Encrypt plain text using AES
        String plainText = "Please encrypt me urgently..."
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

        3.Encrypt the key using RSA public key
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();

        PublicKey puKey = keyPair.getPublic();
        PrivateKey prKey = keyPair.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.PUBLIC_KEY, puKey);
        byte[] encryptedKey = cipher.doFinal(secKey.getEncoded());

        4.Send encrypted data (byteCipherText) + encrypted AES Key (encryptedKey)

        5.On the client side, decrypt symmetric key using RSA private key
        cipher.init(Cipher.PRIVATE_KEY, prKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);

        6. Decrypt the cipher using decrypted symmetric key
        //Convert bytes to AES SecertKey
        SecretKey originalKey = new SecretKeySpec(decryptedKey , 0, decryptedKey .length, "AES");
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        String plainText = new String(bytePlainText);
*/