package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import market.Market;
import vault.Vault;

public class DateUtils {

	public static void main(String[] args) {
		
		ListStockEntryDates();

	}
	
	static void ListStockEntryDates(){
		Vault vault = new Vault(Market.STOCK_MARKET);
		vault.speculate.runBackTest(vault);
		System.out.println("START DATE: " + dateToSimpleDateFormat(vault.speculate.getSortedEntryList().get(0).getDateTime()));
		Date startDate = localDateToUTCDate(vault.speculate.getSortedEntryList().get(0).getDateTime());
		int daysSince = DateUtils.getNumberOfDaysSinceDate(vault.speculate.getSortedEntryList().get(0).getDateTime());
		for(int i = 0; i < daysSince; i++) {
			Date date = addDays(startDate, i);
			System.out.println(dateToSimpleDateFormat(date));
		}
	}
	
	public static Date addDays(Date date, int num){
		Calendar cal = new GregorianCalendar(TimeZone.getDefault());
		cal.setTime(localDateToUTCDate(date));
		setTimeToMidnight(cal);
		cal.add(Calendar.DATE, num);
		Date addDay = cal.getTime();
		return localDateToUTCDate(addDay);
	}
	
	public static String dateToSimpleDateFormat(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		return formatter.format(date);
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
	
	public static void setTimeToMidnight(Calendar date){
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}

}
