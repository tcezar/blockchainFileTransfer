package ru.tcezar.blockchain.api;

import java.io.Serializable;

public interface IMessage extends Serializable{
    IMember getRecipient();
    IMember getSender();
    IMessageData getMessage();
}
