package com.auto.appium.utils;

import com.auto.common.utils.YamlReaderUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverUtil {
    private static volatile AndroidDriver androidDriver;

    /**
     * 懒加载，全局唯一
     * @return
     * @throws MalformedURLException
     */
    public static  AndroidDriver getAndroidDriver() throws MalformedURLException {
        if (androidDriver == null) {
            synchronized(MobileDriverUtil.class){
                androidDriver = new AndroidDriver(new URL(getRemoteURL()), getDesiredCapabilities());
            }
        }
        return androidDriver;
    }

    public static DesiredCapabilities getDesiredCapabilities(){
        String deviceName = (String) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.deviceName");
        String automationName = (String) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.automationName");
        String platformName = (String) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.platformName");
        String platformVersion = (String) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.platformVersion");
        String appPackage = (String) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.appPackage");
        String appActivity = (String) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.appActivity");
        Boolean skipServerInstallation = (Boolean) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.skipServerInstallation");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("deviceName", deviceName);

        capabilities.setCapability("automationName", automationName);

        capabilities.setCapability("platformName", platformName);

        capabilities.setCapability("platformVersion", platformVersion);

        capabilities.setCapability("appPackage", appPackage);

        capabilities.setCapability("appActivity", appActivity);

        capabilities.setCapability("skipServerInstallation",skipServerInstallation);

        return capabilities;
    }

    public static String getRemoteURL(){
        return (String) YamlReaderUtil.instance.getValueByKey("MobileDriver.AndroidDriver.romoteUrl");
    }

    public String t(){
        return "1";
    }
}
