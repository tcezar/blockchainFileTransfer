package ru.tcezar.blockchain.api;

import java.io.Serializable;

public interface IMessage<T extends Serializable> extends Serializable {
    IMember getRecipient();
    IMember getSender();

    T getMessage();
    T getTheme();
}
