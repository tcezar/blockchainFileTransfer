package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.api.AbstractMulticastReceiver;
import ru.tcezar.blockchain.transport.api.INewMembersListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 01.11.2018.
 */
public class NewMembersListener extends AbstractMulticastReceiver implements INewMembersListener {

    private List<IMember> addressBook = new ArrayList<>();
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
    public void setMembers(List<IMember> members) {
        this.addressBook = members;
    }

    @Override
    protected boolean processMessage(IMessage messageData) {
        return false;
    }
}
