package ru.tcezar.crypto.test;

import org.junit.Assert;
import org.junit.Test;
import ru.tcezar.crypto.api.ICriptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CriptoUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CriptoUtilsTest {

    private static final Path publicKeyPath = Paths.get(System.getProperty("user.dir") +"\\publicKey");

    @Test
    public void test() {
        ICriptoUtils criptoUtils = new CriptoUtils();

        IPairKeys pairKeys = criptoUtils.generateKeys();
        Assert.assertNotNull(pairKeys);

        //criptoUtils.saveKeys();
    }

}
