package ru.tcezar.blockchain.transport.api;

import java.io.File;

/**
 * сервер отдающий файл
 */
public interface IServerFileTranfer extends IServer {

    void setFile(File file);

    File getFile();
}
