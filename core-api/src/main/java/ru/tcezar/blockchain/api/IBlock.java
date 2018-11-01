package ru.tcezar.blockchain.api;

import java.io.Serializable;
import java.util.Date;

/**
 * блок цепочки
 */
public interface IBlock extends Serializable {
    Date getTimestamp();

    Serializable getData();

    String getHash();

    long getIndex();

    String getPreviousHash();
}
