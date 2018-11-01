package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlock;
import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.api.IListenerNewChain;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.tcp.AbstractTCPClient;
import ru.tcezar.blockchain.transport.udp.multicast.AbstractMulticastReceiver;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by Michael on 01.11.2018.
 */
public class NewChainsListener extends AbstractMulticastReceiver implements IListenerNewChain {
    IBlockChain blockChain;

    protected NewChainsListener(MulticastSocket socket, InetAddress group) throws IOException {
        super(socket, group);
    }

    public NewChainsListener(String socketAddr, int socketPort) throws IOException {
        super(socketAddr, socketPort);
    }

    @Override
    protected boolean processMessage(IMessage message) {
        IBlock data = (IBlock) message.getMessage();
        if (blockChain.getLatestBlock().getHash().equals(data.getPreviousHash())
                && blockChain.getLatestBlock().getIndex() + 1 == data.getIndex()) {
            blockChain.addBlock(data);
            return true;
        }
        return false;
    }

    @Override
    protected boolean isStoping(IMessage recivedMessage) {
        return false;
    }

    @Override
    protected void errors(IMessage message) throws UnknownHostException {
        UID sender = message.getSender();
        AbstractTCPClient tcpClient = new AbstractTCPClient(message.getSender().addr, 4055) {
            @Override
            public void run() {
                try {
                    sendData(
                            new Message(
                                    message.getSender(),
                                    message.getRecipient(),
                                    "my size blockChain",
                                    SerializationUtils.serializeObject(blockChain.getSize()))
                    );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void setBlockChain(IBlockChain blockChain) {
        this.blockChain = blockChain;
    }
}

