package kr.nanoit.config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 검증
public class Verification {
    private final StringBuilder sb;
    private final String Param_id;
    private final String Param_pw;

    public Verification(String Param_id, String Param_pw, StringBuilder sb) {
        this.Param_id = Param_id;
        this.Param_pw = Param_pw;
        this.sb = sb;
    }

    public void Verification_PW(){
        try {
            StringBuilder sb = new StringBuilder();
            GetProperties_Value gv = new GetProperties_Value(Param_id,Param_pw);
            boolean id = gv.getId_value();
            boolean pw = gv.getPW_value();
            if (id && pw == true) {
                sb.append("<ServerInfo>");
                sb.append(System.getProperty("line.separator"));
                sb.append("  <IP>192.168.0.1</IP>");
                sb.append(System.getProperty("line.separator"));
                sb.append("  <Port>9001</Port>");
                sb.append(System.getProperty("line.separator"));
                sb.append("</ServerInfo>");
                log.info("Responses to be sent to clients XML");
            } else {
                sb.append("The requested ID and password do not match");
                log.info("Send a failed message as a validation failure");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
