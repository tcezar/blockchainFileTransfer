package ru.tcezar.blockchain.transport.udp.multicast;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.transport.MulticastPublisher;
import ru.tcezar.blockchain.transport.messages.Message;

import java.io.IOException;

import static ru.tcezar.blockchain.transport.protocols.NewMembersMessageCommands.HELLO;

/**
 * Created by Michael on 01.11.2018.
 */
public class HelloEverybodyServer implements Runnable {
    private MulticastPublisher multicastPublisher;
    private IMember member;

    public HelloEverybodyServer(String socketAddr, int port, IMember member) throws IOException {
        multicastPublisher = new MulticastPublisher(socketAddr, port);
        this.member = member;
    }

    @Override
    public void run() {
        while (true) {
            try {
                multicastPublisher.multicast(new Message(null, member, "HELLO!", HELLO));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
