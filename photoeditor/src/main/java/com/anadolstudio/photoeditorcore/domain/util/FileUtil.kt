package com.anadolstudio.photoeditorcore.domain.util

import android.os.Environment
import android.util.Log
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtil {

    private const val TAG = "FileUtil"
    private const val DEFAULT_FORMAT = "yyyy_MM_dd_HHmmss"

    fun getFileName(): String {
        val timeFormat: DateFormat = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())
        return "IMG_${timeFormat.format(Date())}.jpeg" // TODO JPEG?
    }

    fun createAppDir(nameDir: String, fileName: String = getFileName()): File {
        val directory = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .toString() + File.separator + nameDir
        )

        if (!directory.exists() && !directory.isDirectory) {
            // create empty directory
            if (!directory.mkdirs()) Log.d(TAG, "Unable to create app dir!")
        }

        return File(directory, fileName)
    }
}
