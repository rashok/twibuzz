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
* [Pagekite] - Localhost tunneling. Make localhost:9000 public!
* [Libphonenumber] - Google Library for phone number validation, formatting and parsing
* jQuery
* Heroku - For deployment.
* PostGresSQL - Postgres Database add on from Heroku.

Installation
--------------

Before proceeding, install play framework 1.2.7. See installation note here - https://www.playframework.com/documentation/1.2.x/install

Once Play has been installed, you can clone this app and run locally.

Install Pagekite to make localhost publicly available as http://yourname.pagekite.me. Read Installation Note - https://pagekite.net/downloads/. Set the pagekite Url in the application.conf file as below.

#### Configuration

Create a Twilio account and edit the conf/application.conf file with the Twilio API key and other info.

```sh
base.url=https://myname.pagekite.me
twilio.accountSid=your-account-id
twilio.authToken=your-auth-token
twilio.number=your-twilio-number
```

Create an Twiml app on the Twilio Dashboard and set the URLs as below.

```
Request URl - http://myname.pagekite.me/gather
Status Call Back - http://myname.pagekite.me/history
```

```sh
git clone https://github.com/rashok/twibuzz.git twibuzz
cd twibuzz
play dependencies
play run
```

Now, hit https://yourname.pagekite.me to see the app.


##Deployment

The app has been deployed on Heroku - http://twilio-buzz-app.herokuapp.com


[Twitter Bootstrap 3.2]:http://getbootstrap.com/
[Twilio]:https://www.twilio.com/
[JQuery Datatables]:http://www.datatables.net/
[Play Framework 1.2.7]:https://www.playframework.com/
[Pagekite]:https://pagekite.net/downloads/
[Libphonenumber]:https://code.google.com/p/libphonenumber/
