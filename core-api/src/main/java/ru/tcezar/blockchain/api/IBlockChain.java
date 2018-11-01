package ru.tcezar.blockchain.api;

import java.util.List;

/**
 * цепочка блоков
 */
public interface IBlockChain {
    List<IBlock> getBlockchain();

    void generateNextBlock(IMessage blockData);

    void addBlock(IBlock newBlock);

    IBlock getLatestBlock();

    int getSize();
}
