package ru.lib.test;

import org.junit.Assert;
import org.junit.Test;
import ru.lib.api.ISettingKeeper;
import ru.lib.impl.SettingKeeper;

public class SettingKeeperTest {

    private static final String test_filepath1 = "test-config.properties";
    private static final String bad_test_filepath = "unknown.properties";

    @Test
    public void test1() {
        ISettingKeeper settingKeeper =  new SettingKeeper(test_filepath1);
        Assert.assertFalse(settingKeeper.getSettings().isEmpty());
    }

    @Test
    public void test2() {
        ISettingKeeper settingKeeper =  new SettingKeeper(bad_test_filepath);
        Assert.assertTrue(settingKeeper.getSettings().isEmpty());
    }

    @Test
    public void test3() {
        String code = "test_code";
        String value = "test_value";

        ISettingKeeper settingKeeper =  new SettingKeeper();
        settingKeeper.setSetting(code, value);
        Assert.assertEquals(value, settingKeeper.getSetting(code));
    }

    @Test
    public void test4() {
        Assert.assertNotNull(new SettingKeeper().toString());
    }
}
