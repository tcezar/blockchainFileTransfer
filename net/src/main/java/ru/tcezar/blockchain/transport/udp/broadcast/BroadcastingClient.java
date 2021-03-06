package ru.tcezar.blockchain.transport.udp.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import static ru.tcezar.blockchain.transport.udp.DatagramPacketUtils.getData;

public class BroadcastingClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int expectedServerCount;
    private byte[] buf;

    public BroadcastingClient(String addr, int expectedServerCount) throws Exception {
        this.expectedServerCount = expectedServerCount;
        this.address = InetAddress.getByName(addr);
    }

    public BroadcastingClient(int expectedServerCount) throws Exception {
        new BroadcastingEchoServer("255.255.255.255", expectedServerCount);
    }

    public int discoverServers(String msg) throws IOException {
        initializeSocketForBroadcasting();
        copyMessageOnBuffer(msg);

        // When we want to broadcast not just to local network, call listAllBroadcastAddresses() and execute broadcastPacket for each value.
        broadcastPacket(address);

        return receivePackets();
    }

    List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            broadcastList.addAll(networkInterface.getInterfaceAddresses()
                    .stream()
                    .filter(address -> address.getBroadcast() != null)
                    .map(address -> address.getBroadcast())
                    .collect(Collectors.toList()));
        }
        return broadcastList;
    }

    private void initializeSocketForBroadcasting() throws SocketException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
    }

    private void copyMessageOnBuffer(String msg) {
        buf = msg.getBytes();
    }

    private void broadcastPacket(InetAddress address) throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
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

    public static void main(String[] args) throws Exception {
        BroadcastingClient client = new BroadcastingClient(2);
        client.discoverServers("hello");
    }
}
