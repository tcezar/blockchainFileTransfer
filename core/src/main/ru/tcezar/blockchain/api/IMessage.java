package ru.tcezar.blockchain.api;

import java.io.Serializable;

public interface IMessage extends Serializable{
    IMembers getRecipient();
    IMembers getSender();
    IDataMessage getMessage();
}
