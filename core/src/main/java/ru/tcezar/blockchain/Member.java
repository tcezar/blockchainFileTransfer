package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.transport.api.*;
import ru.tcezar.crypto.api.ICriptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoHelper;

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
    private IServerFileTranfer singleFileTransfer;
    final private String id;
    final private List<IMember> members;

    public Member() {
        ICriptoUtils criptoUtils = CryptoHelper.getUtils();
        keys = criptoUtils.generateKeys();
        blockChain = new BlockChain();
        id = String.valueOf(keys.getPublicKey().getEncoded());
        listeners = Executors.newFixedThreadPool(2);
        singleTasks = Executors.newSingleThreadExecutor();
        members = new ArrayList<>();
    }

    public List<IMember> getMembers() {
        return members;
    }

    private void addListener(IListener listener) {
        listeners.submit(listener);
    }

    public void addListenerNewMembers(IListenerNewMembers listenerNewMembers) {
        listenerNewMembers.setMembers(members);
        addListener(listenerNewMembers);
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
