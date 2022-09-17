package com.anadolstudio.photoeditorcore.domain.edit_processor.implementation

import android.content.Context
import com.anadolstudio.core.bitmap_util.BitmapDecoder
import com.anadolstudio.core.livedata.SingleLiveEvent
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.livedata.toImmutable
import com.anadolstudio.core.rx_util.quickSingleFrom
import com.anadolstudio.core.rx_util.smartSubscribe
import com.anadolstudio.photoeditorcore.domain.edit_processor.EditProcessor
import com.anadolstudio.photoeditorcore.domain.edit_processor.EditorView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class EditProcessorImpl : EditProcessor {

    private companion object {
        const val MAX_SIDE = 2560
        const val MAX_PREVIEW_SIDE = MAX_SIDE / 2
    }

    private lateinit var editorView: EditorView
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val _editProcessorEvent = SingleLiveEvent<EditProcessorEvent>()
    val editProcessorEvent = _editProcessorEvent.toImmutable()

    // TODO Аннотация, на обязательность выозова этого метода
    override fun bindPhotoEditorView(view: EditorView) {
        editorView = view
    }

    override fun loadImage(context: Context, path: String) {
        _editProcessorEvent.onNext(EditProcessorEvent.Loading(true))

        quickSingleFrom {
            BitmapDecoder.Manager.decodeBitmapFromPath(
                    context = context,
                    path = path,
                    reqWidth = MAX_PREVIEW_SIDE,
                    reqHeight = MAX_PREVIEW_SIDE
            )
        }.doOnSuccess { bitmap ->
            editorView.setBitmap(bitmap)
            // TODO and save bitmap
        }.smartSubscribe(
                onSuccess = { _editProcessorEvent.onNext(EditProcessorEvent.Success.ImageLoaded) },
                onError = { error -> _editProcessorEvent.onNext(EditProcessorEvent.Error(error)) },
                onFinally = { _editProcessorEvent.onNext(EditProcessorEvent.Loading(false)) }
        ).disposeOnClear()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected fun Disposable.disposeOnClear(): Disposable {
        compositeDisposable.add(this)
        return this
    }

}
