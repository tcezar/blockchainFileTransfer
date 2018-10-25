package ru.lib.api;

import java.util.Map;

public interface ISettingKeeper {

    public String getSetting(String code);

    public void setSetting(String code, String value);

    public Map<String, String> getSettings();

}
