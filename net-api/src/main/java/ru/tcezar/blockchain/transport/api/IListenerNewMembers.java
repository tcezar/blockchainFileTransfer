package ru.tcezar.blockchain.transport.api;

import ru.tcezar.blockchain.api.IMember;

import java.util.List;

/**
 * слушатель новых участников
 */
public interface IListenerNewMembers extends IListener {
    void setMembers(List<IMember> members);
}
