package org.example.capture.data.transport;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.example.capture.data.network.Ip;

import java.util.Arrays;
import java.util.List;

import static org.example.capture.constant.Constant.*;
import static org.example.capture.constant.Constant.HEADER_CHECK_SUM;

@Getter
@Builder
public class TcpUdp {

    private final String subject;
    private final String sourcePort;
    private final String destinationPort;
    private final String sequenceNumber;
    private final int ackNumber;
    private final String dataOffset;
    private final Boolean urgentPointer;
    private final Boolean acknowledgment;
    private final Boolean push;
    private final Boolean reset;
    private final Boolean synchronize;
    private final Boolean finish;
    private final int window;
    private final String checkSum;
    private final String data;
    private final String decodedData;
    private final String fullData;

    @Override
    public String toString() {
        return fullData +"\n";
    }


    private static String hexStringToString(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }

        return new String(byteArray);
    }

    public static TcpUdp initTransport(List<String[]> lines, String header) {
        String data = NOT_FOUND;
        String checksum = NOT_FOUND;
        int window = ZERO;
        boolean urgentPointer = false;
        boolean finish = false;
        boolean synchronize = false;
        boolean reset = false;
        boolean push = false;
        boolean ack = false;
        String dataOffset = NOT_FOUND;
        int ackNumber = ZERO;
        String seqNumber = NOT_FOUND;
        String destinationPort = NOT_FOUND;
        String sourcePort = NOT_FOUND;
        String subject = NOT_FOUND;
        String decodedData =NOT_FOUND;

        for (String[] line : lines) {
            if(line.length < 2 && !line[0].contains("data")) subject = line[0];
            else {
                if(line[0].equals(HEX_STREAM)) data = line[1];
                else if (line[0].equals(CHECK_SUM)) checksum = line[1];
                else if (line[0].equals(WINDOW)) window = Integer.parseInt(line[1]);
                else if (line[0].equals(URGENT_POINTER)) urgentPointer = Boolean.parseBoolean(line[1]);
                else if (line[0].equals(FINISH)) finish = Boolean.parseBoolean(line[1]);
                else if (line[0].equals(SYNCHRONIZE)) synchronize = Boolean.parseBoolean(line[1]);
                else if (line[0].equals(RESET)) reset = Boolean.parseBoolean(line[1]);
                else if (line[0].equals(PUSH)) push = Boolean.parseBoolean(line[1]);
                else if (line[0].equals(ACK)) ack = Boolean.parseBoolean(line[1]);
                else if (line[0].equals(DATA_OFFSET)) dataOffset = line[1];
                else if (line[0].equals(ACKNOWLEDGEMENT_NUMBER)) ackNumber = Integer.parseInt(line[1]);
                else if (line[0].equals(SEQUENCE_NUMBER)) seqNumber = line[1];
                else if (line[0].equals(DESTINATION_PORT)) destinationPort = line[1];
                else if (line[0].equals(SOURCE_PORT)) sourcePort = line[1];
            }
        }
        if(!data.equals(NOT_FOUND)){
            decodedData = hexStringToString(data);
        }

        return TcpUdp.builder()
                .acknowledgment(ack)
                .ackNumber(ackNumber)
                .checkSum(checksum)
                .decodedData(decodedData)
                .push(push)
                .reset(reset)
                .destinationPort(destinationPort)
                .sourcePort(sourcePort)
                .data(data)
                .finish(finish)
                .fullData(header)
                .dataOffset(dataOffset)
                .subject(subject)
                .synchronize(synchronize)
                .window(window)
                .urgentPointer(urgentPointer)
                .sequenceNumber(seqNumber)
                .build();
    }
}
