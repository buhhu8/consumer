package com.buhhu8.consumer.config;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(LocalDateTime dateTime) {
        return dateTime.format(dateFormat);
    }

    @Override
    public LocalDateTime unmarshal(String dateTime) {
        return LocalDateTime.parse(dateTime, dateFormat);
    }

    public static LocalDateTime parseDateTime(String s) {
        String inputModified = s.replace ( "T" , " " );
        return LocalDateTime.parse(inputModified, dateFormat);
    }

    public static String printDateTime(LocalDateTime dt) {
        return dt.format(dateFormat);
    }
}