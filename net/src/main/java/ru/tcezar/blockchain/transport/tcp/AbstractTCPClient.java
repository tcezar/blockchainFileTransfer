package ru.tcezar.blockchain.transport.tcp;

import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.api.IClient;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class AbstractTCPClient extends AbstractTCP implements IClient {
    final InetAddress address;
    final int port;
    final int size = 1024 * 1024;
    protected UID recipent;
    protected UID sender;

    public AbstractTCPClient(String addr, int port, UID recipent, UID sender) throws UnknownHostException {
        this.address = InetAddress.getByName(addr);
        this.port = port;
        this.recipent = recipent;
        this.sender = sender;
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

    protected Socket createSocket() throws IOException {
        return new Socket(this.address, this.port);
    }

    public IMessage sendMessageWOClosing(Socket openedSocket, IMessage message)
            throws IOException, ClassNotFoundException {
        System.out.println("clientSocket = " + openedSocket.toString());
        // отправляем сообщение
        System.out.println("sentence: " + message);
        DataOutputStream outToServer = new DataOutputStream(openedSocket.getOutputStream());
        outToServer.writeBytes(SerializationUtils.serializeObject(message) + "\n");
        // читаем ответ
        byte[] bytes = readAllBytesFromInputStream(openedSocket.getInputStream());
        return SerializationUtils.getData(bytes);
    }

    public IMessage sendData(IMessage message) throws ClassNotFoundException {
        IMessage modifiedSentence = null;
        try {
            Socket clientSocket = createSocket();
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
