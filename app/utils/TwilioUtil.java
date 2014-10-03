package utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang.StringUtils;
import play.Logger;

/**
 * Util & Helper methods for validation etc.
 */
public class TwilioUtil {

    private static Phonenumber.PhoneNumber getPhoneNumber (String phone) {
        if (StringUtils.isEmpty(phone)) {
            return null;
        }
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number = null;
        try {
            number = phoneNumberUtil.parse(phone, "");
        } catch (NumberParseException e) {
            Logger.error("Exception occurred while parsing phone number - " +  phone + " " + e.getMessage());
            number = null;
        }
        return number;
    }

    public static boolean isPhoneValid(String phone) {

        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number = getPhoneNumber(phone);
        boolean numberValid = false;
        if (number != null) {
            numberValid = phoneNumberUtil.isValidNumber(number);
        }
        return numberValid;
    }

    public static String getPhoneAsE164 (String phone) {
        String phone164 = null;
        if (isPhoneValid(phone)) {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = getPhoneNumber(phone);
            phone164 = phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        }
        return phone164;
    }
}
