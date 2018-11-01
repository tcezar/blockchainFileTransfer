package ru.tcezar.blockchain.api;

import java.io.Serializable;

public interface IDataMessage extends Serializable {
    IPublicData getPublic();
    IPrivateData getPrivate();
}
