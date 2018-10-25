package ru.lib.test;

import org.junit.Assert;
import org.junit.Test;
import ru.lib.api.ISettingKeeper;
import ru.lib.impl.SettingKeeper;

public class SettingKeeperTest {

    private static final String test_filepath1 = "\\src\\test\\resources\\test-config.txt";
    private static final String test_filepath2 = "\\src\\test\\resources\\bad-test-config.txt";
    private static final String bad_test_filepath = "\\src\\test\\resources\\unknown.txt";

    @Test
    public void test1() {
        ISettingKeeper settingKeeper =  new SettingKeeper(System.getProperty("user.dir") + test_filepath1);
        Assert.assertFalse(settingKeeper.getSettings().isEmpty());
    }

    @Test
    public void test2() {
        ISettingKeeper settingKeeper =  new SettingKeeper(System.getProperty("user.dir") + bad_test_filepath);
        Assert.assertTrue(settingKeeper.getSettings().isEmpty());
    }

    @Test
    public void test3() {
        ISettingKeeper settingKeeper =  new SettingKeeper(System.getProperty("user.dir") + test_filepath2);
        Assert.assertTrue(settingKeeper.getSettings().isEmpty());
    }

    @Test
    public void test4() {
        String code = "test_code";
        String value = "test_value";

        ISettingKeeper settingKeeper =  new SettingKeeper();
        settingKeeper.setSetting(code, value);
        Assert.assertEquals(value, settingKeeper.getSetting(code));
    }

    @Test
    public void test5() {
        Assert.assertNotNull(new SettingKeeper().toString());
    }
}
