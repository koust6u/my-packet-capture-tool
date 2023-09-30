package org.example.capture.data.datalink;

import lombok.Builder;
import lombok.Data;
import org.example.capture.constant.Constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.capture.constant.Constant.NOT_FOUND;

@Data
@Builder
public class Mac {
    private final String subject;
    private final String destination;
    private final String source;
    private final String type;

    private final String fullData;

    @Override
    public String toString() {
        return fullData+"\n";
    }

    public static Mac initMac(List<String[]> lines, String header) {
        String subject = NOT_FOUND;
        String destination = NOT_FOUND;
        String source = NOT_FOUND;
        String type = NOT_FOUND;

        for (String[] line : lines) {
            if(line.length < 2) subject = line[0];
            if(line[0].equals(Constant.DESTINATION_ADDRESS)) destination = line[1];
            else if (line[0].equals(Constant.SOURCE_ADDRESS)) source = line[1];
            else if (line[0].equals(Constant.TYPE)) type = line[1];
        }

        return Mac.builder()
                .destination(destination)
                .source(source)
                .fullData(header)
                .subject(subject)
                .type(type)
                .build();
    }

}
