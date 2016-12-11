# Planed for future releases

## Common stuff

* manage shared dependencies version central in root gradle
* reduced libs - split uiauomator / support / appcompat / espresso intents in own modules/libs
* JavaDoc - Elements / Mocks / Tools
* Package "internal" - Actions / Assertions / Matcher (not intended for public usage)
* error handler - for default assertions (current failure handler only called when using onView checks)
* contribute - avoid issues with git (read git tag only if necessary)
* provided com.android.support dependencies - avoid exclude them, so it use project under test versions

## New or extend elements

* All Elements - ensure for mostly all actions and checks, that the view is currently shown on screen
* EspListDialog - dialog with button list
* EspDateTimePicker - select date time
* EspWebView - basic elements for web view tests with espresso
* EspTextView - autocomplete checks and actions
* EspActionBar - click menu items / overflow menu / action mode / search
* EspToolbar - mostly same like actionbar
* EspRecyclerView - same like list
* EspSpinner - choose option / assertions
* EspViewPager - switch page / assertions visible fragment


## New or extend tools

* EspPermissionsTool - ensure permissions for multiple permissions group (more than one dialog)
* EspPermissionsTool - try getContext instead of getTargetContext (hope most permissions must only specified in test AndroidManifest.xml)

## New or extend mocks

* Mocks with static methods

## Update Support libraries dependencies

* lowest supported android version - changed from v8 to v9 because of support libraries since 24.2.0