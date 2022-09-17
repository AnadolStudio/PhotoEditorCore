package com.anadolstudio.photoeditorcore.domain.edit_processor

import android.content.Context
import java.io.File

interface EditProcessor {

    fun bindPhotoEditorView(view: EditorView)

    fun loadImage(context: Context, path: String)

    fun savePhoto(context: Context, file: File)

    fun onCleared()
}
