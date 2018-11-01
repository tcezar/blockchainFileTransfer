package ru.tcezar.blockchain.transport;

import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.api.IClient;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractTCPClient extends AbstractTCP implements IClient {
    final InetAddress address;
    final int port;
    final int size = 1024 * 1024;

    public AbstractTCPClient(String addr, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(addr);
        this.port = port;
    }
//
//    public static void main(String argv[]) throws Exception {
//        String modifiedSentence;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//        String line;
//        while ((line = inFromUser.readLine())!=null) {
//            AbstractTCPClient abstractTcpClient = new AbstractTCPClient("localhost", 6789);
//            modifiedSentence = abstractTcpClient.sendData(line);
//        }
//        AbstractTCPClient abstractTcpClient = new AbstractTCPClient("localhost", 6789);
//        modifiedSentence = abstractTcpClient.sendData("quit");
//    }

    public IMessage sendData(IMessage message) throws ClassNotFoundException {
        IMessage  modifiedSentence = null;
        try {
            Socket clientSocket = new Socket(this.address, this.port);
            System.out.println("clientSocket = " + clientSocket.toString());
            // отправляем сообщение
            System.out.println("sentence: " + message);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(SerializationUtils.serializeObject(message) + "\n");
            // читаем ответ
            byte[] bytes = readAllBytesFromInputStream(clientSocket.getInputStream());
            modifiedSentence = SerializationUtils.getData(bytes);
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modifiedSentence;
    }
}
