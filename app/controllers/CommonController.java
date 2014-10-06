package controllers;

import play.mvc.Before;
import play.mvc.Controller;

/**
 * Common Controller. Contains interceptors to validate headers.
 */
public class CommonController extends Controller {

    @Before(only = {"Application.gather"})
    static void validateHeaders() {
        if (!request.headers.containsKey("x-twilio-signature")) {
            notFound();
        }
    }
}
