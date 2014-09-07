package controllers;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import play.Logger;

import java.util.HashMap;
import java.util.Map;

public class Application extends CommonController {

    public static void index() {
        render();
    }

    public static void renderPlay() {
        render();
    }

    public static void gather() {
        renderTemplate("Application/gather.xml");
    }

    public static void readCallerInputDigit(String Digits) {
        Logger.debug("Reading Out Fizz Buzz..");
        String fizBuzz = FizzBuzzUtil.speakFizBuzz(Integer.valueOf(Digits));
        renderTemplate("Application/speakfiz.xml", fizBuzz);
    }

    public static void dial() {
        render();
    }

    public static void callMe(String phone) {
        TwilioRestClient twilioRestClient = TwilioService.getInstance();

        Account mainAccount = twilioRestClient.getAccount();

        CallFactory callFactory = mainAccount.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("To", "4084497930");
        callParams.put("From", TwilBuzzConstants.TWILIO_PHONE_NUM);
        callParams.put("Url", TwilBuzzConstants.BASE_URL+"/"+"gather");
        Call call = null;
        try {
            call = callFactory.create(callParams);
            Logger.debug(call.getSid());
        } catch (TwilioRestException e) {
            Logger.error("Error Occurred while placing call to - " + phone);
            e.printStackTrace();
        }
    }
}