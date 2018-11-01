package ru.tcezar.crypto.test;

import org.junit.Assert;
import org.junit.Test;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.PairKeys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class PairKeysTest {

    @Test
    public void test() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        IPairKeys pairKeys = new PairKeys(keyPair.getPublic(), keyPair.getPrivate());
        Assert.assertNotNull(pairKeys.getPublicKey());
        Assert.assertNotNull(pairKeys.getPrivateKey());
    }

}
