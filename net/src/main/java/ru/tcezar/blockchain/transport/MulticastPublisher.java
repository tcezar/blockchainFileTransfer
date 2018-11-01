package ru.tcezar.blockchain.transport;

import ru.tcezar.blockchain.api.IMessageData;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private int port;

    private byte[] buf;

    protected MulticastPublisher(MulticastSocket socket, InetAddress group, int port) throws IOException {
        init(socket, group, port);
    }

    public MulticastPublisher(String socketAddr, int socketPort) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(socketAddr);
        init(datagramSocket, group, socketPort);
    }

    private void init(DatagramSocket socket, InetAddress group, int port) {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    public void multicast(IMessageData multicastMessage) throws IOException {
        buf = SerializationUtils.serializeObject(multicastMessage);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
        socket.send(packet);
        socket.close();
    }
}
