package kr.nanoit.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.util.Properties.*;

/**
 * The key value you want to import must be passed to the parameter
 */

public class GetProperties_Value {
    private Properties properties;
    private final InputStream inputStream;
    private final String Param_id;
    private final String Param_pw;
    private final String propFileName = "serverValue.properties";

    public GetProperties_Value(Properties properties,String Param_id,String Param_pw) throws IOException {
        this.Param_id = Param_id;
        this.Param_pw = Param_pw;
        this.inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        this.properties = Properties.load(inputStream);
    }

    public boolean getId_value(){
        boolean test = false;
        if (properties.getProperty(Param_id) == null) {
            test = true;
        }
        return test;
    }

    public boolean getPW_value(){
        boolean test2 = false;
        if (properties.getProperty(Param_pw) == null) {
            test2 = true;
        }
        return test2;
    }
}