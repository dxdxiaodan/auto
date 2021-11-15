package com.auto.web.page;

import com.auto.common.utils.ElementTypeKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 首页相关操作
 * @author dxd
 */

@Slf4j
public class DesktopNavPage extends BasePage{
    String marketsXpath = "//a/button[contains(text(),'Markets')]";
    String url = "https://crypto.com/exchange";

    @Override
    public void open(WebDriver driver){
        super.openPage(driver,url);
    }

    public WebElement getMarketsBtn(WebDriver driver){
        WebElement elementByCss = super.getElement(driver, ElementTypeKeyUtil.XPATH, marketsXpath);
        return elementByCss;
    }

    public void clickMarketsBtn(WebDriver driver){
        super.click(getMarketsBtn(driver));
    }

    /**
     * 等待页面跳转
     * @param driver
     * @param time
     */
    public void waitJumpMarkets(WebDriver driver,String url,Long time){
        super.waitJump(driver,url,time);
    }

}
