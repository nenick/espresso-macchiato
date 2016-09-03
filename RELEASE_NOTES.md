#### New in release 0.6.0

elements

* EspRecyclerView - itemByVisibleIndex removed, itemByItemIndex renamed to itemByIndex
* EspRecyclerViewItem - reimplement to access item view content with adapter index
* EspRecyclerViewItem - assertion for displayed/hidden/exist/notExist
* EspAppBarLayout - collapse or expand toolbar programmatically

mocks -> intents

* EspGalleryMock -> EspGalleryStub
* EspCameraMock -> EspCameraStub
* EspContactPickerMock -> EspContactStub

tools

* EspFilesTool - for preparing test data

dependencies

test base

matcher