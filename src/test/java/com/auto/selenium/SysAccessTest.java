package com.auto.selenium;

import com.auto.selenium.demo.AccessTab;
import com.auto.selenium.utils.WebDriverUtil;
import junit.framework.TestCase;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author
 * @date
 * @desc
 */

@SpringBootTest()
public class SysAccessTest extends TestCase {
    @Autowired
    WebDriverUtil webDriverUtil;
    WebDriver chromeDriver;

    /*protected void setUp() {
        chromeDriver = webDriverUtil.initChrome();
    }

    public void testLogin() {
        new AccessTab().login(chromeDriver);
    }*/
}
