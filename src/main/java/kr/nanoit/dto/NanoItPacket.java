package kr.nanoit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NanoItPacket implements Packet {
    private final Header header;
    private final Body body;

    @Builder
    public NanoItPacket(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
