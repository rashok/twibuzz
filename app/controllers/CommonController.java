package controllers;

import play.mvc.Before;
import play.mvc.Controller;

/**
 * Common Controller. Contains interceptors to validate headers.
 */
public class CommonController extends Controller {

    @Before(only = {"Application.play"})
    static void validateHeaders() {
        if (!request.headers.containsKey("X-Twilio-Signature ")) {
            notFound();
        }
    }
}
