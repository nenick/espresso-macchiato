#### New in release 0.6.0

elements

* EspView - longClick, doubleClick, assertIsPartiallyDisplayedOnScreen
* EspRecyclerView - itemByVisibleIndex removed, itemByItemIndex renamed to itemByIndex
* EspRecyclerView - support for testing LayoutManager
* EspRecyclerViewItem - reimplement to access item view content with adapter index
* EspRecyclerViewItem - assertion for displayed/hidden/exist/notExist
* EspAppBarLayout - collapse or expand toolbar programmatically
* EspDevice - helper to check current screen size for alternate execution paths (isScreenSizeEqualTo, isScreenSizeAtLeast, isScreenSizeAtMost)

mocks -> intents

* EspGalleryMock -> EspGalleryStub
* EspCameraMock -> EspCameraStub
* EspContactPickerMock -> EspContactStub

tools

* EspFilesTool - tool for preparing test data
* EspAppDataTool - BugFix for removing databases when app use WebView

matcher

* EspAllOfBuilder - more helper methods (withVisibility, withText)

dependencies

test base

matcher