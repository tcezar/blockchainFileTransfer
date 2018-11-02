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

    public PairKeys(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

}
