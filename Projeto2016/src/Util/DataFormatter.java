package Util;

import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataFormatter {
    public static String DateToString(Date date, String format){
        if(date != null) {
            return format == null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) : new SimpleDateFormat(format).format(date);
        }
        return "";
    }

    public static Calendar DateToCalendar(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static String byteToHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] HexStringtoByte(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
