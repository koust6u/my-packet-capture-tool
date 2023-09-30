package org.example.capture.parse;

import lombok.NoArgsConstructor;
import org.example.capture.constant.Protocols;

import java.util.List;

import static org.example.capture.constant.Constant.*;

@NoArgsConstructor
public class Parser {


    public List<String> getPacketByProtocol(List<String> data, Protocols protocols){
        String[] findProtocol = getParseData(data, PROTOCOL);
        if (findProtocol[0].equals(NOT_FOUND)){
            return null;
        }
        String protocol = findProtocol[1].substring(2).trim().substring(1,4);
        if(protocols.getName().equals(protocol)){
            return data;
        }
        return null;
    }





    public String getHexStream(List<String> data){
        String[] parseDatum = getParseData(data, HEX_STREAM);
        return parseDatum.length > 1 ? parseDatum[1].trim() + "\n": NOT_FOUND + "\n";
    }

    private String[] getParseData(List<String> data, String category){
        return data.stream()
                .map(pair -> {
                    String[] temp = pair.split(":");
                    if(temp.length < 2) return new String[]{temp[0]};
                    return new String[]{temp[0].trim(), temp[1].trim()};
                })
                .filter(pair -> pair[0].equals(category))
                .findFirst().orElse(new String[]{NOT_FOUND});
    }

}
