package model.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateUtil {
	//JDK中的日期格式（年-月-日） 
	
		static String regx = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";//科学计数法正则表达式
	    static Pattern pattern = Pattern.compile(regx);
	    public static boolean isENum(String input){//判断输入字符串是否为科学计数法
	        return pattern.matcher(input).matches();
	    }
	
	    public final static String jdkDateFormat = "yyyy-MM-dd"; 

		 //JDK中的日期时间格式（年-月-日 时:分:秒） 
		 public final static String jdkDateTimeFormat = "yyyy-MM-dd HH:mm:ss"; 

		static public Date parseDate(String s) throws ParseException {
			SimpleDateFormat format = new SimpleDateFormat(jdkDateFormat);
			return format.parse(s);
		}
		static public Date parseDateHms(String s) throws ParseException {
			SimpleDateFormat format = new SimpleDateFormat(jdkDateTimeFormat);
			return format.parse(s);
		}
		public  static String  get_datetime()
		{
			String datetimestr;
			
			SimpleDateFormat df = new SimpleDateFormat(jdkDateTimeFormat);//设置日期格式
			datetimestr = df.format(new Date());
			
			return datetimestr;
			}	
		public  static String  get_date()
		{
			String datetimestr;
			
			SimpleDateFormat df = new SimpleDateFormat(jdkDateFormat);//设置日期格式
			datetimestr = df.format(new Date());
			
			return datetimestr;
			}	
		public  static String  get_datetime_ms()
		{
			String msg=""; 
			Date date = new Date(); 
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss.SSS"); 
			msg =sdf.format(date);
			return msg;
		}
	public static String TimeStamp2Date(String timestampString, String formats){
		
		if(isENum(timestampString)){
			BigDecimal bd = new BigDecimal(timestampString);     
			timestampString=bd.toPlainString();
		}
		Long timestamp = Long.parseLong(timestampString)*1000;    
		String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));    
		 
		return date;    
	}  
	
	public static String formatDate(String str){
		   SimpleDateFormat sf1 = new SimpleDateFormat(jdkDateFormat);
		      SimpleDateFormat sf2 =new SimpleDateFormat(jdkDateFormat);
		      String sfstr = "";
		      try {
		       sfstr = sf2.format(sf1.parse(str));
		      } catch (ParseException e) {
				    
				    e.printStackTrace();
				    return null;
				   }
		   return sfstr;
		  }
	
	 //字符串转换为日期 
	 public static Date strToDate(String strDate){ 
		 SimpleDateFormat dateFormat = new SimpleDateFormat(jdkDateFormat); 
		 Date date = null; 
		 
		 try{ 
			 date = dateFormat.parse(strDate); 
		 }catch(ParseException e){ 
		 //System.out.println(e.getMessage()); 
		 } 
		 return date; 
	 } 

	 //字符串转换为日期时间 
	public static Date strToDateTime(String strDateTime){ 
		 SimpleDateFormat dateTimeFormat = new SimpleDateFormat(jdkDateTimeFormat); 
		 Date dateTime = null; 
		 
		 try{ 
			 dateTime = dateTimeFormat.parse(strDateTime); 
		 }catch(ParseException e){ 
		 //System.out.println(e.getMessage()); 
		 } 
		 return dateTime; 
	 } 

	 //日期转换为字符串 
	 public static String dateToStr(Date date){ 
		 SimpleDateFormat dateFormat = new SimpleDateFormat(jdkDateFormat); 
		 String strDate = dateFormat.format(date); 
	 return strDate; 
	 } 

	 //日期时间转换为字符串 
	 public static String dateTimeToStr(Date date){ 
		 SimpleDateFormat dateTimeFormat = new SimpleDateFormat(jdkDateTimeFormat); 
		 String strDateTime = dateTimeFormat.format(date); 
		 
		 return strDateTime; 
	 } 
	
	 public static Date getDateAfter(Date d, int day) {  
	        Calendar now = Calendar.getInstance();  
	        now.setTime(d);  
	        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
	        return now.getTime();  
	    }
	 /**  
	      * 计算两个日期之间相差的天数  
	      * @param smdate 较小的时间 
	      * @param bdate  较大的时间 
	      * @return 相差天数 
	      * @throws ParseException  
	    */    
	    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
	    {    
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    }    
	    /**  
	      * 计算两个日期时间之间相差的秒数  
	      * @param smdate 较小的时间 
	      * @param bdate  较大的时间 
	      * @return 相差秒数 
	      * @throws ParseException  
	    */    
	    public static int secondsBetween(Date smdate,Date bdate) throws ParseException    
	    {    
	        SimpleDateFormat sdf=new SimpleDateFormat(jdkDateTimeFormat);  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_seconds=(time2-time1)/(1000);  
	            
	       return Integer.parseInt(String.valueOf(between_seconds));           
	    }    

	    /**
	    * 格式化日期
	    * 
	    * @param sdate
	    *            日期字符串
	    * @param format
	    *            要格式化的日期格式
	    * @return 格式化后的日期字符串
	    */

    public static java.lang.String format(String sdate, java.lang.String format) {

		    Date date = new Date();
		    SimpleDateFormat df = new SimpleDateFormat(format);
	
		    try {
			    	date = df.parse(sdate);
		    	
		    	} catch (ParseException ex) {
		    		ex.printStackTrace();
		    	}
	
		    return df.format(date);
	    }

	    /**
	    * 一个日期是否是指定的日期格式
	    * 
	    * @param dateStr
	    *            日期字符串
	    * @param pattern
	    *            验证的日期格式
	    * @return 是否是指定的日期格式
	    */

	    public static boolean isValidDate(String dateStr, String pattern) {

	    	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(pattern);

		    try {
				    df.setLenient(false);
				    df.parse(dateStr);
	
				    return true;
		    
		    } catch (ParseException e) {
		    	return false;
		    }

	    }
	    public static boolean isValidDate(String inDate) {

	        if (inDate == null)
	          return false;

	        //set the format to use as a constructor argument
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        if (inDate.trim().length() != dateFormat.toPattern().length())
	          return false;

	        dateFormat.setLenient(false);

	        try {
	          //parse the inDate parameter
	          dateFormat.parse(inDate.trim());
	        }
	        catch (ParseException pe) {
	          return false;
	        }
	        return true;
	      }


	    /**
	    * 将字符串按指定的格式转换为日期类型
	    * 
	    * @param str
	    *            日期字符串
	    * @param format
	    *            指定格式
	    * @return 格式化后的日期对象
	    */

	    public static Date strToDate(String str, String format) {

	    	SimpleDateFormat dtFormat = null;

		    try {
				    dtFormat = new SimpleDateFormat(format);
			
				    return dtFormat.parse(str);
			
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
				return null;
		    }

	    /**
	    * 对一个日期进行偏移
	    * 
	    * @param date
	    *            日期
	    * @param offset
	    *            偏移两
	    * @return 偏移后的日期
	    */

	    public static Date addDayByDate(Date date, int offset) {
	    	
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTime(date);

		    int day = cal.get(Calendar.DAY_OF_YEAR);
		    cal.set(Calendar.DAY_OF_YEAR, day + offset);
	
		    return cal.getTime();

	    }

	    /**
	    * 将日期格式化为<字符串类型>
	    * 
	    * @param 要格式化的日期
	    * @param dateFormat
	    *            日期格式
	    * @return 当前日期<字符串类型>
	    */

	    public static String dateToStr(Date date, String dateFormat) {

	    		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

	    		return sdf.format(date);

	    }

	    /**
	    * 得到当前日期<字符串类型>
	    * 
	    * @param dateFormat
	    *            日期格式
	    * @return 当前日期<字符串类型>
	    */

	    public static String getCurrDate(String dateFormat) {

		    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	
		    return sdf.format(new Date());

	    }

	    /**
	    * 得到当前日期<java.util.Date类型>
	    * 
	    * @param dateFormat
	    *            日期格式
	    * @return 当前日期<java.util.Date类型>
	    */

	    public static Date getCurrentDate(String dateFormat) {

	    	return strToDate(getCurrDate(dateFormat), dateFormat);

	    }

	    /**
	    * 将一个日期转换为指定格式的日期类型
	    * 
	    * @param date
	    *            要转换的日期
	    * @param dateFormat
	    *            日期格式
	    * @return 转换后的日期对象
	    */

	    public static Date formatDate(Date date, String dateFormat) {

		    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	
		    return strToDate(sdf.format(date), dateFormat);

	    }

	    /**
	    * 对格式为20080101类型的字符串进行日期格式化
	    * 
	    * @param dateStr
	    *            要格式化的字符串
	    * @param formatChar
	    *            连接字符
	    * @param dateFormat
	    *            日期格式
	    * @return 格式后的日期字符串
	    */

	    public static String format(String dateStr, String formatChar, String dateFormat) {

	    	try {
				    dateStr = dateStr.substring(0, 4) + formatChar
						    + dateStr.substring(4, 6) + formatChar
						    + dateStr.substring(6, 8);
			
				    return format(dateStr, dateFormat);
			    
			    } catch (Exception e) {
			    		return null;
			    }
	    }

	    /**
	    * 对格式为20080101类型的字符串进行日期格式化
	    * 
	    * @param dateStr
	    *            要格式化的字符串
	    * @param formatChar
	    *            连接字符
	    * @param dateFormat
	    *            日期格式
	    * @return 格式后的日期对象
	    */

	    public static Date formatDate(String dateStr, String formatChar,String dateFormat) {

		    try {
	
			    dateStr = dateStr.substring(0, 4) + formatChar
					    + dateStr.substring(4, 6) + formatChar
					    + dateStr.substring(6, 8);
		
			    return strToDate(dateStr, dateFormat);
	
			    } catch (Exception e) {
			    return null;
			  }
		 }

	    /**
	    * 获得某一个月份的第一天
	    * 
	    * @param date
	    * @return
	    */

	    @SuppressWarnings("static-access")

	    public static Date getFirstDayByMonth(Date date) {

			    GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
			    gc.setTime(date);
			    gc.set(Calendar.DAY_OF_MONTH, 1);
		
			    return formatDate(gc.getTime(), "yyyy-MM-dd");

	    }

	    /**
	    * 获得某一个月份的最后一天
	    * 
	    * @param date
	    * @return
	    */

	    @SuppressWarnings("static-access")

	    public static Date getLastDayByMonth(Date date) {

			    Calendar cal = Calendar.getInstance();

			    cal.setTime(date);
			    cal.set(Calendar.DATE, 1);// 设为当前月的1号
			    cal.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
			    cal.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
			    return formatDate(cal.getTime(), "yyyy-MM-dd");
		
	    }

	    /**
	    * 获得指定日期的年份
	    * 
	    * @param date
	    * @return
	    */

	    public static int getYearByDate(Date date) {

		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);

		    return cal.get(Calendar.YEAR);

	    }

	    /**
	    * 获得指定日期的月份
	    * 
	    * @param date
	    * @return
	    */

	    public static int getMonthByDate(Date date) {

		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);

		    return cal.get(Calendar.MONTH);

	    }

	    /**
	    * 获得指定日期的所在月份当前的天数
	    * 
	    * @param date
	    * @return
	    */

	    public static int getDayInMonthByDate(Date date) {

			    Calendar cal = Calendar.getInstance();
			    cal.setTime(date);
		
			    return cal.get(Calendar.DAY_OF_MONTH);

	    }

	    /**
	    * 获得当前传入日期的上一个月份的当前日期
	    * 
	    * @param date
	    * @return
	    */

	    public static Date getPreviousDate(Date date) {

		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.add(Calendar.MONTH, -1);
	
		    return DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");

	    }

	    public static long compareDateDayValue(Date fDate, Date sDate) {

		    long l1 = fDate.getTime();
		    long l2 = sDate.getTime();
		    long diff = (Math.abs(l2 - l1)) / 1000 / 24 / 60 / 60;

	    return diff;

	    }

	    /**
	    * 获得当前传入日期的下一个月份的当前日期
	    * @param date
	    * @return
	    */

	    public static Date getLastMonthDate(Date date){

		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    cal.add(Calendar.MONTH, 1);

		    return DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");

	    }

	    /**
	    * 计算两个日期相差的月数（具体细分到天数的差别）
	    * @param date1
	    * @param date2
	    * @return
	    */

	    public static int getDiffMonths(Date date1, Date date2) {

	      int iMonth = 0;
	      int flag = 0;

	      try {

		       Calendar objCalendarDate1 = Calendar.getInstance();
		       objCalendarDate1.setTime(date1);
		       Calendar objCalendarDate2 = Calendar.getInstance();
		       objCalendarDate2.setTime(date2);
	
		       if (objCalendarDate2.equals(objCalendarDate1))
		    	   return 0;
	
		       if (objCalendarDate1.after(objCalendarDate2)) {
			        Calendar temp = objCalendarDate1;
			        objCalendarDate1 = objCalendarDate2;
			        objCalendarDate2 = temp;
		       }
	
		       if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
		    	   	flag = 1;

		       if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
	
		    	   iMonth = ((objCalendarDate2.get(Calendar.YEAR) 
		    			   		- objCalendarDate1.get(Calendar.YEAR))
		    			   			* 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
		    			   					- objCalendarDate1.get(Calendar.MONTH);
	
		       else
		    	   iMonth = objCalendarDate2.get(Calendar.MONTH)
		    	   				- objCalendarDate1.get(Calendar.MONTH) - flag;
	
		      } catch (Exception e) {
		       e.printStackTrace();
		      }
	      return iMonth;
	    }

	    /**
	    * 计算两个日期月数的差别，不计算详细到天数的差别
	    * 日期大的为参数一，结果为正数，反之为负数
	    * @return
	    */

	    public static int getDiffMonth(Date date1, Date date2){

			    Calendar calendar1 = Calendar.getInstance();
			    calendar1.setTime(date1);
			    Calendar calendar2 = Calendar.getInstance();
			    calendar2.setTime(date2);

			    int diffyaer = calendar1.get(Calendar.YEAR)-calendar2.get(Calendar.YEAR);
			    int diffmonth = calendar1.get(Calendar.MONTH)-calendar2.get(Calendar.MONTH);
		
			    return diffyaer*12+diffmonth;
		
	    }

	    
	    public static int getMonth(Date start, Date end) {

		    if (start.after(end)) {
		    	Date t = start;
		    	start = end;
		    	end = t;
		    }
	
		    Calendar startCalendar = Calendar.getInstance();
		    startCalendar.setTime(start);
		    Calendar endCalendar = Calendar.getInstance();
		    endCalendar.setTime(end);
	
		    Calendar temp = Calendar.getInstance();
	
		    temp.setTime(end);
		    temp.add(Calendar.DATE, 1);
	
		    int year = endCalendar.get(Calendar.YEAR)
		    				- startCalendar.get(Calendar.YEAR);
	
		    int month = endCalendar.get(Calendar.MONTH)
		    				- startCalendar.get(Calendar.MONTH);
	
		    if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
		    	
		    	return year * 12 + month + 1;
		    } 
		    else if ((startCalendar.get(Calendar.DATE) != 1)  && (temp.get(Calendar.DATE) == 1)) {
	
		    	return year * 12 + month;
	
		    } else if ((startCalendar.get(Calendar.DATE) == 1)  && (temp.get(Calendar.DATE) != 1)) {
	
		    	return year * 12 + month;
	
		    } else {
	
		    	return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
	
		    }
	
	    }

	    public static long getDateDeff(Date startDate, Date endDate) {

	    long intValue = 0;

		    try {
	
				    String df = new String("yyyy-MM-dd");
				    startDate = DateUtil.strToDate(DateUtil.dateToStr(startDate, df), df);
				    endDate = DateUtil.strToDate(DateUtil.dateToStr(endDate, df), df);
				    intValue = (startDate.getTime() - endDate.getTime()) / 86400000;
			
		    } catch (Exception e) {
		    		System.err.println("getDateDeff error");
		    }
	
		    return intValue;
	
	    }

	    public static String gethhssmmmssSSS(Date date){

	    	String result=DateUtil.dateToStr(date, "yyyy-MM-dd:HH:mm:ss:SSS").replace(":", "");

	    	return result.substring(10, result.length());

	    }
}
