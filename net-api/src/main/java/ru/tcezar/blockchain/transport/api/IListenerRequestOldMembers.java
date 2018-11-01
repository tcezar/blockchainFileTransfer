package ru.tcezar.blockchain.transport.api;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.UID;

import java.util.Map;

/**
 * слушатель ответа старых участников
 */
public interface IListenerRequestOldMembers extends IListener {
    void setBlockChain(IBlockChain blockChain);

    void setMembers(Map<UID, Integer> members);
}
