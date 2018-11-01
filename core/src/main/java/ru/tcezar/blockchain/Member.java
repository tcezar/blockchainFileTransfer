package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.transport.api.IListener;
import ru.tcezar.blockchain.transport.api.IServer;
import ru.tcezar.blockchain.transport.api.IServerFileTranfer;
import ru.tcezar.crypto.api.ICriptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Member implements IMember {
    static final long serialVersionUID = 7187392471159151072L;
    final private IPairKeys keys;
    final private IBlockChain blockChain;
    final private ExecutorService listeners;
    final private ExecutorService singleTasks;
    private IServerFileTranfer singleFileTransfer;
    final private String id;

    public Member() {
        ICriptoUtils criptoUtils = CryptoHelper.getUtils();
        keys = criptoUtils.generateKeys();
        blockChain = new BlockChain();
        id = String.valueOf(keys.getPublicKey().getEncoded());
        listeners = Executors.newFixedThreadPool(2);
        singleTasks = Executors.newSingleThreadExecutor();
    }

    public void addListener(IListener listener) {
        listeners.submit(listener);
    }

    public void startSingleTask(IServer server){
        singleTasks.submit(server);
    }

    @Override
    public String getId() {
        return id;
    }
}
