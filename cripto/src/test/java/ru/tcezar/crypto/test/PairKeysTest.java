package ru.tcezar.crypto.test;

import org.junit.Assert;
import org.junit.Test;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.PairKeys;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class PairKeysTest {

    private static final String testMsg = "Hello. It's test message!";

    @Test
    public void test() throws GeneralSecurityException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        IPairKeys pairKeys = new PairKeys(keyPair.getPublic(), keyPair.getPrivate());
        Assert.assertNotNull(pairKeys.getPublicKey());
        Assert.assertNotNull(pairKeys.getPrivateKey());

        System.out.println("Start message: " + testMsg);
        byte[] encryptBytes = pairKeys.encrypt(testMsg.getBytes());
        System.out.println("EnCrypt message: " + new String(encryptBytes));
        byte[] decryptBytes = pairKeys.decrypt(encryptBytes);
        String newTestMsg = new String(decryptBytes);
        System.out.println("DeCrypt message: " + newTestMsg);
        Assert.assertEquals(testMsg, newTestMsg);
    }

}
