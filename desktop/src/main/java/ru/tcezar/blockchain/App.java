package ru.tcezar.blockchain;

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
        BlockChain blockChain = new BlockChain(transport);
        FileTransferForm.setBlockChain(blockChain);
        FileTransferForm.run();
    }
}
