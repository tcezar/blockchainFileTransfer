package ru.tcezar.blockchain;

import ru.tcezar.blockchain.api.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlockChain implements IBlockChain {
    static final long serialVersionUID = 7187392471159151072L;
    private List<IBlock> blockchain;

    public BlockChain() {
        this.blockchain = new ArrayList<>();
        blockchain.add(getGenesisBlock());
    }

    @Override
    public List<IBlock> getBlockchain() {
        return new ArrayList<>(blockchain);
    }

    private IBlock getGenesisBlock() {
        return new Block(0, "0", new IMessage() {
            @Override
            public IMember getRecipient() {
                return null;
            }

            @Override
            public IMember getSender() {
                return null;
            }

            @Override
            public Serializable getMessage() {
                return null;
            }

            @Override
            public Serializable getTheme() {
                return null;
            }

            @Override
            public String toString() {
                return "my genesis block!!";
            }
        });
    }

    @Override
    public void generateNextBlock(IMessage blockData) {

        IBlock lastBlock = getLatestBlock();
        blockchain.add(
                new Block(lastBlock.getIndex() + 1, lastBlock.getHash(), blockData)
        );
    }

    @Override
    public void addBlock(IBlock newBlock) {
        if (isValidNewBlock(newBlock, getLatestBlock())) {
            blockchain.add(newBlock);
        }
    }

    @Override
    public IBlock getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public static boolean isValidNewBlock(IBlock newBlock, IBlock previousBlock) {
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
