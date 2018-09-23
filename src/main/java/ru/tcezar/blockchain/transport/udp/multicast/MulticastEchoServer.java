package ru.tcezar.blockchain.transport.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.Random;

import static ru.tcezar.blockchain.transport.udp.DatagramPacketUtils.getData;

public class MulticastEchoServer extends Thread {

    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    protected InetAddress group = null;
    int anInt = new Random().nextInt();

    public MulticastEchoServer() throws IOException {
        socket = new MulticastSocket(4446);
        socket.setReuseAddress(true);
        group = InetAddress.getByName("230.0.0.0");
        socket.joinGroup(group);
    }

    public static void main(String[] args) {
        try {
            MulticastEchoServer multicastEchoServer = new MulticastEchoServer();
            System.out.println("id = " + multicastEchoServer.anInt);
            multicastEchoServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String received = getData(packet);
                System.out.println(new Date() + ". received = " + received);
                if (received.equals("end")) {
                    break;
                }
                buf = (received.toUpperCase() + ". id = " + anInt).getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                System.out.println(new Date() + ". send = " + getData(packet));
                socket.send(packet);
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
