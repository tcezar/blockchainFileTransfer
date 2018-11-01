package ru.tcezar.blockchain;

import java.io.*;
import java.net.*;
import java.util.*;

public class Transport {
    private final int ports[];
    ServerSocket server;
    private InetAddress localAdress;
    private Map<InetAddress, Set<Integer>> recipient = new HashMap<>();

    public Transport(int minPort, int maxPort) {
        int count = maxPort - minPort;
        int ports[] = new int[count];
        for (int i = 0; i < (count); i++) {
            ports[i] = minPort++;
        }
        this.ports = ports;
        try {
            this.localAdress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            this.localAdress = null;
        }
        for (int port : ports) {
            try {
                server = new ServerSocket(port);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (server == null) {
            throw new RuntimeException("AbstractTCPServer not init");
        }
    }

    @Override
    public String toString() {
        return "server=" + server +
                ", localAdress=" + localAdress;
    }

    public void broadcast(Serializable o) {

    }

    public void scanRecipient() {
        startClient(localAdress.getHostAddress(), ports[0]);
//        int timeout = 1000;
//        for (int i = 1; i < 255; i++) {
//            String host = localAdress.getHostAddress().substring(0,
//                    localAdress.getHostAddress().lastIndexOf(".")) + "." + i;
//            if (host.equals(localAdress.getHostAddress()) & server.getLocalPort() != ports[0]
//                    && startClient(host, ports[0])) {
//                System.out.println(host + " is reachable");
//            }
//
//        }
    }

    private boolean startClient(String host, int port) {
        return false;
    }

    public Map<InetAddress, Set<Integer>> getRecipientList() {
        return recipient;
    }

    public void startServer() {

    }
}
