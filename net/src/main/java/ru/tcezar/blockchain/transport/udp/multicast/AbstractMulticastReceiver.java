package ru.tcezar.blockchain.transport.udp.multicast;

import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.transport.api.IListener;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

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
    protected abstract boolean processMessage(IMessage messageData);

    @Override
    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                IMessage recivedMessage = null;
                try {
                    recivedMessage = SerializationUtils.getData(packet.getData());
                    if (processMessage(recivedMessage)) {
                        // никогда не останавливаемся
                        errors(recivedMessage);
//                        break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // обработка остановки слушателя
                if (isStoping(recivedMessage)) {
                    break;
                }
//                String received = new String(
//                        packet.getData(), 0, packet.getLength());
//                if ("end".equals(received)) {
//                    break;
//                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * обработка остановки потока
     * @param recivedMessage
     * @return
     */
    protected abstract boolean isStoping(IMessage recivedMessage);

    /**
     * обработчик ошибки слушателя
     *
     * @param recivedMessage
     */
    protected abstract void errors(IMessage recivedMessage) throws UnknownHostException;
}
