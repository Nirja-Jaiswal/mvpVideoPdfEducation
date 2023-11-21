package com.Padhantueducation.CommonClasses;

///**
//* Created by Dhanashree N. Borade on 2019-12-19.
//* Copyright (c) 2019 LBM Infotech Pvt. Ltd. All rights reserved.
//*/

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    private Calendar myCalendar;
    private Activity activity;
    private Context context;
    private SetDateTime setDateListener;
    private int mHour, mMinute, mAmPm;

    public DateTimeUtils(Activity activity, Context context, SetDateTime setDateListener) {
        this.activity = activity;
        this.context = context;
        this.setDateListener = setDateListener;
    }

    public void getDate(final String dateFormat,String minDate, String maxDate) {
        myCalendar = Calendar.getInstance();
        DatePickerDialog mTimePicker;
        mTimePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                setDateListener.setDate(sdf.format(myCalendar.getTime()));
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));//Yes 24 hour time
       try {
           SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
           if(minDate!=null && !minDate.equals(""))
                mTimePicker.getDatePicker().setMinDate(sdf.parse(minDate).getTime());
           if(maxDate!=null && !maxDate.equals(""))
                mTimePicker.getDatePicker().setMaxDate(sdf.parse(maxDate).getTime());
       }catch (Exception e){
           e.printStackTrace();
       }
        mTimePicker.show();
    }

    public void getTime(final String timeFormat) {
        myCalendar = Calendar.getInstance();
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setDateListener.setTime(convert24to12hr(hourOfDay + ":" + minute));
            }
        }, mHour, mMinute, false);
        mTimePicker.show();
    }

    public static String convert24to12hr(String time) {
        String convertedTime = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            convertedTime = new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String getCurrentDate(String dateFormat){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String formattedDate = df.format(c);
        return formattedDate;
    }

    /*
     If time has am or pm then flag is true, else false
     * */
    public static String getTimeDifference(String startTime, String endTime, boolean flag) {
        if(startTime.contains("."))
            startTime = startTime.replace(".",":");
        if(startTime.contains("-"))
            startTime = startTime.replace(".","");
        if(endTime.contains("."))
            endTime = endTime.replace(".",":");
        if(endTime.contains("-"))
            endTime = endTime.replace(".","");
        SimpleDateFormat sdf = null;
        if (flag) {
            sdf = new SimpleDateFormat("hh:mm aa");
        } else {
            sdf = new SimpleDateFormat("hh:mm");
        }

        Date endDate = null;
        Date startDate = null;
        try {
            endDate = sdf.parse(endTime);
            startDate = sdf.parse(startTime);
            long millse = endDate.getTime() - startDate.getTime();
            long mills = Math.abs(millse);

            int Hours = (int) (mills / (1000 * 60 * 60));
            int Mins = (int) (mills / (1000 * 60)) % 60;
//            long Secs = (int) (mills / 1000) % 60;

            return (String.valueOf(Hours).length() == 1 ? "0" + String.valueOf(Hours) : String.valueOf(Hours))
                    + ":" +
                    (String.valueOf(Mins).length() == 1 ? "0" + String.valueOf(Mins) : String.valueOf(Mins));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        return "00:00";
    }

    public static boolean compareTime(String scheduleTime, String timeDifference) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date schedleTime = null;
        Date taskTime = null;
        try {
            schedleTime = sdf.parse(scheduleTime);
            taskTime = sdf.parse(timeDifference);
            boolean b = (schedleTime.compareTo(taskTime) > 0);
            return b;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int convertHrsToMin(String hrs) {
        int minuts = 0;
        if(hrs.contains("."))
            hrs = hrs.replace(".",":");
        if(hrs.contains("-"))
            hrs = hrs.replace(".","");
        String[] splitTime = hrs.split(":");
        int h = Integer.parseInt(splitTime[0]);
        int m = Integer.parseInt(splitTime[1]);
        minuts = (h * 60) + m;
        return minuts;
    }

    public static String convertMinTohrs(int min){
        int hours = min / 60; //since both are ints, you get an int
        int minutes = min % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String getDatabaseDateFormat(String date){
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String outputDateStr = date;
        try {
            Date d = inputFormat.parse(date);
            outputDateStr = outputFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDateStr;
    }

    public static String gethhmm(String time){
        DateFormat outputFormat = new SimpleDateFormat("hh:mm");
        DateFormat inputFormat = new SimpleDateFormat("hh:mm a");
        String outputTime = time;
        try {
            Date d = inputFormat.parse(time);
            outputTime = outputFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputTime;
    }

    public String getCountOfDays(String format, String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(startDate);
            expireCovertedDate = dateFormat.parse(endDate);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return String.valueOf((int)dayCount+1);
    }

    public interface SetDateTime {
        void setDate(String date);
        void setTime(String time);
    }
}
