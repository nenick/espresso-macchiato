#### New in release 0.6.0

elements

* EspView - longClick, doubleClick, assertIsPartiallyDisplayedOnScreen
* EspRecyclerView - itemByVisibleIndex removed, itemByItemIndex renamed to itemByIndex
* EspRecyclerView - support for testing LayoutManager
* EspRecyclerViewItem - reimplement to access item view content with adapter index
* EspRecyclerViewItem - assertion for displayed/hidden/exist/notExist
* EspAppBarLayout - collapse or expand toolbar programmatically and check his state
* EspDevice - helper to check current screen size for alternate execution paths (isScreenSizeEqualTo, isScreenSizeAtLeast, isScreenSizeAtMost)

mocks -> intents

* EspGalleryMock -> EspGalleryStub
* EspCameraMock -> EspCameraStub
* EspContactPickerMock -> EspContactStub

tools

* EspFilesTool - tool for preparing test data
* EspAppDataTool - BugFix for removing databases when app use WebView
* EspAppDataTool - BugFix when cacheDir().list() return null after cacheDir was deleted

matcher

* EspAllOfBuilder - more helper methods (withVisibility, withText)

dependencies

* support libraries - update 23 to 24.1.1