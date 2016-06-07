#### New in release 0.5.0

elements

* for mostly all - support template constructor
* EspPermissionDialog - use correct string resource for deny/accept buttons
* EspView - assert is hidden for not on screen or not full overlapped
* EspTextView - text color assertion
* EspEditText - inherit all EspTextView actions and assertions
* EspRecyclerView - action and assertions for RecyclerView
* EspRecyclerViewItem - action and assertions for RecyclerView items
* EspAdapterView - match item by index
* EspAdapterViewItem - match item by index

mocks

* EspGalleryMock - mock intents for gallery

tools

* EspScreenShotTool - compare images percentage

matcher

* EspDisplayedMatcher - fix isDisplayingAtLeast Matcher to support appcompat actionbar on android pre v11 versions

#### New in release 0.4.0

elements

* EspDialog - dismiss dialog but only if dialog config allow it
* EspPermissionDialog - stabilize for pre marshmallow
* EspPermissionDialog - use current localisation for button text
* EspSystemDialog - base to close dialogs with uiautomator if shown
* EspSystemAnrDialog - close anr dialog if shown
* EspSystemAerrDialog - close aerr dialog if shown
* EspAlertDialog - change prepared ids for standard alert dialog
* EspSupportAlertDialog - prepared ids for alert dialog from support package
* EspEditTextTest - with experimental all of builder
* EspDrawer - use real swipe actions instead of layout method calls

tools

* EspPermissionsTool - stabilize for pre marshmallow
* EspPermissionsTool - report if permission is not requested in manifest
* EspScreenshotTool - take screenshots
* EspContactTool - find uri will just return null if nothing found instead of throwing an exception
* EspResourceTool - tool to access android resources

test base

* EspressoTestBase - base for all espresso test cases (finish resumed activities, close system dialogs, take screenshot when test fails, ...)
* EspressoTestCase - base for default espresso tests
* EspressoIntentTestCase - base test case for mocking intents
* EspCloseAllActivitiesFunction - use it to ensure that all active activities are finished
* EspDummyLauncherActivity - support back press at start activity
* EspScreenshotFailureHandler - take screenshot when test fails
* EspFindTestClassFunction - get the current test class and method name

matcher

* EspAllOfBuilder - experimental fluent builder for all of matcher

#### New in release 0.3.0

elements

* EspView - assertions for displayed on screen / state is visible, hidden, gone
* EspEditText - assertion for hint text
* EspAdapterView - click / count / assert list items
* EspListView - click / count / assert list items

tools

* EspAppDataTool - clear preferences / database / file cache / file storage

#### New in release 0.2.0

elements

* All - extend find view strategy
* All - static factory methods to hide implementation details for changes
* EspView - click only visible views
* EspView - does not exist in view hierarchy assertion
* EspDevice - close soft keyboard and state assertions
* EspDialog - new element for dialog handling
* EspAlertDialog - new element for support v7 AlertDialog
* EspPermissionsDialog - accept / deny dialog with ui automator

mocks

* EspCameraMock - avoid real camera handling
* EspContactPickerMock - avoid real contact picker handling

tools

* EspContactTool - create / find / delete contacts
* EspPermissionsTool - check / force permissions state

dependencies

* junit - don't exclude junit, just ignore lint error
* espresso - update 2.2.1 to 2.2.2
* espresso-intents - support for intent mocks
* uiautomator - support to access non app content

#### New in release 0.1.1

Correct license information.

#### New in release 0.1.0

First basic functionality to test an android application.