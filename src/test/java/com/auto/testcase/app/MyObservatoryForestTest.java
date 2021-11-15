package com.auto.testcase.app;

import com.auto.appium.page.AgreementPage;
import com.auto.appium.page.Home2Page;
import com.auto.appium.utils.MobileDriverUtil;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.time.Duration;

@Slf4j
public class MyObservatoryForestTest {
    MobileDriverUtil androidDriverUtil;
    AndroidDriver androidDriver;
    Home2Page home2Page = new Home2Page();

    @BeforeTest
    public void initDriver() throws MalformedURLException {
        androidDriver = MobileDriverUtil.getAndroidDriver();
        // 隐私
        new AgreementPage().clickAgreeBtn(androidDriver);
        // 声明
        new AgreementPage().clickAgreeBtn(androidDriver);
        // 处理权限弹窗
        home2Page.handlePermissionDialog(androidDriver);
        home2Page.clickReminderCloseBtn(androidDriver);
    }

    /**
     * 打开MyObservatory app，进入9-Day页面
     */
    @Test
    public void open9Day(){
        home2Page.clickNavBtn(androidDriver);
        home2Page.sleep(10);
        home2Page.click9DaysTab(androidDriver);
    }
}
