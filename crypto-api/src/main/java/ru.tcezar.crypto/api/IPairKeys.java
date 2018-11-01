package ru.tcezar.crypto.api;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

/*
    Интерфейс для хранения публичного и приватного ключа
 */
public interface IPairKeys extends Serializable {

    /**
     * Метод получения публичного ключа
     *
     * @return публичный ключ
     */
    PublicKey getPublicKey();

    /**
     * Метод получения приватного ключа
     *
     * @return приватный ключ
     */
    PrivateKey getPrivateKey();

    /**
     * Метод шифрования данных
     * @param data данные в байтах
     * @return зашифрованные данные
     */
    byte[] encrypt(byte[] data) throws GeneralSecurityException;

    /**
     * Метод разшифрования данных
     * @param data зашифрованные данные в байтах
     * @return разшифрованные данные
     */
    byte[] decrypt(byte[] data) throws GeneralSecurityException;
}
