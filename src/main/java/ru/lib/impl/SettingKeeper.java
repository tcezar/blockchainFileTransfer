package ru.lib.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.lib.api.ISettingKeeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SettingKeeper implements ISettingKeeper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingKeeper.class);

    private Map<String, String> settings = new HashMap<>();

    public SettingKeeper() {

    }

    public SettingKeeper(String filepath) {
        try {
            LOGGER.info("Processing of file by filepath: {}", filepath);
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                try {
                    String[] strs = str.split("=");
                    setSetting(strs[0], strs[1]);
                } catch (Exception e) {
                    LOGGER.error("Failed to process file string line: '{}'!", str, e);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Failed to open file by filepath: '{}'!", filepath, e);
        }
    }

    @Override
    public String getSetting(String code) {
        return settings.get(code);
    }

    @Override
    public void setSetting(String code, String value) {
        if (code != null) {
            LOGGER.info("Set setting in map (code: {}, value: {})", code, value);
            settings.put(code, value);
        }
    }

    @Override
    public Map<String, String> getSettings() {
        return new HashMap<>(settings);
    }

    @Override
    public String toString() {
        return settings.entrySet().toString();
    }
}
