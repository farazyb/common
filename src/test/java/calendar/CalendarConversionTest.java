package calendar;

import hello.calendar.CalendarConversion;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalendarConversionTest {
    CalendarConversion calendarConversion;
    private final static String CURRENT_DATE = "02/03/2023";
    private final static String SOLAR_CALENDAR_CURRENT_DATE = "1401/11/14";
    private final static String JULIAN_CALENDAR_CURRENT_DATE = "1401/11/14";

    @Before
    public void setObject() {
        calendarConversion = new CalendarConversion();
    }

    @Test
    public void ifCurrentGregorianDateIsCorrect() {
        String currentDate = calendarConversion.getGregorianDate();
        boolean result = currentDate.equals(CURRENT_DATE);
        assertTrue(CURRENT_DATE, result);
    }

    @Test
    public void ifCurrentSolarDateIsCorrect(){
        assertEquals(calendarConversion.getIranianDate(),SOLAR_CALENDAR_CURRENT_DATE);
    }


}
