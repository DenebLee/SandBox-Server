package kr.nanoit.dto;

public interface Packet {
    Header getHeader();

    Body getBody();
}
