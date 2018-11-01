package ru.tcezar.blockchain.transport.messages;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;

import java.io.Serializable;

import static ru.tcezar.blockchain.transport.protocols.NewMembersMessageCommand.HELLO;

/**
 * Created by Michael on 01.11.2018.
 */
public class Message<T extends Serializable> implements IMessage {
    private IMember recipient;
    private IMember sender;
    private T messageData;

    private Message(IMember recipient, IMember sender, T data) {
        this.recipient = recipient;
        this.sender = sender;
        this.messageData = data;
    }

    /**
     * Создание сообщения HELLO
     *
     * @param recipient Получатель сообщения
     * @param sender    Отправитель сообщения
     * @return Сообщение HELLO
     */
    public static Message createHelloMessage(IMember recipient, IMember sender) {
        return new Message(recipient, sender, HELLO);
    }

    /**
     * Создание ответного сообщения с текущим размером цепочик
     *
     * @param recipient Получатель сообщения
     * @param sender    Отправитель сообщения
     * @return Сообщение HELLO_ANSWER
     */
    public static Message createHelloAnswerMessage(IMember recipient, IMember sender, Integer blockChainSize) {
        return new Message(recipient, sender, blockChainSize);
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
}
