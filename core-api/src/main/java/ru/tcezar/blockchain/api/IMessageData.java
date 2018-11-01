package ru.tcezar.blockchain.api;

import java.io.Serializable;

public interface IMessageData extends Serializable {
    IPublicData getPublic();
    IPrivateData getPrivate();
}
