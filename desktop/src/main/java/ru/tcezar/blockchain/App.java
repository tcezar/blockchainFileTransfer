package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.forms.FileTransferForm;

public class App {
    public static void main(String[] args) {
        Transport transport = new Transport(5700,5800);
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
        FileTransferForm.run();
    }
}