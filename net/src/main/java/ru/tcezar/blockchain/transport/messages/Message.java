package ru.tcezar.blockchain.transport.messages;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;

import java.io.Serializable;

/**
 * Created by Michael on 01.11.2018.
 */
public class Message<T extends Serializable> implements IMessage {
    private IMember recipient;
    private IMember sender;
    private T messageData;
    private T messageTheme;

    private Message(IMember recipient, IMember sender, T data) {
        this.recipient = recipient;
        this.sender = sender;
        this.messageData = data;
    }

    @Override
    public IMember getRecipient() {
        return this.recipient;
    }

    @Override
    public IMember getSender() {
        return this.sender;
    }

    @Override
    public T getMessage() {
        return this.messageData;
    }

    @Override
    public Serializable getTheme() {
        return this.messageTheme;
    }
}
