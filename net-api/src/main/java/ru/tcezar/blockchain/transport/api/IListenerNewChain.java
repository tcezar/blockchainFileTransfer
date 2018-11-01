package ru.tcezar.blockchain.transport.api;

import ru.tcezar.blockchain.api.IBlockChain;

/**
 * слушатель новых сообщений
 */
public interface IListenerNewChain extends IListener {
    void setBlockChain(IBlockChain blockChain);
}
