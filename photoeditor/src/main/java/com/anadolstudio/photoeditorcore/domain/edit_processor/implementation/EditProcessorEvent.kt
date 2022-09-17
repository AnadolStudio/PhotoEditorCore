package com.anadolstudio.photoeditorcore.domain.edit_processor.implementation

sealed class EditProcessorEvent {

    class Loading(val isLoading: Boolean) : EditProcessorEvent()

    class Error(val error: Throwable) : EditProcessorEvent()

    sealed class Success : EditProcessorEvent() {

        object ImageLoaded : Success()



    }

}
