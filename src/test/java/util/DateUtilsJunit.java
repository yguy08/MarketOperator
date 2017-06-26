package util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtilsJunit {
	
	String simpleDateBaseline = "2017-05-18";
	String dateToSimpleDateFormat = DateUtils.dateToSimpleDateFormat(new GregorianCalendar(2017, Calendar.MAY, 18).getTime());
	
	@Test
	public void testDateToSimpleDateFormat(){
		assertEquals(simpleDateBaseline, dateToSimpleDateFormat);
	}
	
	String MMddFormatBaseline = "05-18";
	String dateToMMddFormat = DateUtils.dateToMMddFormat(new GregorianCalendar(2017, Calendar.MAY, 18).getTime());
	
	@Test
	public void testdateToMMddFormat(){
		assertEquals(MMddFormatBaseline, dateToMMddFormat);
	}
	
	@Test
	public void testDateofTodayMinusDays(){
		Date oldDate = DateUtils.dateOfTodayMinusDays(20);
		int numDays = DateUtils.getNumDaysFromDateToToday(oldDate);
		assertEquals(20, numDays);
	}
	
		@Test
	public void testNormalizedDate() throws ParseException{
		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Date date1 = DateUtils.localDateToUTCDate(format.parse("Mon Feb 15 19:00:00 EST 2016"));
		Date date2 = DateUtils.localDateToUTCDate(format.parse("Sun Jun 25 20:00:00 EDT 2017"));
		Date date3 = DateUtils.localDateToUTCDate(format.parse("Wed Feb 08 19:00:00 EST 2017"));
		Date date4 = DateUtils.localDateToUTCDate(format.parse("Sun Jan 24 19:00:00 EST 2016"));
		Date date5 = DateUtils.localDateToUTCDate(format.parse("Fri Aug 07 20:00:00 EDT 2015"));
		assertEquals("02-16", DateUtils.dateToMMddFormat(date1));
		assertEquals("06-26", DateUtils.dateToMMddFormat(date2));
		assertEquals("02-09", DateUtils.dateToMMddFormat(date3));
		assertEquals("01-25", DateUtils.dateToMMddFormat(date4));
		assertEquals("08-08", DateUtils.dateToMMddFormat(date5));
	}
	

}
