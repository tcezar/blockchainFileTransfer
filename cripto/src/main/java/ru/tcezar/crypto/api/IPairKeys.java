package ru.tcezar.crypto.api;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface IPairKeys extends Serializable {
    PublicKey getPublicKey();
    PrivateKey getPrivateKey();
}
