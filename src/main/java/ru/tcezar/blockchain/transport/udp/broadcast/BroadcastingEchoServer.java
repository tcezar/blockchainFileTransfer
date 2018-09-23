package ru.tcezar.blockchain.transport.udp.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Random;

import static ru.tcezar.blockchain.transport.udp.DatagramPacketUtils.getData;

public class BroadcastingEchoServer extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];
    int anInt = new Random().nextInt();

    public BroadcastingEchoServer() throws IOException {
        socket = new DatagramSocket(null);
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(4445));
    }

    public static void main(String[] args) throws IOException {
        BroadcastingEchoServer broadcastingEchoServer = new BroadcastingEchoServer();
        broadcastingEchoServer.start();
    }
    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(new Date() + ". received = " + received);
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                buf = (received.toUpperCase() + ". id = " + anInt).getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                System.out.println(new Date() + ". send = " + getData(packet));
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
}
