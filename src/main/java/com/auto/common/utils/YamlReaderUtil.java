package com.auto.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 获得资源目录下yaml文件内容
 * @author dxd
 */
@Slf4j
public class YamlReaderUtil {

    private static Map<String, Map<String, Object>> properties = new HashMap<>();
    private static Map<String, Map<String, Map<String, Object>>> data = new HashMap<>();
    private  Map<String, Map<String, Object>> properties1 = new HashMap<>();
    private static String commonYaml = "application.yaml";
    private static String ACTIVE_ENV = "env.active";
    private static String LOCAL_FILE_NAME = "env.local.fileName";
    private static String TEST_FILE_NAME = "env.local.fileName";
    public static final YamlReaderUtil instance = new YamlReaderUtil();

    static {
        Yaml yaml = new Yaml();
        try {
            InputStream in = YamlReaderUtil.class.getClassLoader().getResourceAsStream(commonYaml);
            properties = yaml.loadAs(in, HashMap.class);
            String active = (String)getValueByKey(ACTIVE_ENV);
            InputStream activeInput = null;
            switch (active){
                case "test": {
                    activeInput = YamlReaderUtil.class.getClassLoader().getResourceAsStream((String)getValueByKey(TEST_FILE_NAME));
                };break;
                case "local": {
                    activeInput = YamlReaderUtil.class.getClassLoader().getResourceAsStream((String)getValueByKey(LOCAL_FILE_NAME));
                };break;
            }
            //data.put("application-local",properties);
            properties = yaml.loadAs(activeInput, HashMap.class);
        } catch (Exception e) {
            log.error("Init yaml failed !", e);
        }
    }

    /**
     * get yaml property
     *
     * @param key
     * @return
     */
    public static Object getValueByKey(String key) {
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
