package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.transport.api.*;
import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoUtils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * участник цепочки
 */
public final class Member implements IMember {
    static final long serialVersionUID = 7187392471159151072L;
    /**
     * ключи
     */
    final private IPairKeys keys;
    /**
     * цепочка
     */
    final private IBlockChain blockChain;
    /**
     * постоянные слушатели
     */
    final private ExecutorService listeners;
    final private ExecutorService singleTasks;
    final private ExecutorService singleFileTransfers;
    final private String id;
    final private List<IMember> members;

    public Member() throws NoSuchAlgorithmException {
        ICryptoUtils criptoUtils = new CryptoUtils();
        keys = criptoUtils.generateKeys();
        blockChain = new BlockChain();
        id = String.valueOf(keys.getPublicKey().getEncoded());
        listeners = Executors.newFixedThreadPool(3);
        singleTasks = Executors.newSingleThreadExecutor();
        singleFileTransfers = Executors.newSingleThreadExecutor();
        members = new ArrayList<>();
    }

    public void stopFiletransfer() {
        singleFileTransfers.shutdown();
    }

    public void startFileTransfer(IServerFileTranfer singleFileTransfer) {
        singleFileTransfers.submit(singleFileTransfer);
    }

    public List<IMember> getMembers() {
        return members;
    }

    public IBlockChain getBlockChain() {
        return blockChain;
    }

    private void addListener(IListener listener) {
        listeners.submit(listener);
    }

    public void addListenerNewMembers(INewMembersListener listenerNewMembers) {
        listenerNewMembers.setMembers(members);
        addListener(listenerNewMembers);
    }
    public void addListenerRequestOldMembers(IListenerRequestOldMembers iListenerRequestOldMembers) {
        iListenerRequestOldMembers.setMembers(members);
        iListenerRequestOldMembers.setBlockChain(blockChain);
        addListener(iListenerRequestOldMembers);
    }

    public void addListenerNewChain(IListenerNewChain listenerNewChain) {
        listenerNewChain.setBlockChain(blockChain);
        addListener(listenerNewChain);
    }

    public void startSingleTask(IServer server) {
        singleTasks.submit(server);
    }

    @Override
    public String getId() {
        return id;
    }
}
