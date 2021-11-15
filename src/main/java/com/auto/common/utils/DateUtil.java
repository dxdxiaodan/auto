package com.auto.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *时间操作相关类
 *@author dxd
 *
 * */
public class DateUtil {
    public static final String Date_Format_1 = "yyyyMMdd";

    // 时间格式化集，线程安全
    private static Map<String,ThreadLocal<SimpleDateFormat>>  formatMap = new HashMap<String,ThreadLocal<SimpleDateFormat>>();

    /**
     * 通过日期格式获得SimpleDateFormat对象，若对象不存在则生成
     * @param pattern 日期格式
     * @return
     */
    public static SimpleDateFormat getFormat(String pattern){
        ThreadLocal<SimpleDateFormat> simpleDateFormat = formatMap.get(pattern);
        if(null == simpleDateFormat){
            simpleDateFormat = new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(pattern);
                }
            };
            formatMap.put(pattern,simpleDateFormat);
        }
        return simpleDateFormat.get();
    }

    /**
     * 根据日期格式将日期对象转换成字符串
     * @param date 日期对象
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String getDateStr(Date date, String pattern){
        return getFormat(pattern).format(date);
    }

    /**
     * 根据日期格式将日期字符串转换成日期对象
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return 日期对象
     */
    public static Date getDate(String date, String pattern) throws ParseException {
        return getFormat(pattern).parse(date);
    }

    /**
     * 获得前后几天的日期
     * @param date 日期
     * @param day 后几天：正数，前几天，负数
     * @param pattern 格式
     * @return
     */
    public static String relateDateStr(Date date,int day,String pattern){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,day);
        Date time = calendar.getTime();
        return getDateStr(time,pattern);
    }
}
