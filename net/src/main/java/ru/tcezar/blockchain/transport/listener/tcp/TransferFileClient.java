package ru.tcezar.blockchain.transport.listener.tcp;

import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.tcp.AbstractTCPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Michael on 02.11.2018.
 */
public class TransferFileClient extends AbstractTCPClient {

    private String fileName;

    public TransferFileClient(String addr, int port, UID recipent, UID sender, String fileName)
            throws UnknownHostException {
        super(addr, port, recipent, sender);
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            Socket socket = createSocket();
            IMessage fileMessage = sendMessageWOClosing(
                    socket, new Message(recipent, sender, "GET FILE", fileName));
            sendMessageWOClosing(socket, new Message(recipent, sender, "ACCEPT GET FILE", fileName));
            socket.close();
            File file = new File(fileName);
            while (file.exists()) {
                fileName = fileName.concat("1");
                file = new File(fileName);
            }
            if (!file.createNewFile()) {
                System.out.println("Не удалось создать файл");
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write((byte[]) fileMessage.getMessage());
            fileOutputStream.close();
            System.out.println("Создан файл ".concat(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
