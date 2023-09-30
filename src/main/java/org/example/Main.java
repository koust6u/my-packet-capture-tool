package org.example;

import org.example.capture.CaptureService;
import org.example.capture.parse.Parser;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws PcapNativeException {

        Scanner sc = new Scanner(System.in);

        System.out.println("find my network interfaces");
        for (PcapNetworkInterface networkInterface : Pcaps.findAllDevs()) {
            // 네트워크 인터페이스 정보 출력
            System.out.println("Interface: " + networkInterface.getName());
            System.out.println("Description: " + networkInterface.getDescription());
            System.out.println("Loopback: " + networkInterface.isLoopBack());
            System.out.println("Datalink Address: " + networkInterface.getLinkLayerAddresses());
            System.out.println();
        }

        System.out.println("to apply interface name (String)");
        String interfaceName = sc.next();
        System.out.println("time out (integer) ");
        int timeout = sc.nextInt();
        int maxSnapLength = 65536;

        CaptureService utils = CaptureService.builder()
                .mode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS)
                .interfaceName(interfaceName)
                .maxSnapLength(maxSnapLength)
                .timeout(timeout)
                .parser(new Parser())
                .build();
        while(true){
            print();
            int cmd = sc.nextInt();
            switch (cmd){
                case 1:
                    utils.capture(10000);
                    break;
                case 2:
                    System.out.println("Enter the protocol to capture (ex// http, https, tcp, udp)");
                    String protocol = sc.next();
                    System.out.println("destination ip (ex// 0.0.0.0.1)");
                    String destination = sc.next();
                    utils.captureByProtocol(10000, destination ,protocol);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command");
                    break;

            }
        }



    }

    private static void print(){
        System.out.println("#######################");
        System.out.println("1. Capture all packets");
        System.out.println("2. Capture with destination ip");
        System.out.println("3. exit");
        System.out.println("#######################");

    }
}