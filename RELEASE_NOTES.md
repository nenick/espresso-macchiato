#### New in release 0.4.0

elements

* EspDialog - dismiss dialog but only if dialog config allow it
* EspPermissionDialog - stabilize for pre marshmallow
* EspPermissionDialog - use current localisation for button text
* EspSystemDialog - bas for close dialogs with uiautomator if shown
* EspSystemAnrDialog - close anr dialog if shown
* EspSystemAerrDialog - close aerr dialog if shown
* EspAlertDialog - change prepared ids for standard alert dialog
* EspSupportAlertDialog - prepared ids for alert dialog from support package

tools

* EspPermissionsTool - stabilize for pre marshmallow
* EspPermissionsTool - report if permission is not requested in manifest
* EspScreenshotTool - take screenshots
* EspContactTool - find uri will just return null if nothing found instead of throwing an exception

test base

* EspressoTestBase - base for all espresso test cases (finish resumed activities, close anr dialogs, take screenshot when test fails)
* EspressoTestCase - base for default espresso tests
* EspressoIntentTestCase - base test case when for mocking intents
* EspCloseAllActivitiesFunction - use it to ensure that all started activities are finished
* EspDummyLauncherActivity - support back press at start activity
* EspScreenshotFailureHandler - take screenshot when test fails
* EspFindTestClassFunction - get the current test class and method name
