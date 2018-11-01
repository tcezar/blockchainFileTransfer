package ru.tcezar.blockchain.api;

import java.io.Serializable;

/**
 * участник цепочки (клиента=сервер)
 */
public interface IMember extends Serializable {
    String getId();
}
