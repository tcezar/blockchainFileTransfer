package ru.tcezar.config;

import java.io.*;
import java.util.Properties;

public class ConfigKeeper {

    public static final String configDir = System.getProperty("user.dir") + File.separator + "config";

    private static final String propertyFilepath = configDir + File.separator + "main.properties";
    private static final Properties properties = new Properties();

    public static final String publicKeyCode = "publicKey";
    public static final String privateKeyCode = "privateKey";
    public static final String blockchainFilepath = configDir + File.separator + "blockchain.data";

    static {
        System.out.println("Processing of file by filepath: " + propertyFilepath);
        try {
            InputStream inputStream = new FileInputStream(propertyFilepath);
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                System.out.println("Failed to load file by filepath: " + propertyFilepath);
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found by filepath: " + propertyFilepath);
            e.printStackTrace();
        }
    }

    public static Object getConfig(String code) {
        return properties.get(code);
    }

    public static void setConfig(String code, String value) {
        if (code != null) {
            System.out.println("Set setting in map (code: " + code +", value: " + value +")");
            properties.put(code, value);
        }
    }

    public static Properties getAllConfigs() {
        return (Properties) properties.clone();
    }

    public static void saveConfigsToFile() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(propertyFilepath);
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file by filepath: " + propertyFilepath);
            e.printStackTrace();
            try {
                File file = new File(propertyFilepath);
                file.getParentFile().mkdirs();
                file.createNewFile();
                outputStream = new FileOutputStream(file);
            } catch (IOException e1) {
                System.out.println("Can't create file by filepath: " + propertyFilepath);
                e1.printStackTrace();
            }
        } finally {
            if (outputStream != null) {
                try {
                    properties.store(outputStream, null);
                } catch (IOException e) {
                    System.out.println("Can't save file by filepath: " + propertyFilepath);
                    e.printStackTrace();
                }
            }
        }
    }

    public static Boolean checkKeysFilepathConsist() {
        return properties.containsKey(publicKeyCode) && properties.containsKey(privateKeyCode);
    }
}
