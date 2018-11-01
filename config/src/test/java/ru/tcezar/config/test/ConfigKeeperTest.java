package ru.tcezar.config.test;

import org.junit.Assert;
import org.junit.Test;
import ru.tcezar.config.api.IConfigKeeper;
import ru.tcezar.config.impl.ConfigKeeperImpl;

public class ConfigKeeperTest {

    private static final String testFilepath = "test-config.properties";
    private static final String badTestFilepath = "unknown.properties";

    @Test
    public void test1() {
        IConfigKeeper settingKeeper = new ConfigKeeperImpl(testFilepath);
        Assert.assertFalse(settingKeeper.getAllConfigs().isEmpty());
    }

    @Test
    public void test2() {
        IConfigKeeper settingKeeper = new ConfigKeeperImpl(badTestFilepath);
        Assert.assertTrue(settingKeeper.getAllConfigs().isEmpty());
    }

    @Test
    public void test3() {
        String code = "test_code";
        String value = "test_value";

        IConfigKeeper settingKeeper = new ConfigKeeperImpl();
        settingKeeper.setConfig(code, value);
        Assert.assertEquals(value, settingKeeper.getConfig(code));
    }

    @Test
    public void test4() {
        Assert.assertNotNull(new ConfigKeeperImpl().toString());
    }
}
