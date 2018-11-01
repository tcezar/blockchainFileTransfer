package ru.tcezar.blockchain.api;

import java.io.Serializable;

/**
 * блок цепочки
 */
public interface IBlock extends Serializable {
    Serializable getData();

    String getHash();

    long getIndex();

    String getPreviousHash();
}
