package com.auto.selenium.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date
 * @desc
 */
@Slf4j
public class YamlReaderUtil {

    private static Map<String, Map<String, Object>> properties = new HashMap<>();
    private static Map<String, Map<String, Map<String, Object>>> data = new HashMap<>();
    private  Map<String, Map<String, Object>> properties1 = new HashMap<>();

    public static final YamlReaderUtil instance = new YamlReaderUtil();

    static {
        Yaml yaml = new Yaml();
        try (InputStream in = YamlReaderUtil.class.getClassLoader().getResourceAsStream("application-local.yaml");) {
            properties = yaml.loadAs(in, HashMap.class);
            data.put("application-local",properties);
        } catch (Exception e) {
            log.error("Init yaml failed !", e);
        }
        /*try (InputStream in = YamlReaderUtil.class.getClassLoader().getResourceAsStream("testData.yaml");) {
            properties = yaml.loadAs(in, HashMap.class);
            data.put("testData",properties);
        } catch (Exception e) {
            log.error("Init yaml failed !", e);
        }*/
    }

/*    public Object getValueByKey(String name,String key) {
        Map<String, Map<String, Object>> stringObjectMap = properties1.get(name);
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            separatorKeys = key.split("\\.");
        } else {
            return stringObjectMap.get(key);
        }
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) stringObjectMap.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null) {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }*/

    /**
     * get yaml property
     *
     * @param key
     * @return
     */
    public Object getValueByKey(String key) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            separatorKeys = key.split("\\.");
        } else {
            return properties.get(key);
        }
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null) {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }
}
