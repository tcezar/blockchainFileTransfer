package ru.tcezar.blockchain.transport.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import static ru.tcezar.blockchain.transport.udp.DatagramPacketUtils.getData;

public class MulticastingClient {
    private DatagramSocket socket;
    private InetAddress group;
    private int expectedServerCount;
    private byte[] buf;

    public MulticastingClient(int expectedServerCount) throws Exception {
        this.expectedServerCount = expectedServerCount;
        this.socket = new DatagramSocket();
        this.group = InetAddress.getByName("230.0.0.0");
    }


    public int discoverServers(String msg) throws IOException {
        copyMessageOnBuffer(msg);
        multicastPacket();

        return receivePackets();
    }

    private void copyMessageOnBuffer(String msg) {
        buf = msg.getBytes();
    }

    private void multicastPacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        System.out.println(new Date() + ". send = " + getData(packet));
    }

    private int receivePackets() throws IOException {
        int serversDiscovered = 0;
        while (serversDiscovered != expectedServerCount) {
            receivePacket();
            serversDiscovered++;
        }
        return serversDiscovered;
    }

    private void receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        socket.receive(packet);
        System.out.println(new Date() + ". received = " + getData(packet) + " from " + packet.getSocketAddress());
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            MulticastingClient multicastingClient = new MulticastingClient(2);
            int count = multicastingClient.discoverServers("hello");
            System.out.println("send count = " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
