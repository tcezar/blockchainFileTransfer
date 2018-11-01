package ru.tcezar.blockchain.transport;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    final InetAddress address;
    final int port;

    public TCPClient(String addr, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(addr);
        this.port = port;
    }

    public static void main(String argv[]) throws Exception {
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = inFromUser.readLine())!=null) {
            TCPClient tcpClient = new TCPClient("localhost", 6789);
            modifiedSentence = tcpClient.sendData(line);
        }
        TCPClient tcpClient = new TCPClient("localhost", 6789);
        modifiedSentence = tcpClient.sendData("quit");
    }

    public String sendData(String data) {
        String modifiedSentence = null;
        try {
            Socket clientSocket = new Socket(this.address, this.port);
            System.out.println("clientSocket = " + clientSocket.toString());
            // отправляем сообщение
            System.out.println("sentence: " + data);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(data + "\n");
            // читаем ответ
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modifiedSentence;
    }
}
