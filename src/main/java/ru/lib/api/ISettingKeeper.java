package ru.lib.api;

import java.util.Properties;

public interface ISettingKeeper {

    public Object getSetting(String code);

    public void setSetting(String code, String value);

    public Properties getSettings();

}
