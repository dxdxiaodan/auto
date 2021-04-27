package com.auto.selenium.demo;

import com.auto.selenium.model.AuthTree;
import com.auto.selenium.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date
 * @desc
 */

public class Json2ObjectTest {
    String json="[{\n" +
            "    \"menuName\": \"系统管理\", \n" +
            "    \"level\": \"1\", \n" +
            "    \"menuCode\": \"\", \n" +
            "    \"link\": \"\", \n" +
            "    \"child\": [\n" +
            "        {\n" +
            "            \"menuName\": \"系统管理\", \n" +
            "            \"level\": \"1\", \n" +
            "            \"menuCode\": \"\", \n" +
            "            \"child\": [ ]\n" +
            "        }\n" +
            "    ]\n" +
            "}]";
    //Json
    @Test
    public void change(){
        List<AuthTree> o = (List<AuthTree>) JsonUtil.parseList(json,AuthTree.class);
        System.out.println(o);
    }
}
