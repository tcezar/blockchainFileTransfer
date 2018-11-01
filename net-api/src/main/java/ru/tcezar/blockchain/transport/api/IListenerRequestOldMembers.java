package ru.tcezar.blockchain.transport.api;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;

import java.util.List;

/**
 * слушатель ответа старых участников
 */
public interface IListenerRequestOldMembers extends IListener {
    void setBlockChain(IBlockChain blockChain);
    void setMembers(List<IMember> members);
}
