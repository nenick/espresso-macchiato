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