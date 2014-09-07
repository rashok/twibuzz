package controllers;

import com.twilio.sdk.TwilioRestClient;

/**
 * Created by rashok on 9/7/14.
 */
public class TwilioService {
    private static final TwilioRestClient twilioRestClientInstance = new TwilioRestClient(TwilBuzzConstants.TWILIO_ACC_SID, TwilBuzzConstants.TWILIO_AUTH_ID);

    public static TwilioRestClient getInstance() {
        return twilioRestClientInstance;
    }
}
