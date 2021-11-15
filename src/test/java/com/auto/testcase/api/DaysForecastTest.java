package com.auto.testcase.api;

import com.auto.api.utils.HttpClientUtil;
import com.auto.common.utils.DateUtil;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * @author dxd
 */
@Slf4j
public class DaysForecastTest {
    String daysForecastUrl = "http://www.hko.gov.hk/wxinfo/json/one_json_uc.xml";

    /**
     * 测试请求是否成功
     */
    @Test
    public void access9DaysURL(){
        Boolean result = HttpClientUtil.getResponse(daysForecastUrl);
        Reporter.log(String.format("请求url: %s 响应结果为: %s",daysForecastUrl,result));
        Assert.assertTrue(result);

    }

    /**
     * 以当天为标准，获得后天的湿度并输出
     * @throws Exception
     */
    @Test
    public void getHumidity() throws Exception {
        String result = HttpClientUtil.getResult(daysForecastUrl, "");
        // 获得后天日期
        String dateStr = DateUtil.relateDateStr(new Date(), 2, DateUtil.Date_Format_1);
        // 湿度
        String humidity = getHumidity(result,dateStr);
        Reporter.log(String.format("后天 %s的Humidity: %s",dateStr,humidity));
        log.info ("后天{}的Humidity:{}",dateStr,humidity);
        // 判断湿度获取是否正确
        // Assert.assertEquals(humidity,"30 - 50%");
        //System.out.println("后天"+dateStr+"的Humidity:"+humidity);
    }

    /**
     * 获得指定日期对应的天气的湿度
     * @param resJson
     * @return
     */
    public String getHumidity(String resJson,String dateStr){
        // 湿度
        String humidity = "";
        JsonElement element = JsonParser.parseString(resJson);
        JsonObject object = element.getAsJsonObject();
        JsonObject F9D = object.get("F9D").getAsJsonObject();
        JsonArray weatherForecast = F9D.getAsJsonArray("WeatherForecast");
        for(int i=0 ;i<weatherForecast.size();i++){
            JsonObject day = weatherForecast.get(i).getAsJsonObject();
            String forecdastDate = day.get("ForecastDate").toString();
            if(forecdastDate.contains(dateStr)){
                String forecastMinrh = day.get("ForecastMinrh").toString();
                String forecastMaxrh = day.get("ForecastMaxrh").toString();
                // 过滤多余的字符，拼接
                humidity = forecastMinrh.substring(1,forecastMinrh.length()-1).concat(" - ").concat(forecastMaxrh.substring(1,forecastMinrh.length()-1)).concat("%");
                break;
            }
        }
        return humidity;
    }
}
