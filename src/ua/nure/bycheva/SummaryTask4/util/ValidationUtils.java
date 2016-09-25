package ua.nure.bycheva.SummaryTask4.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yulia on 26.08.16.
 */
public abstract class ValidationUtils {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public ValidationUtils() {
    }

    public static boolean isPasswordCorrect(String value){
        return value.length() >= 4 && value.length() <= 10;
    }
    public static boolean isNulableOrEmptyString(String value){
        return value == null || value.isEmpty();
    }

    public static boolean isDecimal(String value) {
        if(isNulableOrEmptyString(value)){
            return false;
        }

        char[] number = value.toCharArray();
        boolean dot = false;
        for(int i = 0; i < number.length; i++){
            char c = number[i];
            if(c == '-' || c == '+'){
                if(i != 0){
                    return false;
                }
            }else if(c == '.'){
                if(dot){
                    return false;
                }
                dot = true;
            }else if(!(c >= '0' && c <='9')){
                return false;
            }
        }
        return true;
    }

    public static boolean isPasswordsEquals(String[] values){
        return values.length == 2 && values[0].equals(values[1]);
    }

    public static boolean isEmail(String value) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isInteger(String value) {
        if(isNulableOrEmptyString(value)){
            return false;
        }

        char[] number = value.toCharArray();
        boolean dot = false;
        for(int i = 0; i < number.length; i++){
            char c = number[i];
            if(c == '-' || c == '+'){
                if(i != 0){
                    return false;
                }
            }else if(c < '0' || c >'9'){
                return false;
            }
        }

        return true;
    }

    public static boolean isNegativeInteger(String value) {
        return Long.valueOf(value) < 0;
    }
}
