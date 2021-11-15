package com.auto.testcase.web;

import com.auto.web.page.DesktopNavPage;
import com.auto.web.page.MarketsPage;
import com.auto.web.utils.WebDriverUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class TradeTest {
    JavascriptExecutor js;
    WebDriverUtil webDriverUtil;
    WebDriver driver;
    DesktopNavPage desktopNavPage;
    MarketsPage marketsPage;

    /**
     * 初始化浏览器
     */
    @BeforeTest
    public void initDriver(){
        webDriverUtil = new WebDriverUtil();
        driver = webDriverUtil.initChromeByEagger();
        driver.manage().window().maximize();
    }

    /**
     * 初始化数据
     */
    @BeforeTest
    public void initPage(){
        desktopNavPage = new DesktopNavPage();
        marketsPage = new MarketsPage();
    }

    /**
     * 从首页进入Markets-USDC页，点击CRO/USDC数据对应的按钮，进入trade页面
     */
    @Test(description = "从首页进入Markets-USDC页，点击CRO/USDC数据对应的按钮，进入trade页面",priority = 1)
    public void enterTradePage(){
        //log.info("打开首页");
        desktopNavPage.open(driver);
        desktopNavPage.sleep(500);
        //log.info("点击Markets");
        desktopNavPage.clickMarketsBtn(driver);
        //log.info("等待跳转Markets");
        desktopNavPage.waitJumpMarkets(driver,"/exchange/markets",500L);
        // 点击USDC nav
        String navName = "USDC";
        marketsPage.clickMarketsNav(driver,navName);
        // 查找Instrument:CRO/USDC 对应的行数
        String searchKey = "CRO/USDC";
        String searchKey1 = "CRO_USDC";
        Long rowResult = marketsPage.getSearchRow(driver, searchKey);
        Reporter.log(String.format("%s 对应表格行数: %s",searchKey,rowResult));
        Assert.assertNotEquals(0L,rowResult);
        marketsPage.sleep(1000);
        // 点击Instrument:CRO/USDC 对应的trade按钮
        marketsPage.clickTradeBtnByRow(driver,rowResult.intValue());
        //等待链接跳转
        //marketsPage.sleep(10000);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 100);
        webDriverWait.until(ExpectedConditions.urlContains(searchKey1));
        //Assert.assertTrue(driver.getCurrentUrl().contains(searchKey1));
    }

    @Test(description = "测试是否已点击Markets-USDC导航")
    public void enterMarketsNavPage(){
        String navName = "USDC";
        marketsPage.enterMarketsNavPage(driver,navName);
        String currentNavName = marketsPage.getCurrentNavName(driver);
        Reporter.log("当前nav:"+currentNavName);
        Assert.assertEquals(navName,currentNavName);
    }

    /**
     * 成功找到CRO/USDC数据
     * 对应的trade按钮可点击并且点击后页面跳转到交易页面
     */
    @Test(description = "成功找到CRO/USDC数据")
    public void testFindData(){
        String navName = "USDC";
        marketsPage.enterMarketsNavPage(driver,navName);
        String searchKey = "CRO/USDC";
        Long rowResult = marketsPage.getSearchRow(driver, searchKey);
        Reporter.log(String.format("%s 对应表格行数: %s",searchKey,rowResult));
        Assert.assertNotEquals(0L,rowResult);
    }


    /**
     *测试在USDC页查询不存在的Instrument，是否会提示"No Records"
     */
    @Test(description = "测试在USDC页查询不存在的Instrument，是否会提示\"No Records\"")
    public void searchNoExistKey(){
        String navName = "USDC";
        marketsPage.enterMarketsNavPage(driver,navName);
        marketsPage.scrollPage(driver,-500,0);
        marketsPage.sleep(100);
        String searchKey = "marketsPage";
        marketsPage.searchKey(driver,searchKey);
        String noDataText = marketsPage.noData(driver);
        Reporter.log(String.format("查询结果为: %s",noDataText));
        Assert.assertEquals(noDataText,"No Records");
    }

    /**
     *测试在USDC页查询存在的Instrument 关键字 USD，是否列出所有包含R的Instrument 关键字
     */
    @Test(description = "测试在USDC页查询存在的Instrument 关键字 USD，是否列出所有包含USD的Instrument 关键字")
    public void searchExistKey(){
        String navName = "USDC";
        marketsPage.enterMarketsNavPage(driver,navName);
        marketsPage.scrollPage(driver,-500,0);
        marketsPage.sleep(100);
        String searchKey = "USD";
        marketsPage.searchKey(driver,searchKey);
        List<HashMap<String, String>> rowData = marketsPage.getRowData(driver);
        rowData.forEach(result-> {
            String instrument = result.get("Instrument");
            Reporter.log(String.format("%s ,包含: %s",instrument,searchKey));
            Assert.assertTrue(instrument.contains(searchKey));

        });
    }

    /**
     * 进入Markets-USDC页，测试数据数量是否符合预设值
     */
    @Test(description = "进入Markets-USDC页，测试数据数量是否符合预设值")
    public void checkRowCount(){
        String navName = "USDC";
        Long exceptCount = 38L;
        marketsPage.enterMarketsNavPage(driver,navName);
        Long rowResult = marketsPage.getRowCount(driver);
        Reporter.log(String.format("Markets-%s ,预期数量为: %s ,实际查询出来的数量为: %s",navName,exceptCount,rowResult));
        Assert.assertEquals(exceptCount,rowResult);
    }
    /*@DataProvider(name = "InstrumentTestData")
    public Object[][] getInstrumentTestData(){
        Object[][] result = null;
        try{
            result = DDTUtil.getTestData(FileUtil.getResourcePath("test-data/")+"Instrument测试数据.xlsx", "USDC");
        }catch (Exception e){
            Reporter.log(e.getMessage());
        }
        return result;
    }*/

    /*
    页面数据实时更新，很难匹配
    @Test(description = "检查Markets-指定nav的指定Instrument数据是否正确",dataProvider = "InstrumentTestData")
    public void checkInstrumentData(String... params) throws IOException {
        //Object[][]  result = DDTUtil.getTestData(FileUtil.getResourcePath("test-data/")+"Instrument测试数据.xlsx", "USDC");
        String navName = "USDC";
        marketsPage.enterMarketsNavPage(driver,navName);
        String searchKey = params[0];
        String searchKey1 = "CRO_USDC";
        Long rowResult = marketsPage.getSearchRow(driver, searchKey);
        marketsPage.clickTradeBtnByRow(driver,rowResult.intValue());
    }*/


    @AfterTest
    public void closeDriver(){
        driver.quit();
    }
}
