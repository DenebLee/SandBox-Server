package kr.nanoit.socket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class MainTest {
    public static void main(String[] args) {
        Dto dto = new Dto("nanoit");
        System.out.println(dto.getUsername());;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    static class Dto {
        private final String username;
    }


}
