package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.api.INewMembersListener;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.udp.multicast.AbstractMulticastReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Set;

import static ru.tcezar.blockchain.transport.protocols.NewMembersMessageCommands.HELLO;
import static ru.tcezar.blockchain.transport.protocols.NewMembersMessageCommands.HELLO_ANSWER;

/**
 * Created by Michael on 01.11.2018.
 */
public class NewMembersListener extends AbstractMulticastReceiver implements INewMembersListener {

    private Set<UID> addressBook = new HashSet<>();
    private IBlockChain blockChain = null;

    protected NewMembersListener(MulticastSocket socket, InetAddress group) throws IOException {
        super(socket, group);
    }

    public NewMembersListener(String socketAddr, int socketPort) throws IOException {
        super(socketAddr, socketPort);
    }

    @Override
    public void setBlockChain(IBlockChain blockChain) {
        this.blockChain = blockChain;
    }

    @Override
    public void setMembers(Set<UID> members) {
        this.addressBook = members;
    }

    @Override
    protected boolean processMessage(IMessage message) {
//        System.out.println("получено сообщение:" + message);
        Message<String> newMemberMessage = (Message) message;
        String data = newMemberMessage.getMessage();
        if (HELLO_ANSWER.equals(data)) {
            this.addressBook.add(newMemberMessage.getSender());
            // TODO: 01.11.2018 Сверка цепочек
            if (newChainIsMoreActual(0)) {
                // TODO: 01.11.2018 Запросить актуальную цепочку
            } else {
                // TODO: 01.11.2018 Отправить свои данные
            }
            return true;
        } else if (HELLO.equals(newMemberMessage.getMessage())) {
            System.out.println(String.format("Получен HELLO! %s", newMemberMessage.getSender()));
            this.addressBook.add(newMemberMessage.getSender());
            return true;
        }
        return false;
    }


    private boolean newChainIsMoreActual(Integer size) {
        return false;
    }

    @Override
    protected boolean isStoping(IMessage recivedMessage) {
        return false;
    }

    @Override
    protected void errors(IMessage recivedMessage) {
    }
}
