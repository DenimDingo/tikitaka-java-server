package com.denimdingo.tikitaka.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Response {
    private final String from;
    private final String text;
    private final String time;


}
