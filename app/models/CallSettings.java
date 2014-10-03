package models;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.Map;

/**
 * Created by rashok on 9/8/14.
 */
public class CallSettings {

    public String To;
    public String Url;
    public String SendDigits;

    public static Map<String, String> getMap(CallSettings setting) {
        ObjectMapper m = new ObjectMapper();
        Map<String, String> props = m.convertValue(setting, Map.class);
        return props;
    }
}
