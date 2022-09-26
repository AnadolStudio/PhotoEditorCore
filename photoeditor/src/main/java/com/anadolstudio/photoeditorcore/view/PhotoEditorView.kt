package com.anadolstudio.photoeditorcore.view

import android.graphics.Bitmap
import android.widget.ImageView
import com.anadolstudio.photoeditorcore.domain.edit_processor.EditorView

class PhotoEditorView(private val imageView: ImageView) : EditorView {

    override fun setBitmap(bitmap: Bitmap) = imageView.setImageBitmap(bitmap)

}
