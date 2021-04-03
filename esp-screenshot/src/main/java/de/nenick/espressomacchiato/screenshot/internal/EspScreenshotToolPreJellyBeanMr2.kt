package de.nenick.espressomacchiato.screenshot.internal

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Looper
import android.view.View
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Field
import java.util.*

internal class EspScreenshotToolPreJellyBeanMr2 {

    fun takeScreenShot(screenshot: File) {
        val start = System.currentTimeMillis()
        var recentDecorView: View?
        while (getRecentDecorView(windowDecorViews).also { recentDecorView = it } == null) {
            if (System.currentTimeMillis() - start > 30000) {
                break
            }
            Thread.sleep(100)
        }
        takeScreenShot(recentDecorView, screenshot)
    }

    internal class BitmapResult {
        var bitmap: Bitmap? = null
    }

    companion object {
        val windowDecorViews: ArrayList<View?>?
            get() {
                val viewsField: Field
                val instanceField: Field
                return try {
                    viewsField = windowManager.getDeclaredField("mViews")
                    instanceField = windowManager.getDeclaredField(windowManagerString)
                    viewsField.isAccessible = true
                    instanceField.isAccessible = true
                    val instance = instanceField[null]
                    val viewArrayList: ArrayList<View?>
                    // strange on local pc viewsField.get returns an array list
                    // on build agent it return just an array
                    viewArrayList = try {
                        viewsField[instance] as ArrayList<View?>
                    } catch (e: ClassCastException) {
                        // strange on local pc viewsField.get returns an array list
                        // on build agent it return just an array
                        arrayListOf(*(viewsField[instance] as Array<View?>))
                    }
                    viewArrayList
                } catch (e: Exception) {
                    throw IllegalStateException(e)
                }
            }

        private val windowManager: Class<*>
            get() = try {
                val windowManagerClassName: String = if (Build.VERSION.SDK_INT >= 17) {
                    "android.view.WindowManagerGlobal"
                } else {
                    "android.view.WindowManagerImpl"
                }
                Class.forName(windowManagerClassName)
            } catch (e: ClassNotFoundException) {
                throw IllegalStateException(e)
            } catch (e: SecurityException) {
                throw IllegalStateException(e)
            }

        private val windowManagerString: String
            get() = when {
                Build.VERSION.SDK_INT >= 17 -> {
                    "sDefaultWindowManager"
                }
                Build.VERSION.SDK_INT >= 13 -> {
                    "sWindowManager"
                }
                else -> {
                    "mWindowManager"
                }
            }

        fun getRecentDecorView(views: ArrayList<View?>?): View? {
            check(!(views == null || views.isEmpty())) { "Error in getRecentDecorView: 0 views passed in." }
            val decorViews = arrayOfNulls<View>(views.size)
            var i = 0
            var view: View?
            for (j in views.indices) {
                view = views[j]
                val decorViewClass: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    "com.android.internal.policy.PhoneWindow\$DecorView"
                } else {
                    "com.android.internal.policy.impl.PhoneWindow\$DecorView"
                }
                if (view != null && view.javaClass.name == decorViewClass) {
                    decorViews[i] = view
                    i++
                }
            }
            return getRecentContainer(decorViews)
        }

        private fun getRecentContainer(views: Array<View?>): View? {
            var container: View? = null
            var drawingTime: Long = 0
            for (view in views) {
                if (view != null && view.isShown && view.hasWindowFocus() && view.drawingTime > drawingTime) {
                    container = view
                    drawingTime = view.drawingTime
                }
            }
            return container
        }

        private fun takeScreenShot(view: View?, screenshot: File) {
            checkNotNull(view) { "takeScreenShot: view from getWindowDecorViews() is null." }
            val screen = BitmapResult()
            if (Looper.myLooper() == Looper.getMainLooper()) {
                // On main thread already, Just Do It™.
                screen.bitmap = takeScreenShot(view)
            } else {
                // On a background thread, post to main.
                InstrumentationRegistry.getInstrumentation().runOnMainSync { screen.bitmap = takeScreenShot(view) }
            }
            val fos = FileOutputStream(screenshot)
            screen.bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //screenShotFile.setReadable(true, false);
            fos.flush()
            fos.close()
        }

        private fun takeScreenShot(view: View): Bitmap {
            val bitmap = Bitmap.createBitmap(view.width,
                    view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }
    }
}