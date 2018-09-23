package ru.tcezar.blockchain.transport.udp;

import java.io.IOException;
import java.net.*;
import java.util.Date;

import static ru.tcezar.blockchain.transport.udp.DatagramPacketUtils.getData;

public class EchoClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public EchoClient(String addr) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(addr);
    }

    public String sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        String data = getData(packet);
        System.out.println(new Date() + ". send = " + data);
        packet = new DatagramPacket(new byte[1024], 1024);
        socket.receive(packet);
        data = getData(packet);
        System.out.println(new Date() + ". received = " + data + " from " + packet.getSocketAddress());
        return data;
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient("127.0.0.3");
        String msg = client.sendEcho("hello");
        System.out.println("msg = " + msg);
    }
}
