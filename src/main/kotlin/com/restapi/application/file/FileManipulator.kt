package com.restapi.application.file

import java.io.File

object FileManipulator: FileService {
    @Synchronized
    override fun read(fileName: String): String = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

    @Synchronized
    override fun write(fileName: String, text: String) = File(fileName).writeText(text = text)

    @Synchronized
    override fun create(fileName: String): Boolean = File(fileName).createNewFile()

    @Synchronized
    override fun checkForExistingFile(fileName: String): Boolean = File(fileName).exists()
}