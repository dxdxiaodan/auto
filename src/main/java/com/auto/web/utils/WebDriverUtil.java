package com.auto.web.utils;

import com.auto.common.utils.YamlReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author dxd
 */

@Slf4j
public class WebDriverUtil {
    private String chromeDriverText = (String) YamlReaderUtil.instance.getValueByKey("webdriver.chrome.driver");
    private String chromeDriverPath = (String)YamlReaderUtil.instance.getValueByKey("webdriver.chrome.driver-path");
    private String firefoxDriverText = (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver");
    private String firefoxDriverPath = (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver-path");

    private void propertDriver(){
        System.setProperty(chromeDriverText,chromeDriverPath);
        System.setProperty(firefoxDriverText,firefoxDriverPath);

    }
    public WebDriver initChrome(){
        propertDriver();
        return   InitChromeDriver.chromeDriver;
    }

    public WebDriver initChromeByEagger(){
        propertDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return  new InitOptChromeDriver().getChromeDriver(chromeOptions);
    }

    public WebDriver initFirefox(){
        propertDriver();
        return  InitFirefoxDriver.firefoxDriver;
    }

    static class InitChromeDriver{
         static WebDriver chromeDriver = new ChromeDriver();
    }

    class InitOptChromeDriver{
        WebDriver getChromeDriver(ChromeOptions chromeOptions){
            return new ChromeDriver(chromeOptions);
        }
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

    public String getFirefoxDriverText() {
        return (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver");
    }

    public String getFirefoxDriverPath() {
        return (String)YamlReaderUtil.instance.getValueByKey("webdriver.firefox.driver-path");
    }
}

