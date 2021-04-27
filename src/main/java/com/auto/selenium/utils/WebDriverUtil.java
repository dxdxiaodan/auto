package com.auto.selenium.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Driver;

/**
 * @author
 * @date
 * @desc
 */

@Slf4j
public class WebDriverUtil {
    private String chromeDriver = (String)YamlReaderUtil.instance.getValueByKey("webdriver.chrome.driver");
    private String chromeDriverPath = (String)YamlReaderUtil.instance.getValueByKey("webdriver.chrome.driver-path");
    private String firefoxDriver = (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver");
    private String firefoxDriverPath = (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver-path");

    private void propertDriver(){
        System.setProperty(chromeDriver,chromeDriverPath);
        System.setProperty(firefoxDriver,firefoxDriverPath);

    }
    public WebDriver initChrome(){
        propertDriver();
        return  new InitChromeDriver().chromeDriver;
    }

    public WebDriver initFirefox(){
        propertDriver();
        return  InitFirefoxDriver.firefoxDriver;
    }

     class InitChromeDriver{
         WebDriver chromeDriver = new ChromeDriver();
    }
    static class InitFirefoxDriver{
        static WebDriver firefoxDriver = new FirefoxDriver();

    }

    public String getChromeDriver() {
        return (String)YamlReaderUtil.instance.getValueByKey("webdriver.chrome.driver");
    }

    public String getChromeDriverPath() {
        return (String)YamlReaderUtil.instance.getValueByKey("webdriver.chrome.driver-path");
    }

    public String getFirefoxDriver() {
        return (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver");
    }

    public String getFirefoxDriverPath() {
        return (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver-path");
    }
}

