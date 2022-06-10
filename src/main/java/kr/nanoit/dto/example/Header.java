package kr.nanoit.dto.example;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Header {
    private String type;
    private int bodySize;
}
