package ru.tcezar.blockchain;

import ru.tcezar.blockchain.forms.ApplicationForm;

import java.security.GeneralSecurityException;

public class App {
    public static void main(String[] args) throws GeneralSecurityException {
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
        BlockChain blockChain = new BlockChain();
        IMember member = new Member();
        FileTransferForm.setBlockChain(blockChain);
        FileTransferForm.setMember(member);
        FileTransferForm.run();*/

        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.setVisible(true);

    }
}
