package ru.tcezar.blockchain.transport.messages;

import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;

import java.io.Serializable;

/**
 * Created by Michael on 01.11.2018.
 */
public class Message<T extends Serializable> implements IMessage {
    private UID recipient;
    private UID sender;
    private T messageData;
    private String messageTheme;

    public Message(UID recipient, UID sender, String messageTheme, T data) {
        this.recipient = recipient;
        this.sender = sender;
        this.messageTheme = messageTheme;
        this.messageData = data;
    }

    @Override
    public UID getRecipient() {
        return this.recipient;
    }

    @Override
    public UID getSender() {
        return this.sender;
    }

    @Override
    public T getMessage() {
        return this.messageData;
    }

    @Override
    public String getTheme() {
        return this.messageTheme;
    }

    @Override
    public String toString() {
        String recipient = getRecipient() == null ? "ALL USERS" : getRecipient().toString();
        return String.format("%s -> %s. %s:%s", getSender().toString(), recipient, getTheme(), getMessage());
    }
}
