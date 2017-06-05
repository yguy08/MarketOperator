package util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
	
	Date todaysDate = DateUtils.dateToUTCMidnight(new GregorianCalendar().getTime());
	

}
