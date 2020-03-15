package com.graduateassignment.Util;

import java.util.Date;

/**
 * Created by admin on 2020/3/9.
 */

public class DateUtil {

    /**
     * 返回聚合数据中"yyyy-mm-dd hh:mm"格式日期的年份，月份，日期，时，分
     * @param date
     * @return
     */
    public static String getYearFromJuheDate(String date){
        return date.substring(0,4);
    }
    public static String getMonthFromJuheDate(String date){
        return date.substring(5,7);
    }
    public static String getDayFromJuheDate(String date){
        return date.substring(8,10);
    }
    public static String getHourFromJuheDate(String date){
        return date.substring(11,13);
    }
    public static String getMinuteFromJuheDate(String date){
        return date.substring(14,16);
    }

    public static Date getDateFromJuheDate(String date){
        int year = Integer.parseInt(getYearFromJuheDate(date))-1900;
        int month = Integer.parseInt(getMonthFromJuheDate(date))-1;
        int day = Integer.parseInt(getDayFromJuheDate(date));
        int hour = Integer.parseInt(getHourFromJuheDate(date));
        int minute = Integer.parseInt(getMinuteFromJuheDate(date));
        Date craeteDate = new Date(year,month,day,hour,minute);
        return craeteDate;
    }
}
