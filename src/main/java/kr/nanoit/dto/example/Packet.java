package kr.nanoit.dto.example;

public interface Packet {
    Header getHeader();

    Body getBody();
}
