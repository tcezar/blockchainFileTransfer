package ru.tcezar.blockchain.transport.messages;

import ru.tcezar.blockchain.api.IDataMessage;
import ru.tcezar.blockchain.api.IMembers;

import java.io.Serializable;

public interface IMessage extends Serializable{
    IMembers getRecipient();
    IMembers getSender();
    IDataMessage getMessage();
}
