package ru.tcezar.blockchain.api;

import java.io.Serializable;
import java.util.Set;

/**
 * участник цепочки (клиента=сервер)
 */
public interface IMember extends Serializable {
    Set<UID> getMembers();

    IBlockChain getBlockChain();

    UID getUID();


}
