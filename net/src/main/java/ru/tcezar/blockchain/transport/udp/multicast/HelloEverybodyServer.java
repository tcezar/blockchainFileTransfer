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
    private String socketAddr;
    private int port;
    private IMember member;

    public HelloEverybodyServer(String socketAddr, int port, IMember member) throws IOException {
        this.socketAddr=socketAddr;
        this.port=port;
        this.member = member;
    }

    @Override
    public void run() {
        while (true) {
            try {
                new MulticastPublisher(socketAddr, port).multicast(new Message(null, member.getUID(), "HELLO!", HELLO));
                try {
                    System.out.println("отправка приветствия");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
