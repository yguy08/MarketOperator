package backtest;

import org.junit.Test;

import util.DateUtils;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BackTestJunit {
	
	Date todaysDate;
	String simpleDateBaseline = "2017-05-18";
	String dateToSimpleDateFormat = DateUtils.dateToSimpleDateFormat(new GregorianCalendar(2017, Calendar.MAY, 18).getTime());
	
	@Test
	public void testDateToSimpleDateFormat(){
		assertEquals(simpleDateBaseline, dateToSimpleDateFormat);
	}

}
