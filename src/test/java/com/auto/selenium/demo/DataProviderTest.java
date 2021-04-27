package com.auto.selenium.demo;

import com.auto.selenium.model.AuthModel;
import com.auto.selenium.model.AuthTree;
import com.auto.selenium.utils.DDTUtil;
import com.auto.selenium.utils.JsonUtil;
import com.auto.selenium.utils.YamlReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author
 * @date
 * @desc
 */
@Slf4j
public class DataProviderTest {
    @BeforeClass
    public void BeforeClass() {
        System.out.println("Before Class");
    }
    @BeforeMethod
    public void BeforeMethod() {
        System.out.println("Before Method");
    }
    //@DataProvider
    public Object[][] paramData() {
        return new Object[][] {
                {"Add 1 + 2", 1, 2, 3},
                {"Add null + null", null, null, null},
                {"Add Big", Integer.MAX_VALUE, Integer.MAX_VALUE, null}
        };
    }
    @Test(dataProvider = "ahtuData")
    public void testDataProvider(String testNum,String testName,String adminUser,String adminPwd,String testUser,String testPwd,String authId,
                                 String authList,String disauthList,String authExpert,String disauthExpert) {
        List<AuthTree> allowAuth = (List<AuthTree>) JsonUtil.parseList(authList,AuthTree.class);
        List<AuthTree> disallowAuth = (List<AuthTree>) JsonUtil.parseList(disauthList,AuthTree.class);
        AuthModel model = new AuthModel();
        model.setAdminPwd(adminPwd);
        model.setAdminUser(adminUser);
        model.setAuthExpert(authExpert);
        model.setAuthId(authId);
        model.setAuthList(allowAuth);
        model.setDisauthList(disallowAuth);
        model.setDisauthExpert(disauthExpert);
        model.setTestName(testName);
        model.setTestNum(testNum);
        model.setTestUser(testUser);
        model.setTestPwd(testPwd);
        log.info(model.toString());
    }

    @DataProvider
    public Object[][] ahtuData() {
        Object[][] testData = null;
        try{
            testData = DDTUtil.getTestData((String) YamlReaderUtil.instance.getValueByKey("test.auth.data1.path"), (String) YamlReaderUtil.instance.getValueByKey("test.auth.data1.sheetname"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return testData;
    }
}