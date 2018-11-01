package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessageData;
import ru.tcezar.blockchain.transport.api.AbstractMulticastReceiver;
import ru.tcezar.blockchain.transport.api.IListenerNewChain;
import ru.tcezar.blockchain.transport.api.INewMembersListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;

/**
 * Created by Michael on 01.11.2018.
 */
public class NewChainsListener extends AbstractMulticastReceiver implements IListenerNewChain {
    IBlockChain blockChain;

    protected NewChainsListener(MulticastSocket socket, InetAddress group) throws IOException {
        super(socket, group);
    }

    protected NewChainsListener(String socketAddr, int socketPort) throws IOException {
        super(socketAddr, socketPort);
    }

    @Override
    protected boolean processMessage(IMessageData messageData) {
        return false;
    }

    @Override
    public void setBlockChain(IBlockChain blockChain) {
        this.blockChain = blockChain;
    }
}

