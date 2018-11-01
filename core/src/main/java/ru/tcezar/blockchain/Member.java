package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.crypto.api.ICriptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoHelper;

public final class Member implements IMember {
    static final long serialVersionUID = 7187392471159151072L;
    final private IPairKeys keys;
    final private IBlockChain blockChain;
    final private String id;

    public Member() {
        ICriptoUtils criptoUtils = CryptoHelper.getUtils();
        keys = criptoUtils.generateKeys();
        blockChain = new BlockChain();
        id = String.valueOf(keys.getPublicKey().getEncoded());
    }

    @Override
    public String getId() {
        return id;
    }
}
