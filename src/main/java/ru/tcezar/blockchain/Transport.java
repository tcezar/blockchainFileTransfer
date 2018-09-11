package ru.tcezar.blockchain;

import java.io.Serializable;

public class Transport {
    private final int ports[];
    private final int port;

    public Transport(int minPort, int maxPort) {
        int ports[] = new int[maxPort - minPort + 1];
        for (int port : ports) {
            ports[port] = minPort++;
        }
        this.ports = ports;
        this.port = getFreePort();
    }

    private int getFreePort() {
        return ports[0];
    }

    public void broadcast(Serializable o) {

    }
}
