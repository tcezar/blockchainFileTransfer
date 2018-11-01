package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlock;
import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.api.IListenerNewChain;
import ru.tcezar.blockchain.transport.udp.multicast.AbstractMulticastReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

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
    protected boolean processMessage(IMessage message) {
        IMember sender = message.getSender();
        IBlock data = (IBlock) message.getMessage().getPublic();
        return false;
    }

    @Override
    protected boolean isStoping(IMessage recivedMessage) {
        return false;
    }

    @Override
    protected void errors(IMessage recivedMessage) {

    }

    @Override
    public void setBlockChain(IBlockChain blockChain) {
        this.blockChain = blockChain;
    }
}

