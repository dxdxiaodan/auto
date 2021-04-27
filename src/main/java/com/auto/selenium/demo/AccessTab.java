package com.auto.selenium.demo;

import com.auto.selenium.demo.login.BasicPage;
import com.auto.selenium.demo.login.HandleLoginPage;
import com.auto.selenium.model.AuthModel;
import com.auto.selenium.utils.WebDriverUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author
 * @date
 * @desc
 */
@Slf4j
@Component
public class AccessTab  extends BasicPage {
    @FindBy(xpath = "//td[text()='系统管理']/input[@class='first']")
    private WebElement sysManageInput;

    @FindBy(xpath = "//a[contains(text(),'系统管理')]/../ul/li")
    private List<WebElement> sysManageChild;

    @FindBy(id="userSet")
    WebElement userInfoLogo;

    @FindBy(id="logout")
    WebElement logoutLink;

    public static final String WORK_URL = "/home/work!toTenderWork.action";


    /**
     * 获得页面系统管理子项数
     * @param driver
     * @return
     */
    public int getSysManageChildNum(WebDriver driver){
        driver.get(getPreUrl()+WORK_URL);
        AccessTab accessTab = PageFactory.initElements(driver, AccessTab.class);
        return accessTab.sysManageChild.size();
    }

    public void handlerLogout(WebDriver driver){
        driver.manage().window().maximize();
        AccessTab accessTab = PageFactory.initElements(driver, AccessTab.class);
        Actions action = new Actions(driver);
        action.moveToElement(accessTab.userInfoLogo).perform();
        //action.moveToElement(accessTab.logoutLink).perform();
        accessTab.logoutLink.click();
    }

    /**
     * 检验用户是否有权限访问url，有权限返回true，无权限返回false
     * @param driver
     * @param url
     * @param title
     * @return
     */
    public boolean checkAccessAuth(WebDriver driver, String url,String title){
        driver.get(getPreUrl()+url);
        try{
            driver.findElements(By.xpath("//div[contains(@class,'templatemo-content')]/div/h2[text()='"+title+"']"));
            return true;
        }catch (NoSuchElementException e){
            log.info("[{}]:标题[{}]页不存在",url,title);
            return false;
        }
    }

    /**
     * 检查无权限用户是否无权限访问url页面
     * @param driver
     * @param url
     * @param expert
     * @return
     */
    public boolean checkUnaccessAuth(WebDriver driver, String url,String expert){
        driver.get(getPreUrl()+url);
        try{
            WebElement element = driver.findElement(By.xpath("//div[@class='templatemo-content-container']/div/div/h1[contains(text(),'" + expert + "')]"));
            return true;
        }catch (NoSuchElementException e){
            log.info("[{}]:不符合页面包含【{}】要求",url,expert);
            return false;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 点击系统管理的二级tab，查看是否有权限
     * @param driver
     * @return
     */
    public int accessSysNotice(WebDriver driver){
        ((ChromeDriver) driver).executeScript("$('.templatemo-sidebar').find('a[href*=noticeList]').find('i').click();", "");
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList(windowHandles);
        driver.switchTo().window((String)windowHandlesList.get(windowHandlesList.size()-1));

        /*
        System.out.println(driver.getCurrentUrl());
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//div[contains(@class,'templatemo-content')]/div/h2[text()='业务通知']"))));*/
        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class,'templatemo-content-container')]/div/h2[text()='业务通知']"));
        return elements.size();
    }

    /**
     * 修改角色权限
     * @param driver
     * @return
     */
    public int checkRoleAuth(WebDriver driver){
        driver.findElement(By.xpath("//a[@id='M010000000']")).click();
        driver.findElement(By.xpath("//li[@class='parent_li']/ul/")).click();
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList(windowHandles);
        driver.switchTo().window((String)windowHandlesList.get(windowHandlesList.size()-1));
        driver.get(getPreUrl()+"/sys/rolemanage/role-manage!toShowRoleEdit.action?authId=cedarhdrole895080576451616768");
        WebElement sysManagerBtn = driver.findElement(By.xpath("//td[text()='系统管理']/input[@class='first']"));
        String childData2 = sysManagerBtn.getAttribute("data2");
        List<WebElement> childAuths = driver.findElements(By.xpath("//td/input[@class='second'][@data2='" + childData2 + "']"));
        for(WebElement childAuth : childAuths){
            if(!childAuth.isSelected()){
                childAuth.click();
            }
        }

        /*if(sysManagerBtn.isSelected()){
            sysManagerBtn.click();
        }*/

        /*
        System.out.println(driver.getCurrentUrl());
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//div[contains(@class,'templatemo-content')]/div/h2[text()='业务通知']"))));*/
        return 1;
    }
}
