package org.example.capture.data;

import lombok.Getter;
import org.example.capture.data.datalink.Mac;
import org.example.capture.data.network.Ip;
import org.example.capture.data.transport.TcpUdp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CapturedData {
    private final Mac mac;
    private final Ip ip;
    private final TcpUdp tcpUdp;

    public CapturedData(String header, String network, String transport){
        List<String[]> macLines = initialize(header);
        List<String[]> ipLines = initialize(network);
        List<String[]> transportLines = initialize(transport);
        mac = Mac.initMac(macLines, header);
        ip = Ip.initIp(ipLines, network);
        tcpUdp = TcpUdp.initTransport(transportLines, transport);
    }


    private List<String[]> initialize(String header) {
        String[] headerSplit = header.split("\n");
        return Arrays.stream(headerSplit)
                .map(pair -> {
                    pair = pair.replaceAll("\\s", "");
                    String[] split = pair.split(":",2);
                    if (split.length > 1) return new String[]{split[0], split[1]};
                    return new String[]{split[0]};
                }).collect(Collectors.toList());
    }


}
