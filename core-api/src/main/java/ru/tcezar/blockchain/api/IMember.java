package ru.tcezar.blockchain.api;

import ru.tcezar.blockchain.transport.api.IListenerNewChain;
import ru.tcezar.blockchain.transport.api.IListenerRequestOldMembers;
import ru.tcezar.blockchain.transport.api.INewMembersListener;

import java.io.Serializable;
import java.util.Set;

/**
 * участник цепочки (клиента=сервер)
 */
public interface IMember extends Serializable {
    Set<UID> getMembers();

    IBlockChain getBlockChain();

    void addListenerNewMembers(INewMembersListener listenerNewMembers);

    void addListenerNewChain(IListenerNewChain listenerNewChain);

    void addListenerRequestOldMembers(IListenerRequestOldMembers iListenerRequestOldMembers);

    UID getUID();


}
