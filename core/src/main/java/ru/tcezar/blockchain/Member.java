package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.transport.api.*;
import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
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
    final private InetAddress localAdress;

    public Member() throws GeneralSecurityException, UnknownHostException {
        ICryptoUtils cryptoUtils = new CryptoUtils();
        keys = cryptoUtils.generateKeys();
        blockChain = new BlockChain();
        id = String.valueOf(keys.getPublicKey().getEncoded());
        listeners = Executors.newFixedThreadPool(3);
        singleTasks = Executors.newSingleThreadExecutor();
        singleFileTransfers = Executors.newSingleThreadExecutor();
        members = new ArrayList<>();
        localAdress = InetAddress.getLocalHost();
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
        listenerNewMembers.setBlockChain(blockChain);
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

    @Override
    public String toString() {
        return "id=" + getId() +
                ", localAdress=" + getLocalAdress();
    }

    @Override
    public InetAddress getLocalAdress() {
        return localAdress;
    }
}
