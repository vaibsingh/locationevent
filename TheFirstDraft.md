# Introduction #

So the idea is to use _Location Manager_ to provide the current location. Which will be used to calculate if an event is to be raised.


# The first draft #

## Notes ##
  * All features are subject to change :)
  * Remember to add support for i18N, take care of this at the detailed design
  * Confirm all needed/used parts of this document
  * Maybe the correct name would have been _Location Aware Profiles_
  * I will start the coding process at the weekend, so be tuned, because this document WILL change

## Feature Description ##
The idea of the software is to monitor current location in the background. The first version of the software will send a notifications to user about the current location, when the software detects that the current location is 100m to a waypoint. At the sametime the software will _mute_ the phone when close to the waypoint.

## Needed Permissions ##
  * android.permission.CALL\_PHONE
  * android.permission.SIGNAL\_PERSISTENT\_PROCESSES
  * android.permission.SYSTEM\_ALERT\_WINDOW


## Used Application Framework Components ##
  * Location Manager
  * Notification Manager
  * XMPP Service
  * View System
  * Content Providers
  * Resource Manager
  * Activity Manager


## Used Libraries ##
  * Surface Manager
  * SQLite
  * Media Framework
  * SGL


## Used Applications ##
  * Maps
  * Phone
  * Contacts
  * Home
  * Calendar


## Used Hardware Features ##
  * GPS
  * GSM Telephony
  * Bluetooth, EDGE, 3G, and WiFi