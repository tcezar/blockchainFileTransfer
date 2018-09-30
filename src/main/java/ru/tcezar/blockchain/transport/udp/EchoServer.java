package ru.tcezar.blockchain.transport.udp;

import java.io.IOException;
import java.net.*;

import static ru.tcezar.blockchain.transport.udp.DatagramPacketUtils.getData;

public class EchoServer extends Thread {
    private final InetAddress address;
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public EchoServer(String addr, Integer port) throws SocketException, UnknownHostException {
        address = InetAddress.getByName(addr);
        socket = new DatagramSocket(port, address);
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        EchoServer server = new EchoServer("127.0.0.3", 4445);
        server.start();
    }
    public void run() {
        running = true;

        while (running && !this.isInterrupted()) {

            try {
                DatagramPacket packet
                        = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String received = getData(packet);
                System.out.println(currentThread().getName()+". server " + this.address.toString() + " reciever from " + address.toString() + " data " + received);
                if (received.equals("end")) {
                    System.out.println(currentThread().getName()+". stop ");
                    this.interrupt();
                    continue;
                }
                buf = (received.toUpperCase()
                        + ". server = "
                        + socket.getLocalAddress().toString()
                ).getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
                System.out.println(currentThread().getName()+". server " + this.address.toString() + " send to " + address.toString() + " data " + getData(packet));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        socket.close();
    }

    @Override
    public void interrupt() {
        this.running = false;
        super.interrupt();
    }
}
