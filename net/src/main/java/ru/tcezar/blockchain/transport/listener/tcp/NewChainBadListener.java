package ru.tcezar.blockchain.transport.listener.tcp;

import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.tcp.AbstractTCPServer;

import java.net.UnknownHostException;

public class NewChainBadListener extends AbstractTCPServer {

    public NewChainBadListener(String addr, int port) throws UnknownHostException {
        super(addr, port);
    }

    public NewChainBadListener(int port) throws UnknownHostException {
        super(port);
    }

    @Override
    protected IMessage processMessage(IMessage message) {
        if (! (Boolean) message.getMessage()) {
            new Message(message.getSender(),message.getRecipient(),"end","");
        }
        return new Message(message.getSender(),message.getRecipient(),"fetch two blockChain","");
    }

    @Override
    protected boolean isStopping(IMessage clientSentence) {
        return false;
    }
}
