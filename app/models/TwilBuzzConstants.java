package models;

import play.Play;

/**
 * Created by rashok on 9/5/14.
 */
public final class TwilBuzzConstants {

    public static final String TWILIO_ACC_SID = Play.configuration.getProperty("twilio.accountSid");
    public static final String TWILIO_AUTH_ID = Play.configuration.getProperty("twilio.authToken");
    public static final String TWILIO_PHONE_NUM = Play.configuration.getProperty("twilio.number");
    public static final String TWILIO_VOICE_PERSONA = Play.configuration.getProperty("twilio.voice");

    public static final int TWILIO_SEGMENT_LIMIT = 4096;

    public static final String BASE_URL = Play.configuration.getProperty("base.url");


    // Call History Table Indexes
    public static final int INDEX_CALL_ID = 0;
    public static final int INDEX_FROM = 1;
    public static final int INDEX_TO = 2;
    public static final int INDEX_STATUS = 3;
    public static final int INDEX_CREATED = 4;
    public static final int INDEX_DURATION = 5;
    public static final int INDEX_DELAY = 6;
    public static final int INDEX_INPUT = 7;
    public static final int INDEX_ID = 8;

}
