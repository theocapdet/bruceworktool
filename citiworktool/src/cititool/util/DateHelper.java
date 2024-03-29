/**
 * <p>Title: 日期常用工具方法类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 源天软件</p>
 * @author Rockey 2006-4-10
 * @version 1.0
 */
package cititool.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 日期常用工具方法类
 */
public final class DateHelper {
	  /**
     * "YYYY-MM-DD"日期格式
     */
    public static final String      DATE_YYYYMMMMDD     = "yyyy-MM-dd";

    /**
     * "HH:MM:SS"时间格式
     */
    public static final String      TIME_HHCMMCSS       = "HH:mm:ss";
    /**
     * "HH:MM"时间格式
     */
    public static final String      TIME_HHCMM   = "HH:MM";
    /**
     * "HH:MM:SS AMPM"时间格式
     */
    public static final String      TIME_HHCMMCSSAMPM   = "HH:MM:SS AMPM";
    
    private static SimpleDateFormat m_dtFormater        = null;
    
    
    public static String convertDateIntoYYYYMMDD_HHCMMCSSStr(Date date)
    {
        return convertDateIntoDisplayStr(date, DATE_YYYYMMMMDD + " " + TIME_HHCMMCSS);
    }

    public static String convertDateIntoYYYYMMDDStr(Date date)
    {
        return convertDateIntoDisplayStr(date, null);
    }
    
    public static String convertDateIntoYYYYMMDD_HHMMStr(Date date)
    {
        return convertDateIntoDisplayStr(date, DATE_YYYYMMMMDD + " " + TIME_HHCMM);
    }

    
    public static String convertDateIntoDisplayStr(Date date, String format)
    {
        String dateStr = null;
        if (format == null)
            format = DATE_YYYYMMMMDD;
        if (m_dtFormater == null)
        {
            m_dtFormater = new SimpleDateFormat();
        }
        m_dtFormater.applyPattern(format);
        if (date != null)
        {
            dateStr = m_dtFormater.format(date);
        }
        return dateStr;
    }
    

	public static String getCurDateTimeStr()
  	{
		Date newdate = new Date() ;
		long datetime = newdate.getTime() ;
		Timestamp timestamp = new Timestamp(datetime) ;
		String str = timestamp.toString();
		return new StringBuffer()
						.append(datetime)
						.toString();
   	}
	/**
	 * 获得当前周是当前年的第几周。 
	 */
	public static Integer getWeekOfYear(){
		Calendar c = Calendar.getInstance();   
		//c.get(Calendar.YEAR);
		//c.get(Calendar.WEEK_OF_MONTH);
		int weekint =c.get(Calendar.WEEK_OF_YEAR);
		
		return Integer.valueOf(weekint);
	}
	public static String getCurDateTime()
  	{
		Date newdate = new Date() ;
		long datetime = newdate.getTime() ;
		Timestamp timestamp = new Timestamp(datetime) ;
		return (timestamp.toString()).substring(0,19);
   	}

	public static String getCurrentDate()
  	{
		Date newdate = new Date() ;
		long datetime = newdate.getTime() ;
		Timestamp timestamp = new Timestamp(datetime) ;
		String currentdate = (timestamp.toString()).substring(0,4) + "-" + (timestamp.toString()).substring(5,7) + "-" +(timestamp.toString()).substring(8,10);
		return currentdate;
   	}
	//返回"yyyy-MM-dd"格式日期 
	public static String getDate(Date newdate){
		if (newdate== null)
			return "";
		long datetime = newdate.getTime() ;
		Timestamp timestamp = new Timestamp(datetime) ;
		String currentdate = (timestamp.toString()).substring(0,4) + "-" + (timestamp.toString()).substring(5,7) + "-" +(timestamp.toString()).substring(8,10);
		return currentdate;
		
	}
	
