package com.anadolstudio.photoeditorcore.domain.edit_processor

import android.content.Context

interface EditProcessor {

    fun bindPhotoEditorView(view: EditorView)

    fun loadImage(context: Context, path: String)

    fun onCleared()
}
