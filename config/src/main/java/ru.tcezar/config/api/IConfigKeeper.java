package ru.tcezar.config.api;

import java.util.Properties;

public interface IConfigKeeper {

    public Object getConfig(String code);

    public void setConfig(String code, String value);

    public Properties getAllConfigs();

}
