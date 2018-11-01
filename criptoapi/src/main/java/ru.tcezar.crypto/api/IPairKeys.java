package ru.tcezar.crypto.api;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

/*
    Интерфейс для хранения публичного и приватного ключа
 */
public interface IPairKeys extends Serializable {

    /**
     * Метод получения публичного ключа
     * @return публичный ключ
     */
    PublicKey getPublicKey();

    /**
     * Метод получения приватного ключа
     * @return приватный ключ
     */
    PrivateKey getPrivateKey();
}
