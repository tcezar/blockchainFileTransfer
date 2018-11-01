package ru.tcezar.blockchain.transport.api;

import java.io.File;
import java.nio.file.Path;

/**
 * сервер отдающий файл
 */
public interface IServerFileTranfer extends IServer {

    void setFile(File file);

    File getFile();
}
