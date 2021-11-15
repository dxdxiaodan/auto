package com.auto.appium.page;

import com.auto.common.utils.ElementTypeKeyUtil;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import java.time.Duration;

@Slf4j
public class BasePage {
    public void sleep(int time){
        try{
            Thread.sleep(time);
        }catch (Exception e){
            Reporter.log(e.getMessage());
        }
    }

    public WebElement getElement(AndroidDriver driver,String type,String context){
        WebElement ele;
        switch (type){
            case "xpath":{
                ele = driver.findElement(By.xpath(context));
            };break;
            case "class_name":{
                ele = driver.findElement(By.className(context));
            };break;
            default:{
                ele = driver.findElementById(context);
            }
        }
        return ele;
    }

    public void clickElement(WebElement ele){
        ele.click();
    }


    /**
     * 自动确认权限弹窗
     * @param driver
     */
    public void handlePermissionDialog(AndroidDriver driver){
        String context = "//android.widget.Button[contains(@text,'確定')]";
        String context1 = "//android.widget.Button[contains(@text,'允许')]";
        while(true){
            if(driver.getPageSource().contains("確定")){
                clickElement(this.getElement(driver, ElementTypeKeyUtil.XPATH,context));
            }if(driver.getPageSource().contains("允许")){
                clickElement(this.getElement(driver, ElementTypeKeyUtil.XPATH,context1));
            }else{
                Reporter.log("权限处理完毕");
                break;
            }
        }
    }

    public void runPackground(AndroidDriver androidDriver,Duration time){
        androidDriver.runAppInBackground(time);
    }

    /**
     * 滑动
     * @param androidDriver
     * @param press
     * @param moveTo
     */
    public void swipeView(AndroidDriver androidDriver, PointOption press,PointOption moveTo){
        (new TouchAction(androidDriver)).press(press).moveTo(moveTo).release().perform();
    }
}
