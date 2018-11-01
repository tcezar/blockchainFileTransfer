package ru.tcezar.blockchain.api;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * участник цепочки (клиента=сервер)
 */
public interface IMember extends Serializable {
    UID getUID();
}
