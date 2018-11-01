package ru.tcezar.blockchain.transport.udp;

import java.net.DatagramPacket;

public class DatagramPacketUtils {
    public static String getData(DatagramPacket packet){
        return new String(packet.getData(), 0, packet.getLength());
    }
}
