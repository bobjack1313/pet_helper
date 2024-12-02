package com.csce5430sec7proj.pethelper.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun saveImageToStorage(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "pet_images/${System.currentTimeMillis()}.jpg")
    file.parentFile?.mkdirs()
    file.outputStream().use { outputStream ->
        inputStream?.copyTo(outputStream)
    }
    return file.absolutePath
}
