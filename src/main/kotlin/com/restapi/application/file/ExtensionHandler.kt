package com.restapi.application.file

import org.slf4j.LoggerFactory

object ExtensionHandler {
    private val logger = LoggerFactory.getLogger(ExtensionHandler::class.java)

    fun checkForFileExtension(fileName: String): String {
        if (fileName.split(".").size == 2) {
            return fileName
        }

        return addFileExtension(fileName = fileName)
    }

    private fun addFileExtension(fileName: String): String {
        var file = fileName.replace("\n", "")
        logger.warn("Unsupported file extension detected for file $file!")
        file += ".txt"
        logger.info("New file name: $file")
        return file
    }
}