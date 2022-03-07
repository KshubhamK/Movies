package com.shubham.movies;

import static com.shubham.movies.utils.AppConstants.API_KEY;
import static com.shubham.movies.utils.AppConstants.CONVERTED_DATE_FORMAT;
import static com.shubham.movies.utils.AppConstants.ORIGINAL_DATE_FORMAT;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void checkAPIKeyAvailableOrNot() {
        if (!API_KEY.equals("")) {
            System.out.println("API_KEY " + API_KEY);
        }
        else {
            System.out.println("API_KEY not found");
        }
    }

    @Test
    public void convertDateFormat() {
        String newTime;
        SimpleDateFormat actual = new SimpleDateFormat(ORIGINAL_DATE_FORMAT);
        SimpleDateFormat target = new SimpleDateFormat(CONVERTED_DATE_FORMAT);
        Date date;
        try {
            date = actual.parse("2022-03-03");
            newTime = target.format(date);
            System.out.println("newTime " + newTime);
        } catch (ParseException e) {
            System.out.println("exception caught exception");
            e.printStackTrace();
        }
    }

    @Test
    public void convertFromMinutesToHours() {
        String minutes = Integer.toString(248 % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        System.out.println("convertedTime  " + (248 / 60) + "h " + minutes + "m");
    }

    @Test
    public void currencyFormat() {
        String amount = "12380000000";
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        System.out.println("convertedCurrency  " + formatter.format(Double.parseDouble(amount)));
    }
}