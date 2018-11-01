package ru.tcezar.config.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tcezar.config.api.IConfigKeeper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigKeeper implements IConfigKeeper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigKeeper.class);

    private Properties properties = new Properties();

    public ConfigKeeper() {

    }

    public ConfigKeeper(String filepath) {
        LOGGER.info("Processing of file by filepath: {}", filepath);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filepath);

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                LOGGER.error("Failed to load file by filepath: '{}'!", filepath, e);
            }
        } else {
            LOGGER.error("File not found by filepath: '{}'!", filepath);
        }
    }

    @Override
    public Object getConfig(String code) {
        return properties.get(code);
    }

    @Override
    public void setConfig(String code, String value) {
        if (code != null) {
            LOGGER.info("Set setting in map (code: {}, value: {})", code, value);
            properties.put(code, value);
        }
    }

    @Override
    public Properties getAllConfigs() {
        return (Properties) properties.clone();
    }

    @Override
    public String toString() {
        return properties.entrySet().toString();
    }
}