	//返回"yyyy-MM-dd HH:MM:SS"格式日期
	public static String getDateTime(Date date)
  	{
		if (date==null)
			return "";
		long datetime = date.getTime() ;
		Timestamp timestamp = new Timestamp(datetime) ;
		return (timestamp.toString()).substring(0,19);
   	}
	//返回"yyyy-MM-dd HH:MM"格式日期
	public static String getDateHHMM(Date date)
  	{
		if (date==null)
			return "";
		long datetime = date.getTime() ;
		Timestamp timestamp = new Timestamp(datetime);
		return (timestamp.toString()).substring(0,16);
   	}
	//获取开始时间和结束时间之间的天数
   	public static long getDisDays (String datebegin, String dateend) {
   		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
   		try {
   			Date dateBegin=sdf.parse(datebegin);
   	   		Date dateEnd=sdf.parse(dateend);
   	   		return (dateEnd.getTime()-dateBegin.getTime())/(3600*24*1000) + 1;	
		} catch (Exception e) {
			return 0;
		}
   	}
   	
   	

   	public static String getCurrentTime()
  	{
		Date newdate = new Date() ;
		long datetime = newdate.getTime() ;
		Timestamp timestamp = new Timestamp(datetime) ;
		String currenttime = (timestamp.toString()).substring(11,13) + ":" + (timestamp.toString()).substring(14,16) + ":" +(timestamp.toString()).substring(17,19);
		return currenttime;
   	}
   	/**
   	* 计算2个日期之间间隔天数方法
   	*
   	* @param d1    The first Calendar.
   	* @param d2    The second Calendar.
   	*
   	* @return      天数
   	*/
   	public static long getDaysBetween (java.util.Calendar d1, java.util.Calendar d2) {
   	    return (d1.getTime().getTime()-d2.getTime().getTime())/(3600*24*1000);
   	}
   	/**
   	* 计算2个日期之间间隔天数方法
   	*				
   	* @param d1    	The first Calendar.
   	* 				格式yyyy-MM-dd
   	* @param d2    	The second Calendar.
   	*
   	* @return      天数
   	*/
   	public static long getDaysBetween (String d1, String d2) {
   		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
   		try {
   			Date dt1=sdf.parse(d1);
   	   		Date dt2=sdf.parse(d2);
   	   		return (dt1.getTime()-dt2.getTime())/(3600*24*1000);	
		} catch (Exception e) {
			return 0;
		}
   		
   	}
   	/**
   	 * @param d1
   	 * @param d2
   	 * @param onlyWorkDay 是否只计算工作日
   	 * @return 计算两个日期之间的时间间隔(d1-d2)，可选择是否计算工作日
   	 */
   	public static long getDaysBetween (String d1,String d2,boolean onlyWorkDay) {
   		if(!onlyWorkDay){
   			return getDaysBetween(d1,d2);
   		}else{
	   		long days=0;
	   		Calendar calendar=Calendar.getInstance();   		
	   		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");   		
	   		try {
	   			Date dt1=sdf.parse(d1);
	   			Date dt2=sdf.parse(d2);
	   			days= (dt1.getTime()-dt2.getTime())/(3600*24*1000);
	   			for(calendar.setTime(dt1);!calendar.getTime().before(dt2);calendar.add(Calendar.DAY_OF_YEAR,-1)){
	   				int week=calendar.get(Calendar.DAY_OF_WEEK);
	   		   		if(week == Calendar.SUNDAY || week == Calendar.SATURDAY){
	   		   			days--;
	   		   		}
	   			}
	   			if(days<0){
	   				days=0;
	   			}
	   		} catch (Exception e) {}
	   		return days;
   		}
   	}
   	/**
   	 * @param date
   	 * @return 判断日期是否为工作日(周六和周日为非工作日)
   	 */
   	public static boolean isWorkDay(Date date){
   		Calendar calendar=Calendar.getInstance();
   		calendar.setTime(date);
   		int week=calendar.get(Calendar.DAY_OF_WEEK);
   		if(week == Calendar.SUNDAY || week == Calendar.SATURDAY){
   			return false;
   		}else{
   			return true;
   		}
   	}
   	/**
   	 * 计算两个时间之间的间隔  单位：分钟(minutes)
   	 * 格式 yyyy-MM-dd/HH:mm:ss
   	 * */
   	public static long getMinutesBetween(String s1, String s2) {
   		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd/HH:mm:ss");
   		try {
   			Date dt1=sdf.parse(s1);
   			Date dt2=sdf.parse(s2);
   			return (dt1.getTime()-dt2.getTime())/(60*1000);	
   		} catch (Exception e) {
   			return 0;
   		}
   		
   	}
   	
