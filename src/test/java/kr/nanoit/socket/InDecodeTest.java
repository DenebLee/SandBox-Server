package kr.nanoit.socket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class InDecodeTest {
    @Test
    @DisplayName("")
    void Test(){
        //given
        String originalInput = "test value input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes(StandardCharsets.UTF_8));
        //when
        System.out.println(encodedString);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);

        System.out.println(decodedString);
        
        
        //then
        
        }
    }

