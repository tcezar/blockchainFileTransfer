package ru.tcezar.crypto.impl;

import ru.tcezar.crypto.api.IPairKeys;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

public class CryptoHelper {

    private Cipher cipher;
    private Cipher decryptCipher;

    public CryptoHelper(IPairKeys pairKeys) throws GeneralSecurityException  {
        this.cipher = Cipher.getInstance("RSA");
        this.cipher.init(Cipher.ENCRYPT_MODE, pairKeys.getPublicKey());

        this.decryptCipher = Cipher.getInstance("RSA");
        this.decryptCipher.init(Cipher.DECRYPT_MODE, pairKeys.getPrivateKey());
    }

    public byte[] encrypt(byte[] data) throws GeneralSecurityException {
        return cipher.doFinal(data);
    }

    public byte[] decrypt(byte[] data) throws GeneralSecurityException {
        return decryptCipher.doFinal(data);
    }
}