   	/*				
   	* @param d1    	开始日期
   	* 				格式yyyy-MM-dd
   	* @param d2    	结束日期.
   	*
   	* @return      按月分隔的list，list里面每个月一个map,第一天begindate，最后一天enddate
   	*/
   	public static List<HashMap> getDateBetween (String d1, String d2) {
   		ArrayList<HashMap> list = new ArrayList<HashMap>();
   		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
   		try {
   	   		Calendar cal1 = Calendar.getInstance();
   	   		Calendar cal2 = Calendar.getInstance();
   	   		cal1.setTime(sdf.parse(d1));
   	   		cal2.setTime(sdf.parse(d2));
   	   		
   	        int monthnum =(cal2.get(Calendar.YEAR)- cal1.get(Calendar.YEAR))*12 + cal2.get(Calendar.MONTH)- cal1.get(Calendar.MONTH);
   	        for(int i=0;i<monthnum;i++){
   	        	HashMap<String,Object> map =  new HashMap<String,Object>();
   	        	map.put("begindate",sdf.format(cal1.getTime()));
   	        	cal1.add(Calendar.DATE,cal1.getActualMaximum(Calendar.DATE)-cal1.get(Calendar.DATE));
   	        	map.put("enddate",sdf.format(cal1.getTime()));
   	        	list.add(map);
   	        	cal1.add(Calendar.MONTH,1);
   	        	cal1.add(Calendar.DATE,1-cal1.get(Calendar.DATE));
   	        }
   	        HashMap<String,Object> map =  new HashMap<String,Object>();
   	        map.put("begindate",sdf.format(cal1.getTime()));
   	        map.put("enddate",d2);
   	        list.add(map);  
		} catch (Exception e) {
			return list;
		}			
   		return list;
   	}
   	/*				
   	* 两个时间段得相交的天数
   	* 				格式yyyy-MM-dd
   	*
   	*
   	* @return      天数 
   	*/  	
    public static long getDays(String b1,String e1,String b2,String e2){
 	   long ret = 0;
 	   String begindate = "";
 	   String enddate = "";
 	   SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
   		try {
    	   		Calendar begin1 = Calendar.getInstance();
    	   		Calendar end1 = Calendar.getInstance();
    	   		Calendar begin2 = Calendar.getInstance();
    	   		Calendar end2 = Calendar.getInstance();	  
    	   	    begin1.setTime(sdf.parse(b1));
    	     	end1.setTime(sdf.parse(e1));
    	   	    begin2.setTime(sdf.parse(b2));
    	     	end2.setTime(sdf.parse(e2)); 
 	     	//时间段不相交 
    	     	if ((begin2.getTime().getTime()>end1.getTime().getTime() && end2.getTime().getTime()>end1.getTime().getTime()) ||
    	     		(begin2.getTime().getTime()<begin1.getTime().getTime() && end2.getTime().getTime()<begin1.getTime().getTime())) {
    	     		//System.out.println("b"+ret);
    	     		return ret;
             } 
    	     	
   	     	if (begin2.getTime().getTime() >=  begin1.getTime().getTime()){
    	     		begindate = sdf.format(begin2.getTime());
    	     	}else{
    	     	    begindate = sdf.format(begin1.getTime());
    	     	}
    	     	if (end2.getTime().getTime()>= end1.getTime().getTime()){
    	     		enddate = sdf.format(end1.getTime());
    	     	}else{
    	     	    enddate = sdf.format(end2.getTime());	
    	     	}
    	     	
    	     	if (!begindate.equals("") && !enddate.equals("")){
    	     		ret = getDisDays(begindate,enddate);
    	     	}
 		} catch (Exception e) {
 	        
 		}	  
 		//System.out.println("e"+ret);
 	   return ret;
    }
  	/**			
  	* 比较2个格式为yyyy-MM-dd的日期<br>
  	* 若d1小于d2返回true<br>
  	* d1=2007-10-01,d2=2007-10-15,则返回true
  	* @return 
  	*/  
    public static boolean after(String d1,String d2){
  		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
   		try {
   			Date dt1=sdf.parse(d1);
   	   		Date dt2=sdf.parse(d2);
   	   		return dt1.getTime() < dt2.getTime();	
		} catch (Exception e) {
			return false;
		}
   }
   
