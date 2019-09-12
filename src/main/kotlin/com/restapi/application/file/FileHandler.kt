package com.restapi.application.file

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.restapi.application.ddf.Device
import com.restapi.application.hash.Hash
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.io.FileNotFoundException
import java.lang.Exception

@Service
class FileHandler{

    private val logger = LoggerFactory.getLogger(FileHandler::class.java)

    @Autowired
    private var fileServer: FileServer = FileServer()

    @Synchronized
    fun createFile(ddf: Device): Any {
        return try {
            var gson = Gson()
            var fileName = Hash.hash(gson.toJson(ddf))
            ddf.setDdfFileName(ddfFileName = fileName)
            logger.info("Executing command 'Save' ...")
            fileServer.saveFile(ddf = ddf)
            ResponseEntity("File '$fileName' saved", HttpStatus.OK)
        } catch (ex: FileNotFoundException) {
            logger.error("Error while executing command 'Save'!\n${ex.message}")
            "error404"

        } catch (ex: Exception) {
            logger.error("Error while executing command 'Save'!\n${ex.message}")
            "error500"
        }
    }


    @Synchronized
    fun save(fileName: String): Any {
        return try {
            logger.info("Executing command 'Save' ...")
            fileServer.writeSingleFile(fileName = fileName)
            ResponseEntity("File '$fileName' saved", HttpStatus.OK)
        } catch (ex: FileNotFoundException) {
            logger.error("Error while executing command 'Save'!\n${ex.message}")
            "error404"
        } catch (ex: Exception) {
            logger.error("Error while executing command 'Save'!\n${ex.message}")
            "error500"
        }
    }
}

