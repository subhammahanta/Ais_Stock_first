package com.watsoo.device.management.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.watsoo.device.management.constant.Constant;

@Component
public class DateUtil {

	public static Date getFromDateLong(Long fromDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(fromDate));
		Date dateFrom = cal.getTime();
		return dateFrom;
	}

	public static Date getToDate(Long toDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(toDate));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date dateTo = calendar.getTime();
		// dateTo = addMinutesToJavaUtilDate(dateTo, -(Constant.IST_OFFSET_IN_MINUTES));
		return dateTo;
	}

	public static Date getToDateLong(Long toDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(toDate));
		Date dateTo = calendar.getTime();
		return dateTo;
	}

	public static Date convertStringToDate(String stringDate, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(stringDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateFromLong(Long fromDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(fromDate));
		Date dateFrom = cal.getTime();
		return dateFrom;
	}

	public static Long convertMilliToMin(Long milli) {
		if (milli == null) {
			return 0l;
		}
		return TimeUnit.MILLISECONDS.toMinutes(milli);
	}

	public static float diffDateInMinutes(Date from, Date to) {
		Long dateDiff = Math.abs(from.getTime() - to.getTime());
		return (float) dateDiff / (60 * 1000);
	}

	public static Date addMinutesToJavaUtilDate(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		date = calendar.getTime();
		return date;
	}

	public static Date getFromDateWithTime(Long fromDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(fromDate));
		Date dateFrom = cal.getTime();
		dateFrom = cal.getTime();
		dateFrom = addMinutesToJavaUtilDate(dateFrom, -330);
		SimpleDateFormat isoFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			dateFrom = isoFormat.parse(dateFrom.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFrom;
	}

	public static String localDateTimeToStringInFormatYYYYMMDDHHMMSS(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
		return formatter.format(date);
	}

	// Helper method to compare only the date portions of two Date objects
	public static boolean areDatesEqual(Date date1, Date date2) {
		// Get the year, month, and day for each date
		int year1 = date1.getYear();
		int month1 = date1.getMonth();
		int day1 = date1.getDate();

		int year2 = date2.getYear();
		int month2 = date2.getMonth();
		int day2 = date2.getDate();

		// Compare the extracted date parts
		return year1 == year2 && month1 == month2 && day1 == day2;
	}

	public static Date getStartDateTimeOfTheDay(int day) {
		//FilterDateDTO dto = new FilterDateDTO();
		// Get the current date and time
		Date currentDateAndTime = new Date();

		// Calculate two days ago
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDateAndTime);
		cal.add(Calendar.DAY_OF_MONTH, -day);

//		// Start date-time of two days ago
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//		cal.set(Calendar.MILLISECOND, 0);
//		Date startOfDay = cal.getTime();

		// End date-time of two days ago (end of the day)
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date endOfDay = cal.getTime();
//		dto.setFromDate(startOfDay);
//		dto.setTodDate(endOfDay);
		return endOfDay;
	}
	
	public static Date addDaysToDate(Date date ,Integer Days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, Days);
		return calendar.getTime();
	}

	public static String localDateTimeToStringInFormatDDMMYYYYHHMMA(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MM_yyyy_hh_mm_a);
		return formatter.format(date);
	}
	
	public static String localDateTimeToStringInFormat(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	public static Date addMinutesToJavaUtilDateV2(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -minutes);
		date = calendar.getTime();
		return date;
	}
	
	public static boolean datesEqualIgnoringTime(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
