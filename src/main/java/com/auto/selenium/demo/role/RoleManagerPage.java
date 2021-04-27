package com.auto.selenium.demo.role;

import com.auto.selenium.demo.login.BasicPage;
import com.auto.selenium.model.AuthModel;
import com.auto.selenium.model.AuthTree;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author
 * @date
 * @desc
 */

@Component
public class RoleManagerPage extends BasicPage {
    @FindBy(id="param")
    private WebElement searchRoleInput;
    @FindBy(xpath = "//input[@value='搜索']")
    private WebElement searchBtn;
    @FindBy(xpath = "//span[contains(text(),dxd权限测试) and contains(@class,'adminspancedarhdrole')]/../../td/a[text()='编辑权限']")
    private WebElement editAuthLink;
    @FindBy(xpath = "//td/input[@class='first']")
    private List<WebElement> firstRoleCheckboxs;
    @FindBy(xpath = "//a[contains(text(),'保存并返回')]")
    private WebElement saveBtn;
    @FindBy(xpath = "//td[text()='系统管理']/input[@class='first']")
    private WebElement sysManageInput;

    /**
     * 全选系统管理一级子项并保存
     * @param driver
     * @param firstAuth
     */
    public String choseeAllAuthByFirst(WebDriver driver, String firstAuth){
        driver.get(getPreUrl()+"/sys/rolemanage/role-manage!toShowRoleEdit.action?authId=" + firstAuth);
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList<String>(windowHandles);
        driver.switchTo().window(windowHandlesList.get(windowHandles.size()-1));
        RoleManagerPage roleManagerPage = PageFactory.initElements(driver,RoleManagerPage.class);
        String data1 = roleManagerPage.sysManageInput.getAttribute("data1");
        List<WebElement> sysChilds = driver.findElements(By.xpath("//input[@class='second' and @data2='910']"));
        for(WebElement child : sysChilds){
            if(!child.isSelected()){
                child.click();
            }
        }
        return saveAndResult(driver,roleManagerPage);

    }

    /**
     * 不选系统管理一级子项并保存
     * @param driver
     * @param firstAuth
     * @return
     */
    public String dischoseeAllAuthByFirst(WebDriver driver, String firstAuth){
        driver.get(getPreUrl()+"/sys/rolemanage/role-manage!toShowRoleEdit.action?authId=" + firstAuth);
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList<String>(windowHandles);
        driver.switchTo().window(windowHandlesList.get(windowHandles.size()-1));
        RoleManagerPage roleManagerPage = PageFactory.initElements(driver,RoleManagerPage.class);
        String data1 = roleManagerPage.sysManageInput.getAttribute("data1");
        if(roleManagerPage.sysManageInput.isSelected()){
            roleManagerPage.sysManageInput.click();
        }
        return saveAndResult(driver,roleManagerPage);
    }

    /**
     * 点击保存按钮并返回弹窗文本
     * @param driver
     * @param roleManagerPage
     * @return
     */
    private String saveAndResult(WebDriver driver,RoleManagerPage roleManagerPage){
        roleManagerPage.saveBtn.click();
        Alert result = driver.switchTo().alert();
        String msg = result.getText();
        result.accept();
        return  msg;
    }

    /**
     * 修改指定权限id的权限
     * @param driver
     * @param model
     * @return
     */
    public String saveAuth(WebDriver driver, AuthModel model){
        driver.get(getPreUrl()+"/sys/rolemanage/role-manage!toShowRoleEdit.action?authId=" + model.getAuthId());
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList<String>(windowHandles);
        driver.switchTo().window(windowHandlesList.get(windowHandles.size()-1));
        RoleManagerPage roleManagerPage = PageFactory.initElements(driver,RoleManagerPage.class);
        if(null != model.getAuthList() && model.getAuthList().size() > 0){
            model.getAuthList().stream().forEach(parent ->{
                WebElement pageParent = driver.findElement(By.xpath("//td[text()='" + parent.getMenuName() + "']/input[@class='first']"));
                String data1 = pageParent.getAttribute("data1");
                // 一级子项都不选
                List<WebElement> pageChilds = driver.findElements(By.xpath("//input[@class='second' and @data2='" + data1 + "']"));
                if(pageChilds.size() > 0){
                    pageChilds.stream().forEach(child ->{
                        if(child.isSelected()){
                            child.click();
                        }
                    });
                }
                List<AuthTree> childs = parent.getChild();
                if(childs.size() > 0){
                    childs.stream().forEach(child ->{
                        WebElement element = driver.findElement(By.xpath("//td[contains(text(), '" + child.getMenuName() + "')]/input[@class='second' and @data2='" + data1 + "']"));
                        element.click();
                    });
                }

            });
        }
        return saveAndResult(driver,roleManagerPage);
    }

}
