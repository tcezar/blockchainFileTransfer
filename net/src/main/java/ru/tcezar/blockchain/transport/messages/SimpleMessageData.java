package ru.tcezar.blockchain.transport.messages;

import java.io.Serializable;

/**
 * Created by Michael on 01.11.2018.
 */
public class SimpleMessageData<T> implements Serializable {
    private String command;
    private T data;

    public SimpleMessageData(String command, T data) {
        this.data = data;
        this.command = command;
    }

    public T getData() {
        return this.data;
    }

    public String getCommand() {
        return this.command;
    }
}
