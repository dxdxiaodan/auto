package com.auto.web.page;

import com.auto.common.utils.ElementTypeKeyUtil;
import com.auto.common.utils.YamlReaderUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Markets页面相关操作
 * @author dxd
 */

@Slf4j
public class MarketsPage extends BasePage{
    String MARKETS_NAVXPATH_PRE = "//div[contains(text(),'";
    String MARKETS_NAVXPATH_POS = "') and contains(@class,'e-tabs__nav-item')]";
    String searchBtn = "div.market-title-box.markets-container > div.search-box > input";
    String url = "https://crypto.com/exchange/markets";

    @Override
    public void open(WebDriver driver){
        super.openPage(driver,url);
    }

    public WebElement getNavElement(WebDriver driver,String navName) {
        String navXpath = MARKETS_NAVXPATH_PRE + navName + MARKETS_NAVXPATH_POS;
        return super.getElement(driver,ElementTypeKeyUtil.XPATH, navXpath);
    }

    /**
     * 从Markets页面访问指定Markets-nav页面
     * @param driver
     * @param navName eg:CRO、USDC
     */
    public void enterMarketsNavPage(WebDriver driver,String navName){
        open(driver);
        clickMarketsNav(driver,navName);
    }

    /**
     * 获得markets当前选中的nav
     * @param driver
     * @return
     */
    public String getCurrentNavName(WebDriver driver){
        String js = "var currentNav=\"\";$(\".e-tabs__nav-container\").find(\".e-tabs__nav-item.active\").each(function(){currentNav =$(this).text();}); return currentNav;";
        return (String)super.executeJS(driver,js);

    }

    /**
     * 点击指定navName的Markets nav
     * @param driver
     * @param navName
     * @throws JavascriptException
     */
    public void clickMarketsNav(WebDriver driver,String navName) throws JavascriptException{
        boolean retry = true;
        // js模拟点击，失败则重试，重试
        int retryTime = (int)YamlReaderUtil.instance.getValueByKey("retry.time");
        int sleepTime = (int)YamlReaderUtil.instance.getValueByKey("retry.sleep");
        String navXpath = MARKETS_NAVXPATH_PRE + navName + MARKETS_NAVXPATH_POS;
        String text = "document.evaluate(\""+navXpath+"\", document).iterateNext().click();";

        //js失败则重试，每次重试等待sleepTime ms，最多重试retryTime次
        while (retryTime-- > 0 && retry){
            try{
                super.executeJS(driver,text);
                retry = false;
            }catch (JavascriptException e){
                // JavascriptException:js文件未加载完成
                log.info(e.getMessage());
                retry = true;
                super.sleep(sleepTime);
            }
        }
        if(retry){throw new JavascriptException("click"+navName+"Nav-error");}
    }

    /**
     * 点击指定行数的trade按钮
     * @param driver
     * @param row 行数
     */
    public void clickTradeBtnByRow(WebDriver driver,int row){
        String btnTradeCss = ".tbody__row:nth-child("+row+") .btn-trade";
        Boolean scroll = true;
        while(scroll){
            try{
                WebElement tradeBtn = super.getElement(driver, ElementTypeKeyUtil.CSS_SELECTOR,btnTradeCss);
                new Actions(driver).moveToElement(tradeBtn).click().perform();
                scroll = false;
            }catch (MoveTargetOutOfBoundsException e){
                scroll = true;
                if(!super.scrollEnd(driver)){
                    super.scrollPage(driver,0,400);
                    super.sleep(2000);
                }else{break;}

            }
        }

    }
    /**
     * 获得Markets指定交易类型的行数
     * @param driver
     * @param searchKey
     * @return
     */
    public Long getSearchRow(WebDriver driver, String searchKey){
        String jq = "    var parent;var row = 0;var index = 0;\n" +
                    "    $('.tbody__row').find('.source').each(\n" +
                    "            function(){var s = $(this).next().text();var t = $(this).text();index++;\n" +
                    "            if(t+s == '"+searchKey+"') {row = index;return false;}\n" +
                    "            });\n" +
                    "    return row.valueOf();";
        Long result = (Long)super.executeJS(driver,jq);
        return result;
    }

    public void searchKey(WebDriver driver, String searchKey){
        WebElement element = super.getElement(driver, ElementTypeKeyUtil.CSS_SELECTOR, searchBtn);
        element.sendKeys(searchKey);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * 获得查询结果为空的提示文本
     * @param driver
     * @return
     */
    public String noData(WebDriver driver){
        String css = ".table__body .no-data-row>td";
        try{
            WebElement element = super.getElement(driver, ElementTypeKeyUtil.CSS_SELECTOR, css);
            return element.getText();
        }catch (NoSuchElementException e){
            Reporter.log("报错："+e.getMessage());
            return "";
        }
    }

    /**
     * 获得结果集
     * @param driver
     * @return
     */
    public List<HashMap<String,String>> getRowData(WebDriver driver){
        String js = "var result = []; $(\".table__body\").find(\".tbody__row\").each(function (){ \n" +
                "var data = {};\n" +
                "data[\"source\"] = ($(this).find(\"td:nth-child(1) .source\").text()); \n" +
                "data[\"target\"] = ($(this).find(\"td:nth-child(1) .target\").text());\n" +
                "data[\"lastPrice\"] = ($(this).find(\"td:nth-child(2)\").text());\n" +
                "result.push(data);}); return JSON.stringify(result);";
        String json = (String)super.executeJS(driver, js);
        JsonElement element = JsonParser.parseString(json);
        JsonArray jsonArray = element.getAsJsonArray();
        List list = new ArrayList(jsonArray.size());
        for(int i=0;i<jsonArray.size();i++){
            JsonObject ele = jsonArray.get(i).getAsJsonObject();
            String source = ele.get("source").toString();
            String target = ele.get("target").toString();
            String lastPrice = ele.get("lastPrice").toString();
            HashMap map = new HashMap<String,String>();
            map.put("Instrument",source.substring(1,source.length()-1) + target.substring(1,target.length()-1));
            map.put("lastPrice",lastPrice.substring(1,lastPrice.length()-1));
            list.add(map);
        }
        return list;
    }

    /**
     * 获得表格数据量
     * @param driver
     * @return
     */
    public Long getRowCount(WebDriver driver){
        String jq = "return $(\".table__body\").find(\".tbody__row\").length";
        Long result = (Long)super.executeJS(driver,jq);
        return result;
    }
}
