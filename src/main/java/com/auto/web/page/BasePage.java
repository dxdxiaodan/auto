package com.auto.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

/**
 * selenium基础操作封装
 * @author dxd
 */
public class BasePage {

    public void open(WebDriver driver){
    }

    public void openPage(WebDriver driver,String url) {
        driver.get(url);
    }
    public void click(WebElement ele) {
        ele.click();
    }

    public WebElement getElementByXpath(WebDriver driver,String by) {
        return driver.findElement(By.xpath(by));
    }

    public WebElement getElement(WebDriver driver, String type, String context){
        WebElement ele;
        switch (type){
            case "xpath":{
                ele = driver.findElement(By.xpath(context));
            };break;
            case "class_name":{
                ele = driver.findElement(By.className(context));
            };break;
            case "css_selector":{
                ele = driver.findElement(By.cssSelector(context));
            };break;
            default:{
                ele = driver.findElement(By.id(context));
            }
        }
        return ele;
    }

    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public Boolean element_is_enabled(WebDriver driver, WebElement ele) {
        return ele.isEnabled();
    }

    public void quit(WebDriver driver) {
        driver.quit();
    }

    public void close(WebDriver driver) {
        driver.close();
    }

    /**
     * 等待页面跳转
     * @param driver
     * @param time
     */
    public void waitJump(WebDriver driver,String url,Long time){
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        webDriverWait.until(ExpectedConditions.urlContains(url));
    }

    /**
     * 滚动
     * @param driver
     * @param x
     * @param y
     */
    public void scrollPage(WebDriver driver,int x,int y){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy("+x+","+y+")");
    }

    /**
     * 判断是否滚动到最低
     * @param driver
     * @return
     */
    public Boolean scrollEnd(WebDriver driver){
        String jq = "var scrollTop = $(this).scrollTop();var scrollHeight = $(document).height();var windowHeight = $(this).height();" +
                "if(scrollTop + windowHeight == scrollHeight){return true;}else{return false;}";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean result = (Boolean)js.executeScript(jq);
        return result;
    }

    /**
     * 当前线程睡眠
     * @param time
     */
    public void sleep(int time){
        try{
            Thread.sleep(time);
        }catch (Exception e){
            Reporter.log(e.getMessage());
        }
    }

    public Object executeJS(WebDriver driver,String js){
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        return jsDriver.executeScript(js);
    }
}


