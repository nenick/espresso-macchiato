#### New in release 0.4.0

elements

* EspDialog - dismiss dialog but only if dialog config allow it
* EspPermissionDialog - stabilize for pre marshmallow
* EspPermissionDialog - use current localisation for button text
* (planed) EspAlertDialog - change prepared ids for standard alert dialog
* (planed) EspSupportAlertDialog - prepared ids for alert dialog from support package

tools

* EspPermissionsTool - stabilize for pre marshmallow
* EspScreenshotTool - take screenshots

test base

* EspressoTestBase - base for all test cases
* EspressoTestCase - base for default espresso tests
* EspressoIntentTestCase - base test case when for mocking intents
* EspCloseAllActivitiesFunction - use it to ensure that all started activities are finished
* EspDummyLauncherActivity - support back press at start activity
* EspScreenshotFailureHandler - take screenshot when test fails
* EspFindTestClassFunction - get the current test class and method name
