package org.example.capture.constant;

import lombok.Getter;

import java.lang.invoke.SwitchPoint;

@Getter
public enum Protocols {
    UDP("UDP"), TCP("TCP"), HTTP("HTTP"), HTTPS("HTTPS") , OTHERS("OTHERS");

    private final String name;
    Protocols(String name) {
        this.name = name;
    }


}