   /**
    * 比较两个月分的先后.如果d2>d1,返回true
    * @param d1
    * @param d2
    * @return
    */
    public static boolean afterMonth(String d1,String d2){
 		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM");
  		try {
  			Date dt1=sdf.parse(d1);
  	   		Date dt2=sdf.parse(d2);
  	   		return dt1.getTime() < dt2.getTime();	
		} catch (Exception e) {
			return false;
		}
  }
   
 	/**			
 	* 比较2个日期
 	*
 	* @return 
 	*/  
    public static boolean afterAndEqual(String d1,String d2){
 		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
  		try {
  			Date dt1=sdf.parse(d1);
  	   		Date dt2=sdf.parse(d2);
  	   		return dt1.getTime() <= dt2.getTime();	
		} catch (Exception e) {
			return false;
		}
  }
   
	/*				
	* 移动日期
	* @param date 原日期
	* @param len 移动量
	* @return 移动后日期
	*/  
    public static String dayMove(String date,int len){
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
 		try {
 			Calendar cal = Calendar.getInstance();
  	   		cal.setTime(sdf.parse(date));
  	   		cal.add(Calendar.DATE, len);
 	   		return sdf.format(cal.getTime());	
		} catch (Exception e) {
			return date;
		}
 }
    
    
    public static String dateTimeMove(String datetime,int len){
    	
    	SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    	try{
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(sdf.parse(datetime));
    	cal.add(Calendar.MINUTE, len);
    	return sdf.format(cal.getTime());
    	}
    	catch(Exception e){
    		return datetime;
    	}
    }
 
    public static String getCurrentMonth(){
    	Calendar today=Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    	String curmonth=sdf.format(today.getTime());
    	return curmonth;
}
 
	/*				
	* 移动月份
	* @param date 原日期
	* @param len 移动量
	* @return 移动后日期
	*/  
    public static String monthMove(String date,int len){
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM");
		try {
			Calendar cal = Calendar.getInstance();
	   		cal.setTime(sdf.parse(date));
	   		cal.add(Calendar.MONTH, len);
	   		return sdf.format(cal.getTime());	
		} catch (Exception e) {
			return date;
		}
    }

