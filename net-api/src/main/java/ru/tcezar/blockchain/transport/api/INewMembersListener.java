package ru.tcezar.blockchain.transport.api;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;

import java.util.List;

/**
 * слушатель новых участников
 */
public interface INewMembersListener extends IListener {
    void setBlockChain(IBlockChain blockChain);
    void setMembers(List<IMember> members);
}
