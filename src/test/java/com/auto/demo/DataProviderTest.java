package com.auto.demo;

import com.auto.common.utils.DDTUtil;
import com.auto.common.utils.FileUtil;
import com.auto.common.utils.YamlReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author dxd
 */
@Slf4j
public class DataProviderTest {
    @Test
    public void paramData() throws IOException {
        Object[][] result = new Object[][]{};
        result = DDTUtil.getTestData(FileUtil.getResourcePath("test-data/")+"Instrument测试数据.xlsx", "USDC");
        log.info(result.toString());
    }
}