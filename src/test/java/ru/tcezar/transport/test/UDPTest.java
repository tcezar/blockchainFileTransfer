package ru.tcezar.transport.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.tcezar.blockchain.transport.udp.EchoClient;
import ru.tcezar.blockchain.transport.udp.EchoServer;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UDPTest {
    EchoClient client;
    EchoClient client2;
    EchoServer server;
    EchoServer server2;

    @Before
    public void setup() throws SocketException, UnknownHostException {
        server = new EchoServer("127.0.0.2");
        server2 = new EchoServer("127.0.0.3");
        server.start();
        server2.start();
        try {
            client = new EchoClient("127.0.0.2");
            client2 = new EchoClient("127.0.0.3");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenCanSendAndReceivePacket_thenCorrect() throws IOException {
        System.out.println("//////////////////////////////////////////////////////////////");
        String echo = client.sendEcho("hello server");
        System.out.println(Thread.currentThread().getName()+". echo = "+echo);
        assertTrue(echo.toLowerCase().startsWith("hello server"));

        System.out.println("//////////////////////////////////////////////////////////////");
        echo = client2.sendEcho("hello server");
        System.out.println(Thread.currentThread().getName()+" .echo = "+echo);
        assertTrue(echo.toLowerCase().startsWith("hello server"));

        System.out.println("//////////////////////////////////////////////////////////////");
        // server.interrupt();
        echo = client.sendEcho("server is working");
        System.out.println(Thread.currentThread().getName()+". echo = "+echo);
        assertFalse(echo.equals("hello server"));


        System.out.println("//////////////////////////////////////////////////////////////");
        // server.interrupt();
        echo = client2.sendEcho("server is working");
        System.out.println(Thread.currentThread().getName()+" .echo = "+echo);
        assertFalse(echo.equals("hello server"));

    }

    @After
    public void tearDown() {
        try {
            ((Runnable) () -> {
                try {
                    client.sendEcho("end");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).run();
            ((Runnable) () -> {
                try {
                    client2.sendEcho("end");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).run();
            client.close();
            client2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
