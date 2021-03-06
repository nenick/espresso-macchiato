# Overview Prepared Elements

One of the main goals of this library is to have a matching representation for all Android 
views. Every time your first attempt should be just to prepend `Esp` to the related view. 

* `Button -> EspButton`
* `AlertDialog -> EspAlertDialog`

### EspView

The base class for every view element in this library. It's like Android haves the `View` class
as base for all Android views. This may be a good starting point if you want to create your own 
[custom  element](ElementsCustom.md).

## Prepared Elements

### Core

* EspButton
* EspEditText
* EspTextView

### List

* EspListView
* EspListViewItem
* EspRecyclerView

### Dialog

* EspAlertDialog
* EspDialog
* EspDialogFragment