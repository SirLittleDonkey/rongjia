package com.wyait.manage.utils;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.round;

public class DateUtil implements AutoCloseable,Serializable {
    private static final long serialVersionUID = 5110771010886130754L;
    //饿汉单例
    public static DateUtil instance = new DateUtil();

    private DateUtil() {
    }

    public static DateUtil getInstance() {
        return instance;
    }

    //防序列化（杜绝单例对象被反序列化时重新生成对象）
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    // SimpleDateFormat线程不安全的类，使用ThreadLocal,
    // 也是将共享变量变为独享，线程独享肯定能比方法独享在并发环境中能减少不少创建对象的开销。如果对性能要求比较高的情况下，一般推荐使用这种方法。
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * @param dateStr
     * @return Date
     * @throws ParseException
     * @描述：格式化String转换为Date
     * @创建人：wyait
     * @创建时间：2017年6月7日 下午6:27:03
     */
    public static Date parse(String dateStr) throws ParseException {
        return threadLocal.get().parse(dateStr);
    }

    /**
     * @param date
     * @return 格式：yyyy-MM-dd HH:mm:ss
     * @描述：将date日期转换为string
     * @创建人：wyait
     * @创建时间：2017年6月7日 下午6:27:14
     */
    public static String format(Date date) {
        return threadLocal.get().format(date);
    }

    @Override
    public void close() throws Exception {
    }

    public static String getCurrentState(String planTime, Double planHour, Integer planQty, Integer qualiefidQty, Integer unQualiefidQty) throws ParseException {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(planTime));
            Double planMin = planHour * 60;
            Double reqQtyPerMin = planQty / planMin;
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            long mills = calendar1.getTimeInMillis() - calendar.getTimeInMillis();
            long passMin = mills / 60000;
            Double miQty = passMin * reqQtyPerMin - qualiefidQty - unQualiefidQty;
            if(miQty > 0){
                return "欠产" + round(miQty) + "个";
            }else{
                return "超产" + round(-miQty) + "个";
            }
    }
}

