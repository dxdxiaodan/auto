package com.auto.selenium.demo.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date
 * @desc
 */

public class CompanyLoginPage extends BasicPage {
    @FindBy(id="password4")
    WebElement password;
    @FindBy(id="userName4")
    WebElement username;
    @FindBy(id="J_getCode1")
    WebElement codeBtn;
    @FindBy(id="msgCode")
    WebElement code;
    @FindBy(xpath = "//button[contains(@onclick,'doSubmit()')]")
    WebElement loginBtn;

    /**
     * 供应商登录
     * @param driver
     */
    public void login(WebDriver driver) {
        driver.get(getPreUrl()+getLoginUrl());
        driver.findElement(By.id("userName4")).clear();
        driver.findElement(By.id("userName4")).sendKeys("");
        driver.findElement(By.id("password4")).sendKeys("");
        driver.findElement(By.id("J_getCode1")).click();
        driver.findElement(By.id("msgCode")).sendKeys("1");
        ((ChromeDriver) driver).executeScript("$('button[onclick^=doSubmit3]').click()","");
    }

}
