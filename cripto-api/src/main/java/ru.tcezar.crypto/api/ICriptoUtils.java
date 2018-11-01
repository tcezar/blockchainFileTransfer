package ru.tcezar.crypto.api;

import java.nio.file.Path;

/*
    Интерфейс для работы с шифрование
 */
public interface ICriptoUtils {

    /**
     * Метод для генерации ключей
     *
     * @return возвращает пару ключей
     */
    IPairKeys generateKeys();

    /**
     * Метод сохранения клюей в файлы
     *
     * @param keys           ключи
     * @param publicKeyPath  путь до файла публичного ключа
     * @param privateKeyPath путь до файла приватного ключа
     */
    void saveKeys(IPairKeys keys, Path publicKeyPath, Path privateKeyPath);
}
