package util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilsJunit {
	
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
	
	@Test
	public void testPrettyPointX() throws ParseException{
		String prettyPoint1 = StringFormatter.prettyPointX(new BigDecimal(0.1));
		String prettyPoint2 = StringFormatter.prettyPointX(new BigDecimal(0.01));
		String prettyPoint3 = StringFormatter.prettyPointX(new BigDecimal(0.001));
		String prettyPoint4 = StringFormatter.prettyPointX(new BigDecimal(0.0001));
		String prettyPoint5 = StringFormatter.prettyPointX(new BigDecimal(0.00001));
		String prettyPoint6 = StringFormatter.prettyPointX(new BigDecimal(0.000001));
		String prettyPoint7 = StringFormatter.prettyPointX(new BigDecimal(0.0000001));
		String prettyPoint8 = StringFormatter.prettyPointX(new BigDecimal(0.00000001));
		String prettyPoint9 = StringFormatter.prettyPointX(new BigDecimal(1.0));
		String prettyPoint10 = StringFormatter.prettyPointX(new BigDecimal(10.0));
		String prettyPoint11 = StringFormatter.prettyPointX(new BigDecimal(100.0));
		assertEquals("10M", prettyPoint1);
		assertEquals("1M", prettyPoint2);
		assertEquals("100K", prettyPoint3);
		assertEquals("10K", prettyPoint4);
		assertEquals("1K", prettyPoint5);
		assertEquals("100", prettyPoint6);
		assertEquals("10", prettyPoint7);
		assertEquals("1", prettyPoint8);
		assertEquals("100M", prettyPoint9);
		assertEquals("1B", prettyPoint10);
		assertEquals("10B", prettyPoint11);
		
		String prettyPoint_1 = StringFormatter.prettyPointX(new BigDecimal(-0.1));
		String prettyPoint_2 = StringFormatter.prettyPointX(new BigDecimal(-0.01));
		String prettyPoint_3 = StringFormatter.prettyPointX(new BigDecimal(-0.009349848));
		
		assertEquals("-10M", prettyPoint_1);
		assertEquals("-1M", prettyPoint_2);
		assertEquals("-934K",prettyPoint_3);
	}
}
