package ru.tcezar.blockchain.transport.tcp;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.api.IServer;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class AbstractTCPServer extends AbstractTCP implements IServer {
    private final int port;
    private final InetAddress address;
    private boolean running;
    ServerSocket serverSocket;
    IMember member;

    public AbstractTCPServer(String addr, int port, IMember member) throws UnknownHostException {
        this.address = InetAddress.getByName(addr);
        this.port = port;
        this.member = member;
    }

    public AbstractTCPServer(int port, IMember member) throws UnknownHostException {
        this.address = null;
        this.port = port;
        this.member = member;
    }

//    public static void main(String argv[]) throws Exception {
//        AbstractTCPServer server = new AbstractTCPServer("localhost", 6789);
//        AbstractTCPServer server = new AbstractTCPServer(6789);
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
                    byte[] bytes = readAllBytesFromInputStream(connectionSocket.getInputStream());
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
