package com.anadolstudio.photoeditorcore.domain.photo_segmenter

sealed class SegmenterException(message: String) : Exception(message)

class EmptyMaskException : SegmenterException("Empty mask")
