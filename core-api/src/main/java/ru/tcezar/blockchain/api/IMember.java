package ru.tcezar.blockchain.api;

import java.io.Serializable;
import java.util.Map;

/**
 * участник цепочки (клиента=сервер)
 */
public interface IMember extends Serializable {
    Map<UID, Integer> getMembers();

    IBlockChain getBlockChain();

    UID getUID();


}