	/*				
	* 截取2个时间段相交的时间段
	*
	* @return  String[] = {array[0]=begindate ,array[1]=enddate}
	* 不相交    array[0]=""
	* 
	*/ 
    public static String[] getBetweenDate(String b1,String e1,String b2,String e2){
       String[] date = new String[2];
	   String begindate = "";
	   String enddate = "";
	   SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
		try {
 	   		Calendar begin1 = Calendar.getInstance();
 	   		Calendar end1 = Calendar.getInstance();
 	   		Calendar begin2 = Calendar.getInstance();
 	   		Calendar end2 = Calendar.getInstance();	  
 	   	    begin1.setTime(sdf.parse(b1));
 	     	end1.setTime(sdf.parse(e1));
 	   	    begin2.setTime(sdf.parse(b2));
 	     	end2.setTime(sdf.parse(e2)); 
 	   	    if ((begin2.getTime().getTime()>=end1.getTime().getTime() && end2.getTime().getTime()>=end1.getTime().getTime()) ||
 	     		(begin2.getTime().getTime()<=begin1.getTime().getTime() && end2.getTime().getTime()<=begin1.getTime().getTime())) {
 	     	    date[0] = "";
 	     		return date;
          } 
 	     	
	     	if (begin2.getTime().getTime() >=  begin1.getTime().getTime()){
 	     		begindate = sdf.format(begin2.getTime());
 	     	}else{
 	     	    begindate = sdf.format(begin1.getTime());
 	     	}
 	     	if (end2.getTime().getTime()>= end1.getTime().getTime()){
 	     		enddate = sdf.format(end1.getTime());
 	     	}else{
 	     	    enddate = sdf.format(end2.getTime());	
 	     	}
 	     	
 	     	if (!begindate.equals("") && !enddate.equals("")){
 	     		date[0] = begindate;
 	         	date[1] = enddate;
 	     	}
		} catch (Exception e) {
	        
		}	   	
  	return date;
  } 	
 
    /**
     * 移动月份
     * @param date
     *            时间
     * @param month
     *            移动月份
     * @return
     */
    public static Date getMonth(Date date, int month)
    {
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }
    
    /**
  	* 取得当前星期的第一天
  	* 
  	* @return
  	*/
    public static Date getCurrentWeekFirstDay()
    {
    	Date date = new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
    	Date first = calendar.getTime();//getDay(calendar.getTime(), 1);
    	if (getDayOfWeek(date)==1){//如果今天是星期日
    		first = getDay(calendar.getTime(), -6);
    	}
    	else{
    		first = getDay(calendar.getTime(), 1);
    	}
    	return first;
    }
    
    /**
     * 取得当前日期为本周的第几天
     * 
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
 
    /**
     * 取得当前月份的第一天
     * 
     * @return
     */
    public static Date getCurrentMonthFirstDay()
    {
    	Date date = new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    	return calendar.getTime();
    }
 
    /**
     * 返回指定日期的前后的i天
     * 
     * @param date
     * @param i
     * @return
     */
    public static Date getDay(Date date, int i)
    {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_YEAR, i);
    	return calendar.getTime();
    }
    
    /**
     * 读取查询时的开始时间
     * 
     * @param start
     * @return
     */
    public static Date getStart(Date start)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * String 转为date型
     */

    public static Date stringtoDate(String stringDate )
    {
    	if (StringHelper.isEmpty(stringDate)){
    		return new Date();
    	}
    	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");   
    	Date date = null;
    	try {    
             date = format1.parse(stringDate);               
    	} catch (Exception e) {    
            e.printStackTrace();    
    	}    
        return date;
    }
    
//    /**
//     * 转化数据库通配符
//     * @param str
//     * @return
//     */
//	public static String wildcard(String str) {
//		if (StringHelper.isEmpty(str)){
//			return "";
//		}
//		String dialectname = StringHelper.null2String(BaseContext.getDialectname());
//		if(dialectname.indexOf("SQLServer")!=-1){
//			if (str.indexOf("[")!=-1){
//				str=StringHelper.replaceString(str,"[","[[]");
//			}
//			if (str.indexOf("%")!=-1){
//				str=StringHelper.replaceString(str,"%","[%]");
//			}
//			if (str.indexOf("_")!=-1){
//				str=StringHelper.replaceString(str,"_","[_]");
//			}
//		}
//		if(dialectname.indexOf("Oracle")!=-1){
//
//		}
//		return str;
//	}
}
