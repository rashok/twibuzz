package controllers;

import play.Play;

/**
 * Created by rashok on 9/5/14.
 */
public final class TwilBuzzConstants {

    public static final String TWILIO_ACC_SID = Play.configuration.getProperty("twilio.accountSid");
    public static final String TWILIO_AUTH_ID = Play.configuration.getProperty("twilio.authToken");
    public static final String TWILIO_PHONE_NUM = Play.configuration.getProperty("twilio.number");
    public static final String TWILIO_VOICE_PERSONA = Play.configuration.getProperty("twilio.voice");

    public static final String BASE_URL = Play.configuration.getProperty("base.url");
}
