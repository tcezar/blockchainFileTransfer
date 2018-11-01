package ru.tcezar.crypto.impl;

import ru.tcezar.crypto.api.IPairKeys;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class PairKeys implements IPairKeys {
    private static final long serialVersionUID = 7114619883328691363L;

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher cipher;
    private Cipher decryptCipher;

    public PairKeys(PublicKey publicKey, PrivateKey privateKey) throws GeneralSecurityException {
        this.publicKey = publicKey;
        this.cipher = Cipher.getInstance("RSA");
        this.cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);

        this.privateKey = privateKey;
        this.decryptCipher = Cipher.getInstance("RSA");
        this.decryptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
    }

    @Override
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    @Override
    public byte[] encrypt(byte[] data) throws GeneralSecurityException {
        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data) throws GeneralSecurityException {
        return decryptCipher.doFinal(data);
    }

}
