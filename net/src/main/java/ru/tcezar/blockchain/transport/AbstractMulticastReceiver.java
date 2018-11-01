package ru.tcezar.blockchain.transport;

import ru.tcezar.blockchain.api.IMessageData;
import ru.tcezar.blockchain.transport.api.IListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public abstract class AbstractMulticastReceiver implements IListener {
    protected MulticastSocket socket = null;
    protected InetAddress group;

    protected byte[] buf = new byte[256];

    protected AbstractMulticastReceiver(MulticastSocket socket, InetAddress group) throws IOException {
        init(socket, group);
    }

    protected AbstractMulticastReceiver(String socketAddr, int socketPort) throws IOException {
        MulticastSocket multicastSocket = new MulticastSocket(socketPort);
        InetAddress group = InetAddress.getByName(socketAddr);
        init(multicastSocket, group);
    }

    private void init(MulticastSocket socket, InetAddress group) throws IOException {
        this.socket = socket;
        this.socket.joinGroup(group);
        this.group = group;
    }

    /**
     * Обработчик сообщения
     *
     * @param messageData Данные в сообщении
     * @return True, если необходимо перестать слушать
     */
    protected abstract boolean processMessage(IMessageData messageData);

    @Override
    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                try {
                    IMessageData recivedMessage = SerializationUtils.getData(packet.getData());
                    if (processMessage(recivedMessage)) {
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                if ("end".equals(received)) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
