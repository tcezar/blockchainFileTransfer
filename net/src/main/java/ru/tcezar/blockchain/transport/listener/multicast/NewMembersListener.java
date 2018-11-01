package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.api.INewMembersListener;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.messages.SimpleMessageData;
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

    protected NewMembersListener(String socketAddr, int socketPort) throws IOException {
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
        Message<SimpleMessageData<IMember>> newMemberMessage = (Message) message;
        SimpleMessageData<IMember> data = newMemberMessage.getMessage();
        if (HELLO_ANSWER.equals(data.getCommand())) {
            IMember newMember = data.getData();
            this.addressBook.add(newMember.getUID());
            // TODO: 01.11.2018 Сверка цепочек
            if (newChainIsMoreActual(0)) {
                // TODO: 01.11.2018 Запросить актуальную цепочку
            } else {
                // TODO: 01.11.2018 Отправить свои данные
            }
            return true;
        } else if (HELLO.equals(data.getCommand())) {

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
