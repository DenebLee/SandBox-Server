package kr.nanoit.socket;

import kr.nanoit.config.GetProperties_Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

class PropTest {
    @Test
    @DisplayName("server Test")
    void PropTest2() throws IOException {
        Properties ps = new Properties();
        GetProperties_Value pv = new GetProperties_Value(ps,dd);
        System.out.println(pv.getId());
    }
}
