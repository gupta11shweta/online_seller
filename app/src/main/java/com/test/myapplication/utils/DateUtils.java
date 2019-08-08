package com.test.myapplication.utils;

import android.util.Log;

import com.test.myapplication.model.OrderDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static final String DATE_FORMAT = "dd-MMM-yy";
    public final static String DATE_TIME_FORMAT_IST = "dd MMM yyyy hh:mm:ss a";
    public final static String DATE_TIME_FORMAT = "dd MMM yyyy hh:mm";

    public static final String UTC_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public final static String[] SUPPORTED_DATE_FORMATS = {
            "MM/dd/yyyy",
            "dd-MMM-yyyy",
            "dd-MM-yyyy",
            "MM/dd/yy",
            "dd/MM/yyyy",
            "yyyy/MM/dd",
            "yyyy/dd/MM",
            "EE, MMM dd, yyyy",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss",
            "EE, MMM dd, yyyy HH:mm:ss",
            "EE, MMM dd, yyyy HH:mm",
            "yyyy-MM-dd HH:mm",
            "EE, MMM dd, yyyy",
            "MMM dd",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd hh:mm a",
            "HH:mm",
            "dd-MMM-yy",
            "dd MMM yyyy",
            "MMMM, dd",
            "MMMM dd, yyyy",
            "yyyy-MM-dd'T'HH:mm:ssZ"
    };
    private static final String TAG = DateUtils.class.getSimpleName();
    public final static String DATE_TIME_FORMAT_MONTH = "dd-MMM-yyyy HH:mm:ss";

    private DateUtils() {

    }

    public static String format(Date date) {
        return format(date, DATE_FORMAT);
    }

    public static String format(Date date, String format) {
        if (format == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date getServerDate() {
        Calendar calendar = Calendar.getInstance();
        /*if (SessionInfoContainer.getDifferenceInClientServerDates() != null) {
            calendar.setTimeInMillis(new Date().getTime()
					- SessionInfoContainer.getDifferenceInClientServerDates());
		}*/
        return calendar.getTime();
    }

    public static Date getDateFromSOAPResponse(String soapDate) {
        if (soapDate == null) {
            return null;
        }
        try {
            String[] parts = soapDate.split("T")[0].split("-");

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.YEAR, Integer.parseInt(parts[0]));
            calendar.set(Calendar.MONTH, (Integer.parseInt(parts[1]) - 1));
            calendar.set(Calendar.DATE, Integer.parseInt(parts[2]));

            return calendar.getTime();

        } catch (Exception e) {
            Log.e(TAG, "getDateFromSOAPResponse", e);
            return null;
        }
    }

    public static Date parseDate(String sDate) {
        if (sDate == null) {
            return null;
        }
        try {
            sDate = sDate.trim();
            String[] parts = sDate.split("/");

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.DATE, Integer.parseInt(parts[0]));
            calendar.set(Calendar.MONTH, (Integer.parseInt(parts[1]) - 1));
            calendar.set(Calendar.YEAR, Integer.parseInt(parts[2]));

            return calendar.getTime();

        } catch (Exception e) {
            Log.e(TAG, "parseDate", e);
            return null;
        }
    }

    public static Date getDateWithoutTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        return calendar.getTime();
    }

    public static Date getDateAfterDays(Date currentDate, int days) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static String convertUTCTimeToLocal(String utcDateTime, String format) {
        try {
            DateFormat inputFormat = new SimpleDateFormat(format, Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT+0530"));
            Date dateIST = inputFormat.parse(utcDateTime);
            DateFormat inputFormat2 = new SimpleDateFormat(format, Locale.getDefault());
            inputFormat2.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance().getTimeZone().getID()));
            return inputFormat2.format(dateIST);
        } catch (Exception e) {
            return utcDateTime;
        }
    }


    public static String splitString(String serverString) {
        String[] date;
        String dateString = "";
        try {
            date = serverString.split(" ", 4);
            dateString = date[0] + " " + date[1] + " " + date[2];
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
        return dateString;
    }

    public static String getDateString(String string) {
        String outputString = string;
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = inputFormat.parse(outputString);
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            outputString = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return outputString;
    }

    public static long getDaysDiff(OrderDTO dto) {
        String currentDate = DateUtils.getCurrentDateString();
//        String journeyDate = DateUtils.convertStringToFormatedDate(dto.getDepartureDate(), "dd MMM yyyy", "yyyy-MM-dd");
//        Date d1 = DateUtils.convertStringToDate(currentDate, "yyyy-MM-dd");
//        Date d2 = DateUtils.convertStringToDate(dto.getDepartureDate(), "yyyy-MM-dd");
//        long diff = d2.getTime() - d1.getTime();

        return TimeUnit.DAYS.convert(0, TimeUnit.MILLISECONDS);
    }

    public static long getDaysDiff(String FromDate, String ToDate) {
        String fromDate = DateUtils.convertStringToFormatedDate(FromDate, "dd MMM yyyy", "yyyy-MM-dd");
        String toDate = DateUtils.convertStringToFormatedDate(ToDate, "dd MMM yyyy", "yyyy-MM-dd");
        Date d1 = DateUtils.convertStringToDate(fromDate, "yyyy-MM-dd");
        Date d2 = DateUtils.convertStringToDate(toDate, "yyyy-MM-dd");
        long diff = d2.getTime() - d1.getTime();

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long getTourDaysDiff(Date max, Date min) {
      /*  Date d1 = DateUtils.convertStringToDate(FromDate, "dd/MM/yyyy");
        Date d2 = DateUtils.convertStringToDate(ToDate, "dd/MM/yyyy");*/
        long diff = max.getTime() - min.getTime();

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String convertDateToString(Date date, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return formatter.format(date);
    }

    public static String convertStringToFormatedDate(String dateStr, String inFormat, String outFormat) {
        if(StringUtils.isBlank(dateStr)){
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(inFormat, Locale.getDefault());
        if(outFormat.contains("Z"))
           dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT_MONTH, Locale.getDefault());
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException ex) {
                return dateStr;
            }
        }
        dateFormat = new SimpleDateFormat(outFormat, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date convertStringToDate(String date, String dateFormat) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Date dateToString = null;
        try {
            dateToString = formatter.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return dateToString;
    }

    public static String getCurrentDate() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            formattedDate = df.format(calendar.getTime());
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            return formattedDate;
        }

        return formattedDate;
    }

    public static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    public static String getCurrentDateString() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            formattedDate = df.format(calendar.getTime());
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            return formattedDate;
        }

        return formattedDate;
    }

    public static String getCurrentMonthEndDateString() {
        Calendar calendar_end = DateUtils.getCalendarForNow();
        calendar_end.set(Calendar.DAY_OF_MONTH, calendar_end.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ending = calendar_end.getTime();
        String eDate = DateUtils.convertDateToString(ending, "yyyy-MM-dd");//or sdf.format(end);

        return eDate;
    }

    public static String getEndMonthDateString(Date date) {
        Calendar calendar_end = Calendar.getInstance();
        calendar_end.setTime(date);
        calendar_end.set(Calendar.DAY_OF_MONTH, calendar_end.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ending = calendar_end.getTime();
        String eDate = DateUtils.convertDateToString(ending, "yyyy-MM-dd");//or sdf.format(end);

        return eDate;
    }

    public static String getStartMonthDateString(Date date) {
        Calendar calendar_end = Calendar.getInstance();
        calendar_end.setTime(date);
        calendar_end.set(Calendar.DAY_OF_MONTH, calendar_end.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date ending = calendar_end.getTime();
        //String eDate = DateUtils.convertDateToString(ending, "dd-MM-yyyy");//or sdf.format(end);
        //String  eDate =  DateFormat.getDateInstance(DateFormat.SHORT).format(ending);

        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String eDate = outputFormat.format(ending);

        return eDate;
    }

    public static String getEndMonthDateReceiptString(Date date) {
        Calendar calendar_end = Calendar.getInstance();
        calendar_end.setTime(date);
        calendar_end.set(Calendar.DAY_OF_MONTH, calendar_end.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ending = calendar_end.getTime();
        // String eDate = DateUtils.convertDateToString(ending, "dd-MM-yyyy");//or sdf.format(end);
        // String  eDate =  DateFormat.getDateInstance(DateFormat.SHORT).format(ending);
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String eDate = outputFormat.format(ending);

        return eDate;
    }

    public static String getNextMonthEndDateString() {
        Calendar calendar_end = DateUtils.getCalendarForNow();
        calendar_end.add(Calendar.MONTH, 1);
        calendar_end.set(Calendar.DAY_OF_MONTH, calendar_end.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ending = calendar_end.getTime();
        String eDate = DateUtils.convertDateToString(ending, "yyyy-MM-dd");//or sdf.format(end);

        return eDate;
    }

    public static String getNext12MonthDateString() {
        Calendar calendar_end = DateUtils.getCalendarForNow();
        calendar_end.add(Calendar.MONTH, 12);
        Date ending = calendar_end.getTime();
        String eDate = DateUtils.convertDateToString(ending, "yyyy-MM-dd");//or sdf.format(end);

        return eDate;
    }

    public static boolean isValidDate(String inDate) {
        if (checkDate(inDate)) {
            return true;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean checkDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static long stringToLongDate(String dateString) {
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(dateString);
            startDate = date.getTime();
        } catch (ParseException e) {
            Log.e(TAG, "stringToLongDate: ", e);
        }
        return startDate;
    }

    public static String getCurrentTime() {
//        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFmt.format(new Date());
    }

    public static String getCurrentMsUTC() {
        return String.valueOf(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
    }


    public static String getString(String format) {
        SimpleDateFormat outputFmt = new SimpleDateFormat(format, Locale.getDefault());
        return outputFmt.format(new Date());
    }

    public static String getDateInUTCFormat(Date time) {
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFmt.format(time);
    }

    public static String getOnlyDate() {
        SimpleDateFormat outputFmt = new SimpleDateFormat("yy-MM-dd", Locale.getDefault());
        return outputFmt.format(new Date());
    }

    public static String getOnlyTime() {
        SimpleDateFormat outputFmt = new SimpleDateFormat("HHmmss", Locale.getDefault());
        return outputFmt.format(new Date());
    }

    /**
     * Return date in specified format.
     *
     * @param milliSeconds Date in milliseconds
     * @param dateFormat   Date format
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
//        return formatter.format(calendar.getTime());
        return formatter.format(new Date(milliSeconds));
    }

    public static String getTime(int hr, int min, int sec) {
        StringBuilder builder = new StringBuilder();

        if (hr == 0) builder.append("00:");
        else if (hr > 0 && hr <= 9) builder.append("0").append(hr).append(":");
        else builder.append(hr).append(":");

        if (min == 0) builder.append("00");
        else if (min > 0 && min <= 9) builder.append("0").append(min);
        else builder.append(min);

        return builder.toString();
    }

    private static String calDur(String in, String ot) {
        Date inTime = DateUtils.convertStringToDate(in, DateUtils.UTC_DATE_TIME_FORMAT);
        Date outTime = DateUtils.convertStringToDate(ot, DateUtils.UTC_DATE_TIME_FORMAT);

        long diff = outTime.getTime() - inTime.getTime();

        int hr = (int) TimeUnit.MILLISECONDS.toHours(diff);
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(diff) % 60;

        int initMins, mins;
        if (sec > 0) {
            initMins = (int) TimeUnit.MILLISECONDS.toMinutes(diff) + 1;
            mins = initMins % 60;
            if (initMins / 60 != hr) {
                hr++;
            }
        } else {
            initMins = (int) TimeUnit.MILLISECONDS.toMinutes(diff);
            mins = initMins % 60;
        }

        return DateUtils.getTime(hr, mins, sec);
    }

    public static String getFormattedOutTime(String in, String duration) {
        Date inDate = convertStringToDate(in, "yyyy-MM-dd HH:mm");
        long inLongs = inDate.getTime();
        long durationLongs;
        String[] dur = duration.split(":");
        int hr = Integer.parseInt(dur[0]);
        int min = Integer.parseInt(dur[1]);
        durationLongs = (((hr * 60) + min) * 60) * 1000;
        long finalOut = inLongs + durationLongs;
        return getDate(finalOut, "yyyy-MM-dd hh:mm a");
    }

    public static boolean dateAfterOrEqualCurrentDate(String selectedDate) {
        if (StringUtils.isBlank(selectedDate)) {
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(SUPPORTED_DATE_FORMATS[1]);
        Date date = null;
        Date currentDate = new Date();
        try {
            date = formatter.parse(formatter.format(DateUtils.convertStringToDate(selectedDate, DateUtils.SUPPORTED_DATE_FORMATS[1])));
            currentDate = formatter.parse(formatter.format(currentDate));
        } catch (Exception e) {
            Log.e(TAG, "dateAfterOrEqualCurrentDate: ", e);
            return false;
        }
        return date.after(currentDate) || date.equals(currentDate);
    }
}
