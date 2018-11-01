package ru.tcezar.blockchain;

import io.netty.util.internal.ConcurrentSet;
import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.api.*;
import ru.tcezar.config.ConfigKeeper;
import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Set;
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
    final private UID id;
    final private Set<UID> members;
//    final private ConcurrentHashMap<>

    public Member() throws GeneralSecurityException, IOException {
        ICryptoUtils cryptoUtils = new CryptoUtils();
        if (ConfigKeeper.checkKeysFilepathConsist()) {
            keys = cryptoUtils.getKeysFromFiles(
                    Paths.get(String.valueOf(ConfigKeeper.getConfig(ConfigKeeper.publicKeyCode))),
                    Paths.get(String.valueOf(ConfigKeeper.getConfig(ConfigKeeper.privateKeyCode))));
        } else {
            keys = cryptoUtils.generateKeys();
            ConfigKeeper.setConfig(ConfigKeeper.publicKeyCode, ConfigKeeper.configDir + File.separator +
                    ConfigKeeper.publicKeyCode + keys.getPublicKey().hashCode());
            ConfigKeeper.setConfig(ConfigKeeper.privateKeyCode, ConfigKeeper.configDir + File.separator +
                    ConfigKeeper.privateKeyCode + keys.getPrivateKey().hashCode());
            cryptoUtils.saveKeysToFiles(keys,
                    Paths.get(String.valueOf(ConfigKeeper.getConfig(ConfigKeeper.publicKeyCode))),
                    Paths.get(String.valueOf(ConfigKeeper.getConfig(ConfigKeeper.privateKeyCode))));
            ConfigKeeper.saveConfigsToFile();
        }
        blockChain = new BlockChain();
        String interfaceIP = System.getProperty("interfaceIP");
        if (interfaceIP == null) {
            interfaceIP = InetAddress.getLocalHost().getHostAddress();
        }
        id = new UID(keys.getPublicKey().hashCode(),
                interfaceIP != null ? interfaceIP : "127.0.0.1"
        );
        listeners = Executors.newFixedThreadPool(3);
        singleTasks = Executors.newSingleThreadExecutor();
        singleFileTransfers = Executors.newSingleThreadExecutor();
        members = new ConcurrentSet<>();
    }

    public void stopFiletransfer() {
        singleFileTransfers.shutdown();
    }

    public void startFileTransfer(IServerFileTranfer singleFileTransfer) {
        singleFileTransfers.submit(singleFileTransfer);
    }

    @Override
    public Set<UID> getMembers() {
        return members;
    }

    @Override
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
    public UID getUID() {
        return id;
    }

    @Override
    public String toString() {
        return getUID().toString();
    }

    @Override
    public int hashCode() {
        return getUID().id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && IMember.class.isAssignableFrom(obj.getClass())) {
            return getUID().equals(((IMember) obj).getUID());
        }
        return false;
    }

}
