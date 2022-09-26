package com.anadolstudio.photoeditorcore.domain.edit_processor.implementation

import com.anadolstudio.core.livedata.SingleCustomEvent

sealed class EditProcessorEvent : SingleCustomEvent() {

    class Loading(val isLoading: Boolean) : EditProcessorEvent()

    class Error(val error: Throwable) : EditProcessorEvent()

    sealed class Success : EditProcessorEvent() {

        object ImageLoaded : Success()

        class ImageSaved(val path: String) : Success()

    }

}
