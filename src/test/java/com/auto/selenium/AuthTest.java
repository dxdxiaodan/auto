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
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author
 * @date
 * @desc
 */
@Slf4j
public class AuthTest {
    WebDriverUtil webDriverUtil;

    HandleLoginPage handleLoginPage = new HandleLoginPage();

    RoleManagerPage RoleManagerPage = new RoleManagerPage();

    AccessTab accessTab = new AccessTab();

    WebDriver chromeDriver;

    WebDriver firefoxDriver;

    AuthModel model;

    @BeforeTest
    public  void initDriver(){
        webDriverUtil = new WebDriverUtil();
        chromeDriver = webDriverUtil.initChrome();
        chromeDriver.manage().window().maximize();
        firefoxDriver = webDriverUtil.initChrome();
        firefoxDriver.manage().window().maximize();
    }

    @AfterClass
    public void end(){
        chromeDriver.quit();
        firefoxDriver.quit();
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
        model = new AuthModel(testNum, testName, adminUser, adminPwd, testUser, testPwd, authId,
                 authList, disauthList, authExpert, disauthExpert);

        log.info("数据读取转换成功【{}】",model.toString());
        handleLoginPage.handleLogin(chromeDriver,model.getAdminUser(),model.getAdminPwd());
        log.info("管理员登录,[{}]-[{}]",model.getAdminUser(),model.getAdminPwd());
        String result = RoleManagerPage.saveAuth(chromeDriver, model);
        Assert.assertEquals(result,"更新权限成功");

        handleLoginPage.handleLogin(firefoxDriver,model.getTestUser(),model.getTestPwd());
        model.getAuthList().stream().forEach(parent ->{
            parent.getChild().stream().forEach(child -> {
                boolean r = accessTab.checkAccessAuth(firefoxDriver, child.getLink(),child.getMenuName());
                Assert.assertEquals(true,r,"用户["+model.getTestUser()+"]["+parent.getMenuName()+"："+child.getMenuName()+"]["+child.getLink()+"]"+child.getMenuName()+"无权限访问");
                Reporter.log("用户["+model.getTestUser()+"]是否有权限访问页面["+parent.getMenuName()+"："+child.getMenuName()+"]【"+child.getLink()+"】:"+r);
            });
        });
        model.getDisauthList().stream().forEach(parent ->{
            parent.getChild().stream().forEach(child -> {
                boolean r = accessTab.checkUnaccessAuth(firefoxDriver, child.getLink(), model.getDisauthExpert());
                Assert.assertEquals(true,r,"用户["+model.getTestUser()+"]["+parent.getMenuName()+"："+child.getMenuName()+"]["+child.getLink()+"]不符合页面包含【"+model.getDisauthExpert()+"】要求");
                Reporter.log("用户["+model.getTestUser()+"]是否无权限访问页面["+parent.getMenuName()+"："+child.getMenuName()+"]【"+child.getLink()+"】:"+r);
            });
        });

    }

    @AfterMethod()
    public void logout(){
        accessTab.handlerLogout(chromeDriver);
        accessTab.handlerLogout(firefoxDriver);
    }

}
