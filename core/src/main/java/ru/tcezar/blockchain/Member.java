package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.crypto.api.ICriptoUtils;
import ru.tcezar.crypto.api.IPairKeys;
import ru.tcezar.crypto.impl.CryptoHelper;

import java.security.PrivateKey;
import java.security.PublicKey;

public final class Member implements IMember {

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