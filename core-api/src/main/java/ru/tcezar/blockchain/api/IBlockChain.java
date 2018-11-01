package ru.tcezar.blockchain.api;

import java.io.Serializable;
import java.util.List;

/**
 * цепочка блоков
 */
public interface IBlockChain extends Serializable {
    List<IBlock> getBlockchain();

    boolean generateNextBlock(IMessage blockData);

    boolean addBlock(IBlock newBlock);

    IBlock getLatestBlock();

    int getSize();
}
