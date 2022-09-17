package com.anadolstudio.photoeditorcore.domain.util

import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import kotlin.math.max

object DisplayUtil {

    fun getDefaultSize(): DisplayMetrics = Resources.getSystem().displayMetrics

    // TODO need Test
    fun workspaceSize(
            vararg views: View,
            checkWidth: Boolean = false,
            checkHeight: Boolean = true
    ): Point {
        val displayMetrics = getDefaultSize()

        return with(views) {
            val result = Point(displayMetrics.widthPixels, displayMetrics.heightPixels)

            if (isEmpty()) return result

            var width = 0
            var height = 0

            for (view in views) {
                if (view.visibility != View.VISIBLE) continue

                if (checkWidth) width += view.width
                if (checkHeight) height += view.height
            }

            result.run {
                x = max(x - width, 0)
                y = max(y - height, 0)
            }

            result
        }
    }
}
