package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlock;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class Block implements IBlock {
    final private long index;
    final private String previousHash;
    final private Date timestamp;
    final private Object data;
    final private String hash;

    public Block(long index, String previousHash, Object data) {
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

    public String getHash() {
        return hash;
    }

    public long getIndex() {
        return index;
    }

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
