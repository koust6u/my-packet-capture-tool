package org.example.capture.data.network;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.capture.constant.Constant;
import org.example.capture.data.datalink.Mac;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.capture.constant.Constant.*;

@Getter
@Builder
public class Ip {
    private final String subject;
    private final String version;
    private final String headerLength; //IHL
    private final String typeOfService; //TOS
    private final String totalLength; //(byte)
    private final int identification;
    private final String flags; // 항상 false, 분할 되지 않았는가?, 분할된 패킷이 더 있는가?
    private final String fragmentOffset;
    private final int timeToLive; //TTL
    private final String protocol;
    private final String checksum;
    private final String source;
    private final String destination;
    private final String fullData;

    @Override
    public String toString() {
        return fullData + "\n";
    }

    public static Ip initIp(List<String[]> lines, String header) {
        String subject = NOT_FOUND;
        String version = NOT_FOUND;
        String headerLength = NOT_FOUND;
        String protocol = NOT_FOUND;
        String destination = NOT_FOUND;
        int timeToLive = ZERO;
        int identification = ZERO;
        String fragmentOffset = NOT_FOUND;
        String source = NOT_FOUND;
        String checksum = NOT_FOUND;
        String flags = NOT_FOUND;
        String totalLength = NOT_FOUND;
        String typeOfService = NOT_FOUND;

        for (String[] line : lines) {
            if(line.length < 2) subject = line[0];
            else {
                if(line[0].equals(DESTINATION_ADDRESS)) destination = line[1];
                else if (line[0].equals(SOURCE_ADDRESS)) source = line[1];
                else if (line[0].equals(VERSION)) version = line[1];
                else if (line[0].equals(HEADER_LENGTH)) headerLength = line[1];
                else if (line[0].equals(TYPE_OF_SERVICE)) typeOfService = line[1];
                else if (line[0].equals(TOTAL_LENGTH)) totalLength = line[1];
                else if (line[0].equals(IDENTIFICATION)) identification = Integer.parseInt(line[1]);
                else if (line[0].equals(FLAG)) flags = line[1];
                else if (line[0].equals(FRAGMENT_OFFSET)) fragmentOffset = line[1];
                else if (line[0].equals(TIME_TO_LIVE)) timeToLive = Integer.parseInt(line[1]);
                else if (line[0].equals(PROTOCOL)) protocol = line[1];
                else if (line[0].equals(HEADER_CHECK_SUM)) checksum = line[1];
            }
        }

        return Ip.builder()
                .checksum(checksum)
                .destination(destination)
                .flags(flags)
                .typeOfService(typeOfService)
                .fragmentOffset(fragmentOffset)
                .protocol(protocol)
                .fullData(header)
                .source(source)
                .headerLength(headerLength)
                .identification(identification)
                .subject(subject)
                .totalLength(totalLength)
                .version(version)
                .timeToLive(timeToLive)
                .build();
    }
}
