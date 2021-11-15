package com.auto.appium.page;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class AgreementPage extends BasePage{
    String agreeBtnId = "hko.MyObservatory_v1_0:id/btn_agree";
    String disagreeBtnId = "hko.MyObservatory_v1_0:id/btn_not_agree";

    public void clickAgreeBtn(AndroidDriver driver){
        driver.findElementById(agreeBtnId).click();
        super.sleep(100);
    }

    public void clickDisagreeBtn(AndroidDriver driver){
        driver.findElementById(disagreeBtnId).click();
    }
}
