package ru.tcezar.blockchain;

import ru.tcezar.blockchain.forms.ApplicationForm;
import ru.tcezar.blockchain.transport.listener.multicast.NewChainsListener;
import ru.tcezar.blockchain.transport.listener.multicast.NewMembersListener;
import ru.tcezar.blockchain.transport.udp.multicast.HelloEverybodyServer;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {
    public static void main(String[] args) throws GeneralSecurityException, IOException {

        Member member = new Member();
        member.addListenerNewMembers(new NewMembersListener("230.0.0.0", 2001, member));
        member.addListenerNewChain(new NewChainsListener("230.0.0.0",2002, member));
//        member.addListenerRequestOldMembers(new n("230.0.0.0",20002));
        HelloEverybodyServer helloEverybodyServer = new HelloEverybodyServer(
                "230.0.0.0", 2001, member);
        Thread thread = new Thread(helloEverybodyServer);
        thread.setDaemon(true);
        thread.start();

        ApplicationForm applicationForm = new ApplicationForm(member);
        applicationForm.setVisible(true);

    }
}
