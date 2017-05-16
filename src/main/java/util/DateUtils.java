package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

	public static void main(String[] args) {

	}
	
	public static Date addDays(Date date, int num){
		Calendar cal = new GregorianCalendar(TimeZone.getDefault());
		cal.setTime(date);
		setTimeToMidnight(cal);
		cal.add(Calendar.DATE, num);
		Date addDay = cal.getTime();
		return localDateToUTCDate(addDay);
	}
	
	public static Date dateToUTCMidnight(Date date){
		Calendar cal = new GregorianCalendar(TimeZone.getDefault());
		cal.setTime(date);
		setTimeToMidnight(cal);
		Date d = cal.getTime();
		return localDateToUTCDate(d);
	}
	
	public static Date getCurrentDateToUTCDateMidnight(){
		Date d = new Date();
		Date utcDate = localDateToUTCDate(d);
		Date utcMidnight = dateToUTCMidnight(utcDate);
		return utcMidnight;
	}
	
	public static String dateToSimpleDateFormat(Date date){
		Date utcDate = localDateToUTCDate(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		return formatter.format(utcDate);
	}
	
	public static String dateToMMddFormat(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		formatter.setLenient(false);
		return formatter.format(localDateToUTCDate(date));
	}
	
	public static Date getCurrentUTCDate(){
		Date dt = new Date();
		return localDateToUTCDate(dt); 
	}
	
	public static Date localDateToUTCDate(Date localDate){
		Calendar cal = new GregorianCalendar(TimeZone.getDefault());
		cal.setTime(localDate);
		long offset = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
		long minuteOffset = offset / (60 * 1000);
		cal.set(Calendar.MINUTE, (int)(cal.getMaximum(Calendar.MINUTE) - minuteOffset));
		Date utcDate = cal.getTime();
		return utcDate;
	}
	
	public static int getNumberOfDaysSinceDate(Date fromDate){
		Date currentDate = getCurrentUTCDate();
		Date sinceDate = localDateToUTCDate(fromDate);
		Long days = Math.abs((currentDate.getTime() - sinceDate.getTime())) / 86400000;
		String toInt = days.toString();
		return Integer.parseInt(toInt);
	}
	
	public static int getNumberOfDaysFromDate(Date dateToCompare, Date dateOf){
		Long days = Math.abs((dateOf.getTime() - dateToCompare.getTime())) / 86400000;
		String toInt = days.toString();
		return Integer.parseInt(toInt);
	}
	
	public static void setTimeToMidnight(Calendar date){
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}
	
	 public static Date UnixTimestampToDate(int unixTimeStamp) {
		 Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		 calendar.setTimeInMillis(Long.parseLong(unixTimeStamp + "000"));
		 return calendar.getTime();
	
	 }
}
