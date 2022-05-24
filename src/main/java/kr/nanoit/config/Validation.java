package kr.nanoit.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Validation {
    private String param_Id = null;
    private String param_pw = null;
    private String fileName = "NanoitServer.properties";

    public Validation(String id , String pw ) {
        this.param_Id = id;
        this.param_pw = pw;
    }

    public void ValidationValue() throws IOException {
        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        prop.load(is);

        if(param_Id.equals(prop.getProperty("auth.id.count"))){
            System.out.println("value is match");
        } else if(param_Id != prop.getProperty("auth.id.count")){
            System.out.println("value is not match");
        }


    }
}
