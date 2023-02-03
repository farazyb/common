package hello.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarConversion {

    private int irYear; // Year part of a Iranian date
    private int irMonth; // Month part of a Iranian date
    private int irDay; // Day part of a Iranian date
    private int gYear; // Year part of a Gregorian date
    private int gMonth; // Month part of a Gregorian date
    private int gDay; // Day part of a Gregorian date
    private int juYear; // Year part of a Julian date
    private int juMonth; // Month part of a Julian date
    private int juDay; // Day part of a Julian date
    private int leap; // Number of years since the last leap year (0 to 4)
    private int JDN; // Julian Day Number
    private int march; // The march day of Farvardin the first (First day of jaYear)

    /**
     * create instance of  gregorian calendar at current Date
     */
    public CalendarConversion() {
        Calendar calendar = new GregorianCalendar();
        setGregorianDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * create instance of  gregorian calendar at date by input Parameters
     *
     * @param year  gregorian Year as int
     * @param month gregorian month as int
     * @param day   gregorian day as int
     */
    public CalendarConversion(int year, int month, int day) {
        setGregorianDate(year, month, day);
    }

    /**
     * @return solar calendar year
     */
    public int getIranianYear() {
        return irYear;
    }

    /**
     * @return solar calendar month
     */
    public int getIranianMonth() {
        return irMonth;
    }

    /**
     * @return solar calendar day
     */
    public int getIranianDay() {
        return irDay;
    }

    /**
     * @return gregorian calendar year
     */
    public int getGregorianYear() {
        return gYear;
    }

    /**
     * @return gregorian calendar month
     */
    public int getGregorianMonth() {
        return gMonth;
    }

    /**
     * @return gregorian calendar day
     */
    public int getGregorianDay() {
        return gDay;
    }

    /**
     * @return Julian calendar year
     */
    public int getJulianYear() {
        return juYear;
    }

    /**
     * @return Julian calendar month
     */
    public int getJulianMonth() {
        return juMonth;
    }

    /**
     * @return Julian calendar day
     */
    public int getJulianDay() {
        return juDay;
    }

    /**
     * get exact time as hh:mm:ss Format
     *
     * @return String
     */
    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);

        return (h < 10 ? ("0" + h) : h) + ":" + (m < 10 ? ("0" + m) : m) + ":" + (s < 10 ? ("0" + s) : s);
    }

    /**
     * get exact time as HHmm Format as a long
     *
     * @return long
     */
    public long getCheckTime() {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);

        return Integer.parseInt((h < 10 ? ("0" + h) : h) + "" + (m < 10 ? ("0" + m) : m));
    }

    /**
     * get exact time as hhmmss Format
     *
     * @return String
     */
    @Deprecated
    public String getSwitchTime() {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);

        return (h < 10 ? ("0" + h) : h) + "" + (m < 10 ? ("0" + m) : m) + "" + (s < 10 ? ("0" + s) : s);
    }

    /**
     * get exact time as hms Format without any padding
     *
     * @return String
     */
    @Deprecated
    public String getVoucherTime() {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);

        return h + m + s + "";
    }

    /**
     * @return solar date as yyyy/MM/dd format as a String
     */
    public String getIranianDate() {
        String month = irMonth + "";
        String day = irDay + "";
        if (irMonth < 10) {
            month = "0" + irMonth;
        }
        if (irDay < 10) {
            day = "0" + irDay;
        }
        return (irYear + "/" + month + "/" + day);
    }

    /**
     * @return Gregorian Date as MM/dd/yyyy format as a String
     */
    public String getGregorianDate() {
        return ((gMonth < 10 ? ("0" + gMonth) : gMonth) + "/" + (gDay < 10 ? ("0" + gDay) : gDay) + "/" + (gYear < 10 ? ("0" + gYear) : gYear));
    }

    /**
     * @return Gregorian Date as yyyyMMdd format as a Long
     */
    public int getGregorianCheckDate() {
        return Integer.parseInt((gYear < 10 ? ("0" + gYear) : gYear) + "" + (gMonth < 10 ? ("0" + gMonth) : gMonth) + "" + (gDay < 10 ? ("0" + gDay) : gDay));
    }

    /**
     * @return Gregorian Date as yyyyMMdd format as a String
     */
    public String getCheckDate() {
        return ((gYear < 10 ? ("0" + gYear) : gYear) + "" + (gMonth < 10 ? ("0" + gMonth) : gMonth) + "" + (gDay < 10 ? ("0" + gDay) : gDay));
    }

    /**
     * @return Gregorian Date as MMdd format as a String
     */
    public String getSwitchDate() {
        return ((gMonth < 10 ? ("0" + gMonth) : gMonth) + "" + (gDay < 10 ? ("0" + gDay) : gDay));
    }

    /**
     * @return Gregorian Date as MM/dd/yyyy format as a String
     */
    public String getVoucherGregorianDate() {
        return ((gMonth < 10 ? ("0" + gMonth) : gMonth) + "/" + (gDay < 10 ? ("0" + gDay) : gDay) + "/" + (gYear < 10 ? ("0" + gYear) : gYear));
    }

    /**
     * @return Julian Date as yyyy/MM/dd format as a String
     */
    public String getJulianDate() {
        return (juYear + "/" + juMonth + "/" + juDay);
    }

    /**
     * @return Name of Day in Week  as a String
     */
    public String getWeekDayStr() {
        String weekDayStr[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return (weekDayStr[getDayOfWeek()]);
    }

    /**
     * @return All type of Date as a String
     */
    public String toString() {
        return (getWeekDayStr() + ", Gregorian:[" + getGregorianDate() + "], Julian:[" + getJulianDate()
                + "], Iranian:[" + getIranianDate() + "]");
    }

    /**
     * change Date to next day
     */
    public void nextDay() {
        JDN++;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * change Date to many next day as you want
     * @param days
     */
    public void nextDay(int days) {
        JDN += days;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * change Date to previous day
     */
    public void previousDay() {
        JDN--;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * change date to many previous day as you want
     * @param days
     */
    public void previousDay(int days) {
        JDN -= days;
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * create instance of Calendar as solar calendar with input parameters
     * @param year
     * @param month
     * @param day
     */
    public void setIranianDate(int year, int month, int day) {
        irYear = year;
        irMonth = month;
        irDay = day;
        JDN = IranianDateToJDN();
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     * create instance of Calendar as Gregorian calendar with input parameters
     * @param year
     * @param month
     * @param day
     */
    public void setGregorianDate(int year, int month, int day) {
        gYear = year;
        gMonth = month;
        gDay = day;
        JDN = gregorianDateToJDN(year, month, day);
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }
    /**
     * create instance of Calendar as Julian calendar with input parameters
     * @param year
     * @param month
     * @param day
     */
    public void setJulianDate(int year, int month, int day) {
        juYear = year;
        juMonth = month;
        juDay = day;
        JDN = julianDateToJDN(year, month, day);
        JDNToIranian();
        JDNToJulian();
        JDNToGregorian();
    }

    /**
     *
     * @return GMT Time as MMddhhmmss Format
     */
    public String getGMT() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    private int getDayOfWeek() {
        return (JDN % 7);
    }


    private void IranianCalendar() {
        // Iranian years starting the 33-year rule
        int Breaks[] = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060, 2097, 2192, 2262, 2324,
                2394, 2456, 3178};
        int jm, N, leapJ, leapG, jp, j, jump;
        gYear = irYear + 621;
        leapJ = -14;
        jp = Breaks[0];
        // Find the limiting years for the Iranian year 'irYear'
        j = 1;
        do {
            jm = Breaks[j];
            jump = jm - jp;
            if (irYear >= jm) {
                leapJ += (jump / 33 * 8 + (jump % 33) / 4);
                jp = jm;
            }
            j++;
        } while ((j < 20) && (irYear >= jm));
        N = irYear - jp;
        // Find the number of leap years from AD 621 to the begining of the current
        // Iranian year in the Iranian (Jalali) calendar
        leapJ += (N / 33 * 8 + ((N % 33) + 3) / 4);
        if (((jump % 33) == 4) && ((jump - N) == 4)) {
            leapJ++;
        }
        // And the same in the Gregorian date of Farvardin the first
        leapG = gYear / 4 - ((gYear / 100 + 1) * 3 / 4) - 150;
        march = 20 + leapJ - leapG;
        // Find how many years have passed since the last leap year
        if ((jump - N) < 6) {
            N = N - jump + ((jump + 4) / 33 * 33);
        }
        leap = (((N + 1) % 33) - 1) % 4;
        if (leap == -1) {
            leap = 4;
        }
    }

    private int IranianDateToJDN() {
        IranianCalendar();
        return (gregorianDateToJDN(gYear, 3, march) + (irMonth - 1) * 31 - irMonth / 7 * (irMonth - 7) + irDay - 1);
    }

    private void JDNToIranian() {
        JDNToGregorian();
        irYear = gYear - 621;
        IranianCalendar(); // This invocation will update 'leap' and 'march'
        int JDN1F = gregorianDateToJDN(gYear, 3, march);
        int k = JDN - JDN1F;
        if (k >= 0) {
            if (k <= 185) {
                irMonth = 1 + k / 31;
                irDay = (k % 31) + 1;
                return;
            } else {
                k -= 186;
            }
        } else {
            irYear--;
            k += 179;
            if (leap == 1) {
                k++;
            }
        }
        irMonth = 7 + k / 30;
        irDay = (k % 30) + 1;
    }

    private int julianDateToJDN(int year, int month, int day) {
        return (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408;
    }

    private void JDNToJulian() {
        int j = 4 * JDN + 139361631;
        int i = ((j % 1461) / 4) * 5 + 308;
        juDay = (i % 153) / 5 + 1;
        juMonth = ((i / 153) % 12) + 1;
        juYear = j / 1461 - 100100 + (8 - juMonth) / 6;
    }

    private int gregorianDateToJDN(int year, int month, int day) {
        int jdn = (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408;
        jdn = jdn - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;
        return (jdn);
    }

    private void JDNToGregorian() {
        int j = 4 * JDN + 139361631;
        j = j + (((((4 * JDN + 183187720) / 146097) * 3) / 4) * 4 - 3908);
        int i = ((j % 1461) / 4) * 5 + 308;
        gDay = (i % 153) / 5 + 1;
        gMonth = ((i / 153) % 12) + 1;
        gYear = j / 1461 - 100100 + (8 - gMonth) / 6;
    }


}
