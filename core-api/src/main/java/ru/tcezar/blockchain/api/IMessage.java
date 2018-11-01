package ru.tcezar.blockchain.api;

import java.io.Serializable;

public interface IMessage<T extends Serializable> extends Serializable {
    UID getRecipient();

    UID getSender();

    T getMessage();

    String getTheme();
}
