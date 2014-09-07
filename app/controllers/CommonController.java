package controllers;

import com.twilio.sdk.TwilioRestClient;
import play.mvc.Controller;

/**
 * Created by rashok on 9/5/14.
 */
public class CommonController extends Controller {

    //    @play.mvc.Before(only = {"Application.play"})
    static void validateHeaders() {
        if (!request.headers.containsKey("X-Twilio-Signature ")) {
            notFound();
        }
    }
}
