package com.auto.selenium.demo.login;

import com.auto.selenium.utils.YamlReaderUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date
 * @desc
 */
@Component
public class HandleLoginPage extends BasicPage {
    @FindBy(id="password")
    WebElement password;
    @FindBy(id="userName")
    WebElement username;
    @FindBy(xpath = "//button[contains(@onclick,'doSubmit()')]")
    WebElement loginBtn;
    @FindBy(xpath = "//li[contains(text(),'招标人登录')]")
    WebElement handleChangeTab;

    /**
     * 经办人登录
     * @param driver
     */
    public void handleLogin(WebDriver driver,String username,String pwd) {
        driver.get(getPreUrl()+getLoginUrl());
        HandleLoginPage handleLoginPage = PageFactory.initElements(driver, HandleLoginPage.class);
        handleLoginPage.handleChangeTab.click();
        handleLoginPage.username.clear();
        handleLoginPage.username.sendKeys(username);
        handleLoginPage.password.sendKeys(pwd);
        handleLoginPage.loginBtn.click();
    }


}
