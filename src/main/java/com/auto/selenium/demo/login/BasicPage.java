package com.auto.selenium.demo.login;

import com.auto.selenium.utils.YamlReaderUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date
 * @desc
 */
public class BasicPage {
    private String preUrl;
    private String loginUrl;

    public String getPreUrl() {
        return (String)YamlReaderUtil.instance.getValueByKey("web-pre-url");
    }

    public String getLoginUrl() {
        return (String)YamlReaderUtil.instance.getValueByKey("web-login-url");
    }

}
