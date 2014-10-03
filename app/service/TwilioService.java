package service;

import com.twilio.sdk.TwilioRestClient;
import models.TwilBuzzConstants;

public class TwilioService {

    private static final TwilioRestClient twilioRestClientInstance = new TwilioRestClient(TwilBuzzConstants.TWILIO_ACC_SID,TwilBuzzConstants.TWILIO_AUTH_ID);

    private TwilioService() {}

    public static TwilioRestClient getInstance() {
        return twilioRestClientInstance;
    }
}
