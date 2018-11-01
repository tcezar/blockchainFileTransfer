package ru.tcezar.crypto.api;

import java.nio.file.Path;

public interface ICriptoUtils {
    IPairKeys generateKeys();
    void saveKeys(Path privateKey, Path publicKey);
}
