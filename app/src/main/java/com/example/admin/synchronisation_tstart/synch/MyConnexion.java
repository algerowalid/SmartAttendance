package com.example.admin.synchronisation_tstart.synch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MyConnexion {



    // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
    public static boolean isOnlinePing() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("192.168.1.101", 80);// Ping google

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false
                ; } // SHOULD BE FALSE TO WORK
    }


    public static boolean isOnlinePingDistant() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("82.196.25.71", 80);// Ping google

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false
                ; } // SHOULD BE FALSE TO WORK
    }



}
