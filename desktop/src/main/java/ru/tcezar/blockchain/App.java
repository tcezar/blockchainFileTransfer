package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.forms.ApplicationForm;
import ru.tcezar.blockchain.forms.FileTransferForm;
import ru.tcezar.blockchain.transport.listener.multicast.NewMembersListener;
import ru.tcezar.blockchain.transport.udp.multicast.HelloEverybodyServer;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {
    public static void main(String[] args) throws GeneralSecurityException, IOException {

        /*Transport transport = new Transport(5700,5800);
        Thread threadTransport = new Thread(new Runnable() {
            @Override
            public void run() {
                transport.startServer();
            }
        });
        threadTransport.setDaemon(true);
        threadTransport.start();
        FileTransferForm.setTransport(transport);
        BlockChain blockChain = new BlockChain();*/
        Member member = new Member();
        /*FileTransferForm.setBlockChain(blockChain);
        FileTransferForm.setMember(member);
        FileTransferForm.run();*/
        member.addListenerNewMembers(new NewMembersListener("230.0.0.0",20000));
        HelloEverybodyServer helloEverybodyServer = new HelloEverybodyServer(
                "230.0.0.0", 20000, member);
        helloEverybodyServer.run();
        FileTransferForm.run();

        ApplicationForm applicationForm = new ApplicationForm(member);
        applicationForm.setVisible(true);

    }
}
