package ru.tcezar.blockchain.transport.udp.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;

import static ru.tcezar.blockchain.transport.udp.DatagramPacketUtils.getData;

public class BroadcastingEchoServer extends Thread {

    protected DatagramSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];

    public BroadcastingEchoServer(Integer port) throws IOException {
        socket = new DatagramSocket(null);
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(port));
    }

    public BroadcastingEchoServer(String s, int expectedServerCount) throws IOException {
        new BroadcastingEchoServer(4445);
    }

    public static void main(String[] args) throws IOException {
        BroadcastingEchoServer broadcastingEchoServer = new BroadcastingEchoServer();
        broadcastingEchoServer.start();
    }

    public void run() {
        running = true;
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                //packet = new DatagramPacket(new byte[1024], 1024, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(new Date() + ". received = " + received);
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                buf = (received.toUpperCase() + ". hashCode = " + hashCode()).getBytes();
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
