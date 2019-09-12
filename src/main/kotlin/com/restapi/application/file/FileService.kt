package com.restapi.application.file

interface FileService {
    fun read(fileName: String): String?
    fun write(fileName: String, text: String)
    fun create(fileName: String): Boolean
    fun checkForExistingFile(fileName: String): Boolean
}