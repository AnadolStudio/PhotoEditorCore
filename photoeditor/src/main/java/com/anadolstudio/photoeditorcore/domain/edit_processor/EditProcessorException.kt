package com.anadolstudio.photoeditorcore.domain.edit_processor

sealed class PhotoEditException : Exception() {

    object InvalidateBitmapException : PhotoEditException()

    object FailedSaveException : PhotoEditException()

}
