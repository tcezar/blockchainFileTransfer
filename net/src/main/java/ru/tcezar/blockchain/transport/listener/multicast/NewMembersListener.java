package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.udp.multicast.AbstractMulticastReceiver;
import ru.tcezar.blockchain.transport.api.INewMembersListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;

/**
 * Created by Michael on 01.11.2018.
 */
public class NewMembersListener extends AbstractMulticastReceiver implements INewMembersListener {

    protected NewMembersListener(MulticastSocket socket, InetAddress group) throws IOException {
        super(socket, group);
    }

    protected NewMembersListener(String socketAddr, int socketPort) throws IOException {
        super(socketAddr, socketPort);
    }

    @Override
    public void setMembers(List<IMember> members) {

    }

    @Override
    protected boolean processMessage(IMessage messageData) {
        return false;
    }
}
