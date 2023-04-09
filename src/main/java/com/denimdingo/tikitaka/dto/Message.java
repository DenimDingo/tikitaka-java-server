package com.denimdingo.tikitaka.dto;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String type;
    private String name;
    private String msg;
    private String date;
    private String sender;
    private String receiver;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void newConnect() {
        this.type = "new";
    }

    public void closeConnect() {
        this.type = "close";
    }

}
