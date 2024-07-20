package me.xiaoying.serverbuild.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类 日期
 */
public class DateUtil {
    /**
     * 获取格式日期
     *
     * @param format 格式
     * @return String*Date
     */
    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 日期相减
     *
     * @param date 日期 1
     * @param date1 日期 2
     * @return 减后时间
     */
    public static long getDateReduce(Date date, Date date1) {
        return date.getTime() - date1.getTime();
    }


    /**
     * 日期相减
     *
     * @param date 日期 1
     * @param date1 日期 2
     * @param format 日期格式
     * @return 减后时间
     */
    public static long getDateReduce(String date, String date1, String format) {
        Date d = null;
        Date d1 = null;
        try {
            d = new SimpleDateFormat(format).parse(date);
            d1 = new SimpleDateFormat(format).parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d != null;
        assert d1 != null;
        return d.getTime() - d1.getTime();
    }
}