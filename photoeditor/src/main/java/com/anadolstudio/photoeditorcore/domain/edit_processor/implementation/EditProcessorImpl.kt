package com.anadolstudio.photoeditorcore.domain.edit_processor.implementation

import android.content.Context
import android.graphics.Bitmap
import com.anadolstudio.core.bitmap_util.BitmapDecoder
import com.anadolstudio.core.livedata.SingleLiveEvent
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.livedata.toImmutable
import com.anadolstudio.core.rx_util.quickSingleFrom
import com.anadolstudio.photoeditorcore.domain.edit_processor.EditProcessor
import com.anadolstudio.photoeditorcore.domain.edit_processor.EditorView
import com.anadolstudio.photoeditorcore.domain.edit_processor.PhotoEditException.FailedSaveException
import com.anadolstudio.photoeditorcore.domain.edit_processor.PhotoEditException.InvalidateBitmapException
import com.anadolstudio.photoeditorcore.domain.util.BitmapSaver
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.File

class EditProcessorImpl : EditProcessor {

    private companion object {
        const val MAX_SIDE = 2560
        const val MAX_PREVIEW_SIDE = MAX_SIDE / 2
    }

    private lateinit var editorView: EditorView
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val _editProcessorEvent = SingleLiveEvent<EditProcessorEvent>()
    private lateinit var bitmap: Bitmap

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
            this.bitmap = bitmap
        }.smartProcessorSubscribe(
                onSuccess = { _editProcessorEvent.onNext(EditProcessorEvent.Success.ImageLoaded) },
                onError = { _editProcessorEvent.onNext(EditProcessorEvent.Error(InvalidateBitmapException)) }
        ).disposeOnClear()
    }

    override fun savePhoto(context: Context, file: File) {
        _editProcessorEvent.onNext(EditProcessorEvent.Loading(true))

        quickSingleFrom {
            val parent = file.parent ?: throw FailedSaveException
            val nameDir = parent.substring(parent.lastIndexOf("/"), parent.length)

            return@quickSingleFrom BitmapSaver.Factory.save(context, bitmap, nameDir, file)
        }.smartProcessorSubscribe(
                onSuccess = { path -> _editProcessorEvent.onNext(EditProcessorEvent.Success.ImageSaved(path)) },
                onError = { _editProcessorEvent.onNext(EditProcessorEvent.Error(FailedSaveException)) }
        ).disposeOnClear()
    }

    override fun onCleared() = compositeDisposable.clear()

    private fun <T> Single<T>.smartProcessorSubscribe(
            onSuccess: (T) -> Unit,
            onError: ((Throwable) -> Unit)? = null
    ): Disposable = this.subscribe(
            { data ->
                onSuccess.invoke(data)
                _editProcessorEvent.onNext(EditProcessorEvent.Loading(false))
            },
            { error ->
                when (onError != null) {
                    true -> onError.invoke(error)
                    false -> _editProcessorEvent.onNext(EditProcessorEvent.Error(error))
                }

                _editProcessorEvent.onNext(EditProcessorEvent.Loading(false))
            }
    )

    protected fun Disposable.disposeOnClear(): Disposable {
        compositeDisposable.add(this)
        return this
    }

}
