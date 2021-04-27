package com.auto.selenium;

import com.auto.selenium.demo.AccessTab;
import com.auto.selenium.demo.login.HandleLoginPage;
import com.auto.selenium.demo.role.RoleManagerPage;
import com.auto.selenium.model.AuthModel;
import com.auto.selenium.model.AuthTree;
import com.auto.selenium.utils.DDTUtil;
import com.auto.selenium.utils.JsonUtil;
import com.auto.selenium.utils.WebDriverUtil;

import com.auto.selenium.utils.YamlReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@Slf4j
@SpringBootTest
public class RoleTests extends AbstractTestNGSpringContextTests {
    WebDriverUtil webDriverUtil;

    @Autowired
    HandleLoginPage handleLoginPage;

    @Autowired
    RoleManagerPage RoleManagerPage;

    @Autowired
    AccessTab accessTab;

    WebDriver chromeDriver;

    WebDriver firefoxDriver;

    AuthModel model;

    @BeforeTest
    public  void initDriver(){
        webDriverUtil = new WebDriverUtil();
        chromeDriver = webDriverUtil.initChrome();
        chromeDriver.manage().window().maximize();
        firefoxDriver = webDriverUtil.initFirefox();
        firefoxDriver.manage().window().maximize();
    }

    @DataProvider
    public Object[][] testData() {
        Object[][] testData = null;
        try{
            testData = DDTUtil.getTestData((String) YamlReaderUtil.instance.getValueByKey("test.auth.data1.path"), (String) YamlReaderUtil.instance.getValueByKey("test.auth.data1.sheetname"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return testData;
    }

    @Test(dataProvider = "testData")
    public void testDataProvider(String testNum,String testName,String adminUser,String adminPwd,String testUser,String testPwd,String authId,
                                 String authList,String disauthList,String authExpert,String disauthExpert) {
        List<AuthTree> allowAuth = (List<AuthTree>) JsonUtil.parseList(authList,AuthTree.class);
        List<AuthTree> disallowAuth = (List<AuthTree>) JsonUtil.parseList(disauthList,AuthTree.class);
        model = new AuthModel();
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
        log.info("数据读取转换成功【{}】",model.toString());
        handleLoginPage.handleLogin(chromeDriver,model.getAdminUser(),model.getAdminPwd());
        log.info("管理员登录,[{}]-[{}]",model.getAdminUser(),model.getAdminPwd());
        String result = RoleManagerPage.saveAuth(chromeDriver, model);
        Assert.assertEquals(result,"更新权限成功");
        logout();


    }

    //@Test
    public void testHandlerLogin() {
        String username = (String) YamlReaderUtil.instance.getValueByKey("user.handlers.user1");
        String pwd = YamlReaderUtil.instance.getValueByKey("user.handlers.pwd1")+"";
        handleLoginPage.handleLogin(chromeDriver,username,pwd);
    }

    //@Test(groups = {"chooseAllAuth"},dependsOnMethods = "testHandlerLogin",priority = 1)
    public  void choseeAllAuth(){
        choseeAllAuthByFirst();
        testTagretLogin();
        checkSysManagerChild();
    }

    //@Test(groups = {"dischoseeAllAuth"},dependsOnMethods = "testHandlerLogin",priority = 2)
    public  void dischoseeAllAuth(){
        dischoseeAllAuthByFirst();
        testTagretLogin();
        checkSysManagerChild1();
    }


    //@Test(groups = {"chooseAllAuth"},priority = 0,dependsOnMethods = "testHandlerLogin")
    public void choseeAllAuthByFirst() {
        String result = RoleManagerPage.choseeAllAuthByFirst(chromeDriver, "cedarhdrole895080576451616768");
        Assert.assertEquals(result,"更新权限成功");
    }

    //@BeforeMethod()
    public void testTagretLogin() {
        String username = (String) YamlReaderUtil.instance.getValueByKey("user.handlers.user2");
        String pwd = YamlReaderUtil.instance.getValueByKey("user.handlers.pwd2")+"";
        handleLoginPage.handleLogin(firefoxDriver,username,pwd);
    }

    //@Test(groups = {"chooseAllAuth"},priority = 1 )
    public void checkSysManagerChild(){
        int sysManageChildNum = accessTab.getSysManageChildNum(firefoxDriver);
        Assert.assertEquals(11,sysManageChildNum);
    }

    //@Test(groups = {"dischoseeAllAuth"} )
    public void dischoseeAllAuthByFirst() {
        RoleManagerPage.dischoseeAllAuthByFirst(chromeDriver,"cedarhdrole895080576451616768");
    }

    //@AfterTest
    //@AfterGroups(groups = {"chooseAllAuth" , "dischoseeAllAuth"})
    public void logout(){
        accessTab.handlerLogout(chromeDriver);
    }

    //dependsOnMethods = {"testTagretLogin"}
    //@Test(groups = {"dischoseeAllAuth"} )
    public void checkSysManagerChild1(){
        int sysManageChildNum = accessTab.getSysManageChildNum(firefoxDriver);
        Assert.assertEquals(0,sysManageChildNum);
    }

    @Test(enabled = false)
    public void testLogin1() {
        initDriver();
        //accessTab.handleLogin(firefoxDriver);
    }

    @Test(enabled = false)
    public void testSysAuth(){
        //int i = accessTab.accessSysNotice(chromeDriver);
        //Assert.assertTrue(i>0);
    }

    @Test(enabled = false)
    public void testRoleAuth(){
        //int i = accessTab.checkRoleAuth(firefoxDriver);
        //Assert.assertTrue(i>0);
    }
}
