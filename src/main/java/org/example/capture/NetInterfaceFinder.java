package org.example.capture;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetInterfaceFinder {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostName = inetAddress.getHostName();
        String hostAddress = inetAddress.getHostAddress();
        System.out.println("host name: " + hostName);
        System.out.println("host address: "+ hostAddress);
    }
}
