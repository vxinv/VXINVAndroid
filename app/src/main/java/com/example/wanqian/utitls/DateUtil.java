package com.example.wanqian.utitls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
/**
 *
 */

public class DateUtil {

    /**
     * 将日期字符串转化为需要格式的日期
     *
     * @param date   日期字符串
     * @param format 格式字符串
     * @return 转换后的日期对象
     */
    public static Date strToFormatDate(String date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date, new ParsePosition(0));
    }

    /**
     * 将字符串转换为yyyy-MM-dd格式的日期
     *
     * @param date
     * @return 转换后的日期对象
     */
    public static Date strToDate(String date) {
        return DateUtil.strToFormatDate(date, "yyyy-MM-dd");
    }

    /**
     * 将字符串转换为yyyy-MM-dd HH:mm:ss格式的日期
     *
     * @param date
     * @return 转换后的日期对象
     */
    public static Date strToDateTime(String date) {
        return DateUtil.strToFormatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将date型日期转换成特定格式的时间字符串
     *
     * @param date
     * @param format
     * @return 转换后的日期对象
     */
    public static String dateToFormatStr(Date date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date dateToFormat(Date date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dStr = sdf.format(date);
        return strToFormatDate(dStr, format);
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    /**
     * 将date型日期转换成yyyy-MM-dd HH:mm:ss格式的时间字符串
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd HH:mm格式的时间字符串
     */
    public static String dateTimeToStr(Date date) {
        return DateUtil.dateToFormatStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将date型日期转换成yyyyMMddHHmmss格式的时间字符串
     *
     * @param date 日期
     * @return 返回yyyyMMddHHmmss格式的时间字符串
     */
    public static String dateTimeToStr14(Date date) {
        return DateUtil.dateToFormatStr(date, "yyyyMMddHHmmss");
    }

    public static String dateTimeToStr13(Date date) {
        return DateUtil.dateToFormatStr(date, "MMddHHmmssSSS");
    }

    /**
     * 将date型日期转换成yyyy-MM-dd格式的日期字符串
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式的日期字符串
     */
    public static String dateToStr(Date date) {
        return DateUtil.dateToFormatStr(date, "yyyy-MM-dd");
    }

    public static String dateToStr1(Date date, String append) {
        StringBuffer dateStr = new StringBuffer();
        int yearOfDate = getYearOfDate(date);
        int monthOfDate = getMonthOfDate(date);
        int dayOfDate = getDayOfDate(date);
        dateStr.append(monthOfDate).append("月").append(dayOfDate).append("日").append(append);
        return dateStr.toString();
    }

    /**
     * 计算出date day天之前或之后的日期
     *
     * @param date {@link Date}	日期
     * @param days int				天数，正数为向后几天，负数为向前几天
     * @return 返回Date日期类型
     */
    public static Date getDateBeforeOrAfterDays(Date date, int days) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
        return now.getTime();
    }

    /**
     * 计算出date monthes月之前或之后的日期
     *
     * @param date    日期
     * @param monthes 月数，正数为向后几天，负数为向前几天
     * @return 返回Date日期类型
     */
    public static Date getDateBeforeOrAfterMonthes(Date date, int monthes) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + monthes);
        return now.getTime();
    }

    /**
     * 计算出date years年之前或之后的日期
     *
     * @param date  日期
     * @param years 年数，正数为向后几年，负数为向前几年
     * @return 返回Date日期类型
     */
    public static Date getDateBeforeOrAfterYears(Date date, int years) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + years);
        return now.getTime();
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param beginDate
     * @param endDate
     * @return 如果beginDate 在 endDate之后返回负数 ，反之返回正数
     */
    public static int daysOfTwoDate(Date beginDate, Date endDate) {

        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        beginCalendar.setTime(beginDate);
        endCalendar.setTime(endDate);

        return daysOfTwoDate(beginCalendar, endCalendar);

    }

    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     */
    public static String getDatePoor(long endDate, long nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate - nowDate;
        // 计算差多少天
        long day = diff / nd;

        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        if (day==0){
            if (hour==0){
                if (min==0){
                    return  sec + "秒";
                }else{
                    return  min + "分钟" + sec + "秒";
                }

            }else{
                return hour + "小时" + min + "分钟" + sec + "秒";
            }
        }else{
            return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
        }

    }

    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }

    public static int daysOfTwoDate(String beginDate, String endDate) {
        return daysOfTwoDate(strToDate(beginDate), strToDate(endDate));
    }

    public static int daysOfTwoDateTime(String beginDate, String endDate) {
        return daysOfTwoDate(strToDateTime(beginDate), strToDateTime(endDate));
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param d1
     * @param d2
     * @return 如果d1 在 d2 之后返回负数 ，反之返回正数
     */
    public static int daysOfTwoDate(Calendar d1, Calendar d2) {
        int days = 0;
        int years = d1.get(Calendar.YEAR) - d2.get(Calendar.YEAR);
        if (years == 0) {//同一年中
            days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
            return days;
        } else if (years > 0) {//不同一年
            for (int i = 0; i < years; i++) {
                d2.add(Calendar.YEAR, 1);
                days += -d2.getActualMaximum(Calendar.DAY_OF_YEAR);
                if (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
                    days += d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
                    return days;
                }
            }
        } else
            for (int i = 0; i < -years; i++) {
                d1.add(Calendar.YEAR, 1);
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                if (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
                    days += d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
                    return days;
                }
            }
        return days;
    }

    public static Date parseTimeMillion(long times) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(times);
        return format.parse(d);
    }

    /**
     * 获得当前时间当天的开始时间，即当前给出的时间那一天的00:00:00的时间
     *
     * @param date 当前给出的时间
     * @return 当前给出的时间那一天的00:00:00的时间的日期对象
     */
    public static Date getDateBegin(Date date) {
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null) {
            try {
                return DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.CHINA).parse(ymdFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 获得当前时间当天的结束时间，即当前给出的时间那一天的23:59:59的时间
     *
     * @param date 当前给出的时间
     * @return 当前给出的时间那一天的23:59:59的时间的日期对象
     */
    public static Date getDateEnd(Date date) {
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null) {
            try {
                date = getDateBeforeOrAfterDays(date, 1);
                date = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.CHINA).parse(ymdFormat.format(date));
                Date endDate = new Date();
                endDate.setTime(date.getTime() - 1000);
                return endDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getYearOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonthOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Date formatDate(Date curDate, String format) {
        String dateStr = dateToFormatStr(curDate, format);
        return strToFormatDate(dateStr, format);
    }

    public static Date getFullDate(String monthDay) {
        Date date = new Date();
        int year = getYearOfDate(date);
        int month = getMonthOfDate(date);

        String[] md = monthDay.split("-");
        if (month > Integer.valueOf(md[0])) {
            year += 1;
        }
        String d = year + "-" + monthDay;
        return strToDate(d);
    }

    /**
     * 获取前一个月开始时间和结束时间
     *
     * @param subDate 往前几个月
     * @param flag    true开始，false结束
     * @return
     */
    public static Date getLashMonth(int subDate, boolean flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = new Date();
        if (flag) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - subDate);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            result = DateUtil.getDateBegin(cal.getTime());
        } else {
            Calendar cal = Calendar.getInstance();
            if (subDate != 0) {
                cal.add(Calendar.MONTH, -(subDate - 1));
                cal.set(Calendar.DAY_OF_MONTH, 0);
            } else {
                cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            }
            result = DateUtil.getDateEnd(cal.getTime());
        }
        try {
            result = sdf.parse(sdf.format(result));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 取得与原日期相差一定天数的日期，返回Date型日期
     *
     * @param date       原日期
     * @param intBetween 相差的天数
     * @return date加上intBetween天后的日期
     */
    public static Date getDateBetween(Date date, int intBetween) {
        Calendar calo = Calendar.getInstance();
        calo.setTime(date);
        calo.add(Calendar.DATE, intBetween);
        return calo.getTime();
    }

    /**
     * 判断是否为月初
     *
     * @return
     */
    public static boolean isMonthFirst() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(format.format(new Date())).compareTo(getLashMonth(0, true)) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断是否为月底
     *
     * @return
     */
    public static boolean isMonthLast() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(format.format(new Date())).compareTo(getLashMonth(0, false)) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断是否为当月第一天
     *
     * @return
     */
    public static boolean isLastDay() {
        Calendar c = Calendar.getInstance();
        int today = c.get(Calendar.DAY_OF_MONTH);
        // 然后判断是不是本月的第一天
        if (today == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当月第一天
     *
     * @return
     */
    public static Date getFirstDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /**
     * 当月最后一天
     *
     * @return
     */
    public static Date getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        Date result = new Date();
        try {
            result = df.parse(df.format(theDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取昨天凌晨0点0分0秒Date
     *
     * @return
     */
    public static Date geyBeginYesterday() {
        Calendar c = Calendar.getInstance();
        //System.out.println(Calendar.DATE);//5
        c.add(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);

        Date time = c.getTime();
        return time;
    }

    /**
     * 获取当天凌晨0点0分0秒Date
     *
     * @return
     */
    public static Date getDayBeginOfDate() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date beginOfDate = calendar1.getTime();
        return beginOfDate;
    }

    public static Date getDayBeginOfDate(String time) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date(Long.valueOf(time)));
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date beginOfDate = calendar1.getTime();
        return beginOfDate;
    }

    /**
     * 获取当天23点59分59秒Date
     *
     * @return
     */
    public static Date getDayEndOfDate() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date endOfDate = calendar1.getTime();
        return endOfDate;
    }

    /**
     * 获取明天的凌晨0点0分0秒Date
     *
     * @return
     */
    public static Date getTomorrowBeginOfDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取当月1号的凌晨0点0分0秒Date
     *
     * @return
     */
    public static Date getMounthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTime();
    }

    /**
     * 获取下一个月的第一天凌晨0点0分0秒Date
     *
     * @return
     */
    public static Date getPerFirstDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static boolean isYearFirst() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(format.format(new Date())).compareTo(getFirstDay(true)) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isYearEnd() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(format.format(new Date())).compareTo(getEndDay(true)) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * flag 为true 按年   反之按月
     *
     * @param flag
     * @return
     */
    public static Date getFirstDay(boolean flag) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        if (flag) {
            ca.set(Calendar.DAY_OF_YEAR, 1);
        } else {
            ca.set(Calendar.DAY_OF_MONTH, 1);
        }
        try {
            return format.parse(format.format(ca.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * flag 为true 按年   反之按月
     *
     * @param flag
     * @return
     */
    public static Date getEndDay(boolean flag) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        if (flag) {
            ca.set(Calendar.DAY_OF_YEAR, 1);
        } else {
            ca.set(Calendar.DAY_OF_MONTH, 1);
        }
        ca.add(Calendar.MONTH, 12);
        if (flag) {
            ca.add(Calendar.DAY_OF_YEAR, -1);
        } else {
            ca.add(Calendar.DAY_OF_MONTH, -1);
        }
        try {
            return format.parse(format.format(ca.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 获取day天的日期   正数向后数day天的日期，负数向前数
     *
     * @param day
     */
    public static Date getDateNumDay(int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());                            //  someDate 为你要获取的那个月的时间
        ca.set(Calendar.DAY_OF_MONTH, day);
        try {
            return format.parse(format.format(ca.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 当前时间 往后数 day天的时间
     *
     * @param day
     */
    public static Date getDateNumDay(String startTime, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date(Long.valueOf(startTime)));
        ca.add(Calendar.DAY_OF_MONTH, day);
        try {
            return format.parse(format.format(ca.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 判断date 是否在startDate和endDate 之间
     *
     * @param date
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean timeInterval(Date date, Date startDate, Date endDate) {
        if (date.getTime() >= startDate.getTime() && date.getTime() < endDate.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 获取今天还剩下多少秒
     *
     * @return
     */
    public static int getMiao() {
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int) (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }


    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
        }
        if (inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断当前是否是零点
     *
     * @return
     */
    public static boolean isZero() {
        Calendar c = Calendar.getInstance();
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        if (hh == 0 && mm == 0 && ss == 0) {
            return true;
        } else {
            return false;
        }
    }




    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            times = String.valueOf(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//得到当前的时间
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "yyyy-MM-dd HH:mm:ss" "yyyy-MM-dd" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean IsSameday(long preDay,long afterDay) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(preDay);
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = new Date(afterDay);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }


    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    /**
     * 是否在时间内
     *
     * @param currentTime 要判断的时间
     * @param startTime   起始时间
     * @param day         往后数几天
     * @return
     */
    public static boolean isStartToDays(String currentTime, String startTime, int day) {
        Date endTime = getDateNumDay(startTime, day);
        boolean b = timeInterval(new Date(Long.valueOf(currentTime)), getDayBeginOfDate(startTime), endTime);
        return b;
    }


    public static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }


}
