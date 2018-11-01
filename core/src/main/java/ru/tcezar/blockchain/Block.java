package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlock;
import ru.tcezar.blockchain.api.IMessage;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class Block implements IBlock {
    static final long serialVersionUID = 7187392471159151072L;
    final private long index;
    final private String previousHash;
    final private Date timestamp;
    final private IMessage data;
    final private String hash;

    public Block(long index, String previousHash, IMessage data) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = new Date();
        this.data = data;
        this.hash = calculateHash(this);
    }

    public static String calculateHash(Block block) {
        String text = String.valueOf(block.index) + String.valueOf(block.previousHash) + String.valueOf(block.timestamp) + String.valueOf(block.data);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            throw new BlockchainException("failed to calculate hash",e);
        }
    }

    @Override
    public IMessage getData() {
        return data;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public long getIndex() {
        return index;
    }

    @Override
    public String getPreviousHash() {
        return previousHash;
    }

    @Override
    public int hashCode() {
        return hash.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return hash;
    }
}
