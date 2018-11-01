package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.udp.multicast.AbstractMulticastReceiver;
import ru.tcezar.blockchain.transport.api.INewMembersListener;
import ru.tcezar.blockchain.transport.messages.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Message newMemberMessage = (Message) message;
        newMemberMessage.getMessage();
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
