package org.example.capture;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.capture.constant.Constant;
import org.example.capture.data.CapturedData;
import org.example.capture.parse.Parser;
import org.pcap4j.core.*;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;



@Slf4j
@Builder
@Getter
public class CaptureService {
    private final String interfaceName;
    private final int maxSnapLength;
    private final int timeout;
    private final PcapNetworkInterface.PromiscuousMode mode;
    private Parser parser ;

    public void capture(int packetCount) {
        capture(packetCount, "All");
    }

    public void captureByProtocol(int packetCount, String ip, String protocol) {
        capture(packetCount, protocol, ip);
    }


    private void capture(int packetCount, String protocol, String ip) {
        PcapHandle handle = null;
        try{
            PcapNetworkInterface nif = Pcaps.getDevByName(interfaceName);
            handle = nif.openLive(maxSnapLength, mode, timeout);
            handle.loop(packetCount, pcapPacket -> {
                EthernetPacket ethernetPacket = pcapPacket.get(EthernetPacket.class);
                Packet payload = ethernetPacket.getPayload();
                String ethernet = ethernetPacket.getHeader().toString();
                String network = payload.getHeader().toString();
                String transport = payload.getPayload().toString();
                CapturedData capturedData = new CapturedData(ethernet, network, transport);

                String toUpper = protocol.toUpperCase();
                System.out.println(capturedData.getIp().getDestination());
                System.out.println(capturedData.getIp().getProtocol());

                if((capturedData.getIp().getDestination().contains(ip) && capturedData.getIp().getProtocol().contains(toUpper) )
                ){
                    System.out.println("-------------"+ protocol +"----------------");
                    System.out.println("=====ETHERNET=====");
                    System.out.println(ethernet);
                    System.out.println("=====NETWORK=====");
                    System.out.println(network);
                    System.out.println("=====TRANSPORT=====");
                    System.out.println(transport);
                    System.out.println("=====Captured Data=====");
                    System.out.println(capturedData.getTcpUdp().getData());
                    System.out.println("=====Decoded"+ protocol + "Data=====");
                    System.out.println(capturedData.getTcpUdp().getDecodedData());
                    System.out.println("======================");
                }

            });
        } catch (PcapNativeException e) {
            log.error("can not found interface by Interface name: {}", interfaceName);
        } catch (NotOpenException | InterruptedException e) {
            log.error("System Exception Occur");
        }finally {
            if (handle != null) handle.close();
        }
    }


    private void capture(int packetCount, String protocol) {
        PcapHandle handle = null;
        try{
            PcapNetworkInterface nif = Pcaps.getDevByName(interfaceName);
            handle = nif.openLive(maxSnapLength, mode, timeout);
            handle.loop(packetCount, pcapPacket -> {
                EthernetPacket ethernetPacket = pcapPacket.get(EthernetPacket.class);
                Packet payload = ethernetPacket.getPayload();
                String ethernet = ethernetPacket.getHeader().toString();
                String network = payload.getHeader().toString();
                String transport = payload.getPayload().toString();
                CapturedData capturedData = new CapturedData(ethernet, network, transport);

                System.out.println("-------------"+ protocol +"----------------");
                System.out.println("=====ETHERNET=====");
                System.out.println(ethernet);
                System.out.println("=====NETWORK=====");
                System.out.println(network);
                System.out.println("=====TRANSPORT=====");
                System.out.println(transport);
                System.out.println("=====Captured Data=====");
                System.out.println(capturedData.getTcpUdp().getData());
                System.out.println("=====Decoded"+ protocol + "Data=====");
                System.out.println(capturedData.getTcpUdp().getDecodedData());
                System.out.println("======================");


            });
        } catch (PcapNativeException e) {
            log.error("can not found interface by Interface name: {}", interfaceName);
        } catch (NotOpenException | InterruptedException e) {
            log.error("System Exception Occur");
        }finally {
            if (handle != null) handle.close();
        }
    }




}
