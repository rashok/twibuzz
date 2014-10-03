TwiBuzz App
=========

This is a simple web app which uses [Twilio] API to make phone calls, log call history, replay calls etc.

Version
----

1.0

Tech
-----------

This App was developed using the below frameworks:

* [Play Framework 1.2.7] - High Velocity Web Framework for Java & Scala
* [Twitter Bootstrap 3.2] - great UI boilerplate for modern web apps
* [JQuery Datatables]
* jQuery
* Heroku - For deployment.
* PostGresSQL - Postgres Database add on from Heroku.

Installation
--------------

Before proceeding, install play framework 1.2.7. See installation note here - https://www.playframework.com/documentation/1.2.x/install

Once Play has been installed, you can clone this app and run locally.

```sh
git clone [https://github.com/rashok/twibuzz.git] twibuzz
cd twibuzz
play dependencies
play run
```
#### Configuration

Create a Twilio account and edit the conf/application.conf file with the Twilio API key and other info.

```sh
twilio.accountSid=your-account-id
twilio.authToken=your-auth-token
twilio.number=your-twilio-number
```

##Deployment

The app has been deployed on Heroku - http://twilio-buzz-app.herokuapp.com


[Twitter Bootstrap 3.2]:http://getbootstrap.com/
[Twilio]:https://www.twilio.com/
[JQuery Datatables]:http://www.datatables.net/
[Play Framework 1.2.7]:https://www.playframework.com/
