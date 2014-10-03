package controllers;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import models.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.http.HttpStatus;
import play.Logger;
import play.db.jpa.JPAPlugin;
import play.db.jpa.Transactional;
import play.mvc.Scope;
import service.TwilioService;
import utils.FizzBuzzUtil;
import utils.TwilioUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application extends CommonController {

    private final static ScheduledExecutorService callScheduler = Executors.newScheduledThreadPool(1);

    public static void index() {
        renderPlay();
    }

    public static void renderPlay() {
        render();
    }

    public static void renderDial() {
        render();
    }

    public static void renderDelay() {
        render();
    }

    public static void gather() {
        renderTemplate("Application/gather.xml");
    }

    public static void callMeNow(String phone) {
        callMe(phone, 0, false);
    }

    public static void callMeDelayed(String phone, int delay) {
        callMe(phone, delay, true);
    }


    public static void readCallerInputDigit(String Digits, String CallSid) {
        Logger.debug("Reading Out Fizz Buzz..");
        String fizBuzz = FizzBuzzUtil.speakFizBuzz(Integer.valueOf(Digits));

        //Twilio Say Segment Limit - Break down the string into segments with less than 4096 characters.
        String segment = WordUtils.wrap(fizBuzz, TwilBuzzConstants.TWILIO_SEGMENT_LIMIT, "\n", false);

        //Check the size of the segment. Responses in Twilio have to be less than 64KB.
        final byte[] utfBytes = segment.getBytes();
        ArrayList<String> saySegments = new ArrayList<String>();

        if (utfBytes.length > 65535) {
            saySegments.add("Input number is too large. Twilio Responses cannot be larger than 64 kilo bytes.");
        } else {
            StringTokenizer segmentTokens = new StringTokenizer(segment, "\n");
            while (segmentTokens.hasMoreElements()) {
                saySegments.add(segmentTokens.nextToken());
            }
        }

        CallLog callLog = CallLog.findByCallSId(CallSid);
        if (callLog != null) {
            callLog.input = Digits;
            callLog.save();
        }

        renderTemplate("Application/speakfiz.xml", saySegments);
    }

    private static void scheduleCall(final CallFactory callFactory, final CallSettings settings,
                                     final long delay, final boolean logHistory, final CallLog.CallType type) {
        final Runnable callerAction = new Runnable() {
            @Transactional
            public void run() {
                Logger.debug("Placing a call after a delay of - " + delay + " seconds");

                Call call = null;
                Map<String, String> callParams = CallSettings.getMap(settings);
                callParams.put("From", TwilBuzzConstants.TWILIO_PHONE_NUM);
                callParams.put("IfMachine", "Hangup");
                callParams.put("StatusCallback", TwilBuzzConstants.CALL_END_CALLBACK_URL + "?log=" + logHistory);
                callParams.put("StatusCallbackMethod", "POST");

                try {
                    call = callFactory.create(callParams);
                    if (logHistory) {
                        JPAPlugin.startTx(false);
                        CallLog.logCallHistory(call, delay, type);
                        JPAPlugin.closeTx(false);
                    }
                } catch (TwilioRestException e) {
                    Logger.error(e, "TwilioRestException Occurred while logging call history.");
                }
            }
        };

        callScheduler.schedule(callerAction, delay, TimeUnit.SECONDS);
    }

    private static void callMe(String phone, int delay, boolean log) {

        if (StringUtils.isEmpty(phone)) {
            renderJSON(new StatusMessage(HttpStatus.SC_BAD_REQUEST, "Please enter a phone number."));
        }

        if (delay < 0) {
            renderJSON(new StatusMessage(HttpStatus.SC_BAD_REQUEST, "Please enter a a valid delay time in seconds. Delay cannot be less than zero."));
        }

        if (!TwilioUtil.isPhoneValid(phone)) {
            renderJSON(new StatusMessage(HttpStatus.SC_BAD_REQUEST,
                    "Phone Number is invalid. Country Code has to be present with a plus (+) sign."));
        }

        TwilioRestClient twilioRestClient = TwilioService.getInstance();
        Account mainAccount = twilioRestClient.getAccount();
        CallFactory callFactory = mainAccount.getCallFactory();

        CallSettings settings = new CallSettings();
        settings.To = phone;
        settings.Url = TwilBuzzConstants.WELCOME_URL;

        scheduleCall(callFactory, settings, delay, log, CallLog.CallType.USER_REQUEST);
        renderJSON(new StatusMessage(HttpStatus.SC_OK, "Thank You. A call will be placed on the provided number."));
    }


    public static void getCallHistoryList() {
        Scope.Params params = request.params;
        int start = Integer.parseInt(params.get("iDisplayStart"));
        int fetchSize = Integer.parseInt(params.get("iDisplayLength"));
        int sortBy = Integer.parseInt(params.get("iSortCol_0"));
        String sortDir = params.get("sSortDir_0");

        Pagination pagination = new Pagination(start, fetchSize, sortBy, sortDir);
        DataTableModel result = CallLog.getCallLogsForDisplay(pagination);
        renderJSON(result);
    }

    public static void replayCall(String id) {
        Logger.debug("Replaying Call - " + id);
        CallLog callEntry = CallLog.findById(Long.valueOf(id));

        if (callEntry == null) {
            renderJSON(new StatusMessage(HttpStatus.SC_NO_CONTENT, "Unable to replay this call."));
        }

        if (StringUtils.isEmpty(callEntry.input)) {
            renderJSON(new StatusMessage(HttpStatus.SC_BAD_REQUEST, "This call cannot be replayed as there was no user input."));
        }

        TwilioRestClient twilioRestClient = TwilioService.getInstance();
        Account mainAccount = twilioRestClient.getAccount();
        CallFactory callFactory = mainAccount.getCallFactory();

        CallSettings settings = new CallSettings();
        settings.To = callEntry.who;
        settings.Url = TwilBuzzConstants.REPLAY_CALL+"?Digits=" + callEntry.input + "&CallSid=" + callEntry.callId;
        settings.SendDigits = callEntry.input;

        scheduleCall(callFactory, settings, callEntry.delay, true, CallLog.CallType.REPLAY);

        renderJSON(new StatusMessage(HttpStatus.SC_OK, "Thank You.The call will be replayed soon."));
    }

    /**
     * This is a common post call event handler. We need to log the call history only for the delayed phone buzz app. For all other calls,
     * nothing needs to be done.
     *
     * @param CallSid
     * @param Duration
     * @param CallStatus
     * @param log
     */
    public static void postCallEndEvent(String CallSid, String Duration, String CallStatus, boolean log) {
        if (log) {
            Logger.debug("Logging Call History after the call has ended.");
            CallLog callLog = CallLog.findByCallSId(CallSid);
            if (callLog != null) {
                callLog.duration = Duration;
                callLog.status = CallStatus;
                callLog.modified = new Date();
                callLog.save();
            }
        }
    }
}