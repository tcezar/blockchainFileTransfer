package ru.tcezar.blockchain.transport;

import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.api.IServer;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class AbstractTcpServer implements IServer {
    private final int port;
    private final InetAddress address;
    private boolean running;
    ServerSocket serverSocket;

    public AbstractTcpServer(String addr, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(addr);
        this.port = port;

    }

    public AbstractTcpServer(int port) throws UnknownHostException {
        this.address = null;
        this.port = port;

    }

//    public static void main(String argv[]) throws Exception {
//        AbstractTcpServer server = new AbstractTcpServer("localhost", 6789);
//        AbstractTcpServer server = new AbstractTcpServer(6789);
//        server.run();
//    }

    public void run() {
        running = true;
        try {
            if (address != null) {
                serverSocket = new ServerSocket(this.port, 0, this.address);
            } else {
                serverSocket = new ServerSocket(this.port);
            }
            System.out.println("serverSocket = " + serverSocket.toString());
            IMessage clientSentence;
            IMessage capitalizedSentence;
            while (running) {
                try {
                    Socket connectionSocket = serverSocket.accept();
                    byte[] bytes = connectionSocket.getInputStream().readAllBytes();
                    clientSentence = SerializationUtils.getData(bytes);
//                    BufferedReader inFromClient =
//                            new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//                    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    System.out.println("Received: " + clientSentence);
                    if (isStopping(clientSentence)) {
                        running = false;
                        continue;
                    }
//                    if ("quit".equalsIgnoreCase(clientSentence)) {
//                        running = false;
//                        continue;
//                    }
                    capitalizedSentence = processMessage(clientSentence);
//                    capitalizedSentence = clientSentence.toUpperCase() + "\n";
                    connectionSocket.getOutputStream().write(SerializationUtils.serializeObject(capitalizedSentence));

//                    outToClient.writeBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                    running = false;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract IMessage processMessage(IMessage clientSentence);

    protected abstract boolean isStopping(IMessage clientSentence);
}
