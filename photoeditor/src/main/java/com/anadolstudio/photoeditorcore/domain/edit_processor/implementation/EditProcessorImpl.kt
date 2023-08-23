package com.anadolstudio.photoeditorcore.domain.edit_processor.implementation

import android.content.Context
import com.anadolstudio.photoeditorcore.domain.edit_processor.EditProcessor
import com.anadolstudio.photoeditorcore.domain.edit_processor.EditorView
import java.io.File

class EditProcessorImpl : EditProcessor {

    private companion object {
        const val MAX_SIDE = 2560
        const val MAX_PREVIEW_SIDE = MAX_SIDE / 2
    }

    override fun bindPhotoEditorView(view: EditorView) {
        TODO("Not yet implemented")
    }

    override fun loadImage(context: Context, path: String) {
        TODO("Not yet implemented")
    }

    override fun savePhoto(context: Context, file: File) {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        TODO("Not yet implemented")
    }
}
