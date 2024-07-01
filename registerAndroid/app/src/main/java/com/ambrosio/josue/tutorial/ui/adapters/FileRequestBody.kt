package com.ambrosio.josue.tutorial.ui.adapters

import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink

class FileRequestBody(
    private val contentResolver: ContentResolver,
    private val fileUri: Uri,
    private val onProgressUpdate: (Long, Long) -> Unit
) : RequestBody() {

    private val contentType = contentResolver.getType(fileUri)?.toMediaTypeOrNull()

    override fun contentType(): okhttp3.MediaType? {
        return contentType
    }

    override fun contentLength(): Long {
        return contentResolver.openFileDescriptor(fileUri, "r")?.use {
            it.statSize
        } ?: 0
    }

    override fun writeTo(sink: BufferedSink) {
        contentResolver.openInputStream(fileUri)?.use { inputStream ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var bytesRead: Int
            val totalLength = contentLength()
            var bytesWritten = 0L

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                sink.write(buffer, 0, bytesRead)
                bytesWritten += bytesRead
                onProgressUpdate(bytesWritten, totalLength)
            }
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }
}

fun FileRequestBody.toMultipart(fileName: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData("archivo", fileName, this)
}