package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.IBlockChain;

import java.util.ArrayList;
import java.util.List;

public class BlockChain implements IBlockChain {
    static final long serialVersionUID = 7187392471159151072L;
    private List<Block> blockchain;

    public BlockChain() {
        this.blockchain = new ArrayList<>();
        blockchain.add(getGenesisBlock());
    }

    public List<Block> getBlockchain() {
        return new ArrayList<>(blockchain);
    }

    private Block getGenesisBlock() {
        return new Block(0, "0", "my genesis block!!");
    }

    public void generateNextBlock(Object blockData) {

        Block lastBlock = getLatestBlock();
        blockchain.add(
                new Block(lastBlock.getIndex() + 1, lastBlock.getHash(), blockData)
        );
    }

    public void addBlock(Block newBlock) {
        if (isValidNewBlock(newBlock, getLatestBlock())) {
            blockchain.add(newBlock);
        }
    }

    private Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public static boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (previousBlock.getIndex() + 1 != newBlock.getIndex()) {
            System.out.println("invalid index");
            return false;
        } else if (!previousBlock.getHash().equals(newBlock.getPreviousHash())) {
            System.out.println("invalid previoushash");
            return false;
        } else if (!Block.calculateHash(newBlock).equals(newBlock.getHash())) {
            System.out.println("invalid hash: " + Block.calculateHash(newBlock) + " " + newBlock.getHash());
            return false;
        }
        return true;
    }

    public boolean isValidChain(List<Block> blockchainToValidate) {
        if (!blockchainToValidate.get(0).equals(getGenesisBlock())) {
            return false;
        }
        List<Block> tempBlocks = new ArrayList<>();
        tempBlocks.add(blockchainToValidate.get(0));
        for (int i = 1; i < blockchainToValidate.size(); i++) {
            if (isValidNewBlock(blockchainToValidate.get(i), blockchainToValidate.get(i - 1))) {
                tempBlocks.add(blockchainToValidate.get(i));
            } else {
                return false;
            }
        }
        return true;
    }

    public void replaceChain(List<Block> newBlocks) {
        if (isValidChain(newBlocks) && newBlocks.size() > blockchain.size()) {
            System.out.println("Received blockchain is valid. Replacing current blockchain with received blockchain");
            blockchain = new ArrayList<>(newBlocks);
            //transport.broadcast(responseLatestMsg());//send last msg
        } else {
            System.out.println("Received blockchain invalid");
        }
    }

    @Override
    public int getSize() {
        return this.blockchain.size();
    }

    private Block responseLatestMsg() {
        return null;
    }
}
