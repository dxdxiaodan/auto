package com.auto.selenium.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author
 * @date
 * @desc
 */

public class DDTUtil {
    public static Object[][] getTestData(String excelFilePath,String sheetName) throws IOException {
        File file = new File(excelFilePath);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook Workbook =null;
        String fileExtensionName = excelFilePath.substring(excelFilePath.indexOf("."));
        //文件类型如果是“.xlsx”，则使用XSSFWorkbook
        //文件类型如果是".xls",则使用
        if (fileExtensionName.equals(".xlsx")) {
            Workbook = new XSSFWorkbook(inputStream);
        } else if(fileExtensionName.equals(".xls")){
            Workbook = new HSSFWorkbook(inputStream);
        }
        //通过sheetName参数，生成Sheet对象
        Sheet Sheet = Workbook.getSheet(sheetName);
        int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();
        //创建名为records的List对象来存储从excel文件读取的数据
        ArrayList<Object[]> records = new ArrayList<Object[]>();
        //使用两个for循环遍历excel文件的所有数据（除了第一行，第一行是数据列名称），所以i从1开始，而不是从0开始
        for (int i = 1; i <= rowCount; i++) {
            Row row = Sheet.getRow(i);
            if(row.getPhysicalNumberOfCells()==0){
                break;
            }
            String fields[] = new String[row.getLastCellNum()-2];
            /*
             * if用于判断数据行是否要参与测试的执行，excel文件的倒数第二列为数据行的状态位，标记为y表示要执行，非y则不参与测试脚本的执行，会跳过
             */
            if (row.getCell(row.getLastCellNum()-2).getStringCellValue().equals("y")) {
                for (int j = 0; j < row.getLastCellNum()-2; j++) {
                    //判断Excel文件的单元格字段是数字还是字符串，字符串格式调用getStringCellValue方法获取
                    //数字格式调用getNumericCellValue方法获取
                    fields[j] = (String)(row.getCell(j).getCellType()== CellType.STRING?
                            row.getCell(j).getStringCellValue():""+row.getCell(j).getNumericCellValue());
                }
                //将fields的数据对象存储到records的List中
                records.add(fields);
            }
        }
        //定义函数返回值，即Object[][]
        //将存储测试数据的List转换为一个Object的二维数组
        Object[][] results = new Object[records.size()][];
        //设置二维数据每行的值，每行是一个Object对象
        for (int i = 0; i < records.size(); i++) {
            results[i] = records.get(i);
        }
        return results;
    }

}
