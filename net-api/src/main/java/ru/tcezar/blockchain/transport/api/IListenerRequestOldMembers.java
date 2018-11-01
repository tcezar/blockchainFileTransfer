package ru.tcezar.blockchain.transport.api;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.UID;

import java.util.List;
import java.util.Set;

/**
 * слушатель ответа старых участников
 */
public interface IListenerRequestOldMembers extends IListener {
    void setBlockChain(IBlockChain blockChain);
    void setMembers(Set<UID> members);
}
