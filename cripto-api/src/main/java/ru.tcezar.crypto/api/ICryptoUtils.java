package ru.tcezar.crypto.api;

import java.io.IOException;
import java.nio.file.Path;
import java.security.spec.InvalidKeySpecException;

/*
    Интерфейс для работы с шифрование
 */
public interface ICryptoUtils {

    /**
     * Метод для генерации ключей
     *
     * @return возвращает пару ключей
     */
    IPairKeys generateKeys();

    /**
     * Метод сохранения ключей в файлы
     *
     * @param keys           ключи
     * @param publicKeyPath  путь до файла публичного ключа
     * @param privateKeyPath путь до файла приватного ключа
     */
    void saveKeysToFiles(IPairKeys keys, Path publicKeyPath, Path privateKeyPath) throws IOException;

    /**
     * Метод получения ключей из файлов
     *
     * @param publicKeyPath  путь до файла публичного ключа
     * @param privateKeyPath путь до файла приватного ключа
     * @return набор ключей
     */
    IPairKeys getKeysFromFiles(Path publicKeyPath, Path privateKeyPath) throws IOException, InvalidKeySpecException;
}
