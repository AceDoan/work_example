package com.example.demo.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;;

public enum Status {
    PLANNING, DOING, COMPLETE;

    @JsonCreator
    public static Status decode(final int code) {
        return Stream.of(Status.values()).filter(targetEnum -> targetEnum.ordinal() == code).findFirst().orElse(null);
    }

}
