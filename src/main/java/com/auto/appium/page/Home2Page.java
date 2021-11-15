package com.auto.appium.page;

import com.auto.common.utils.ElementTypeKeyUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import java.util.Set;

@Slf4j
public class Home2Page extends BasePage{
    String reminderCloseBtnId = "hko.MyObservatory_v1_0:id/btn_friendly_reminder_skip";
    String navBtnXpath = "//android.widget.ImageButton[@content-desc='转到上一层级']";

    public void clickReminderCloseBtn(AndroidDriver driver){
        this.clickElement(this.getElement(driver, "",reminderCloseBtnId));
        //super.sleep(10);
    }

    public void clickNavBtn(AndroidDriver driver){
        /*WebElement element = this.getElement(driver, ElementTypeKeyUtil.XPATH, navBtnXpath);
        this.clickElement(element);*/
        new TouchAction(driver).tap(PointOption.point(51, 85)).perform();
    }

    public void click9DaysTab(AndroidDriver driver){
        PointOption press = PointOption.point(226, 920);
        PointOption moveTo = PointOption.point(226, 500);
        super.swipeView(driver,press,moveTo);
    }
}
