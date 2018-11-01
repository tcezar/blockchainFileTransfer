package ru.tcezar.blockchain.transport.api;

import java.nio.file.Path;

/**
 * сервер отдающий файл
 */
public interface IServerFileTranfer extends IServer {
    void setFile(Path file);
    Path getFile();
}
