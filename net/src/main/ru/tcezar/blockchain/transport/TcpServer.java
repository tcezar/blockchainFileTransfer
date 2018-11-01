package ru.tcezar.blockchain.transport;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpServer extends Thread {
    private final int port;
    private final InetAddress address;
    private boolean running;
    ServerSocket serverSocket;

    public TcpServer(String addr, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(addr);
        this.port = port;

    }

    public TcpServer(int port) throws UnknownHostException {
        this.address = null;
        this.port = port;

    }

    public static void main(String argv[]) throws Exception {
        TcpServer server = new TcpServer("localhost", 6789);
//        TcpServer server = new TcpServer(6789);
        server.start();
    }

    public void run() {
        running = true;
        try {
            if (address != null) {
                serverSocket = new ServerSocket(this.port, 0, this.address);
            } else {
                serverSocket = new ServerSocket(this.port);
            }
            System.out.println("serverSocket = " + serverSocket.toString());
            String clientSentence;
            String capitalizedSentence;
            while (running) {
                try {
                    Socket connectionSocket = serverSocket.accept();
                    BufferedReader inFromClient =
                            new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    clientSentence = inFromClient.readLine();
                    System.out.println("Received: " + clientSentence);
                    if ("quit".equalsIgnoreCase(clientSentence)) {
                        running = false;
                        continue;
                    }
                    capitalizedSentence = clientSentence.toUpperCase() + "\n";
                    outToClient.writeBytes(capitalizedSentence);
                } catch (IOException e) {
                    e.printStackTrace();
                    running = false;
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
