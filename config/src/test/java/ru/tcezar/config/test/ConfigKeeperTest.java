package ru.tcezar.config.test;

import org.junit.Assert;
import org.junit.Test;
import ru.tcezar.config.ConfigKeeper;

public class ConfigKeeperTest {

    @Test
    public void test() {
        String code = "test_code";
        String value = "test_value";

        ConfigKeeper.setConfig(code, value);
        Assert.assertEquals(value, ConfigKeeper.getConfig(code));
        Assert.assertFalse(ConfigKeeper.getAllConfigs().isEmpty());

        ConfigKeeper.saveConfigsToFile();
    }
}
