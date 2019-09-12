package com.restapi.application.file

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.restapi.application.database.DDFRepository
import com.restapi.application.database.DeviceRepository
import com.restapi.application.ddf.DDFFile
import com.restapi.application.ddf.Device
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.FileNotFoundException
import java.lang.Exception

@Service
class FileServer{

    private val logger = LoggerFactory.getLogger(FileServer::class.java)
    private val gson = Gson()

    @Autowired
    private  var repository: DDFRepository? = null

    @Autowired
    private  var deviceRepository: DeviceRepository? = null

    private fun checkRootDirectory(fileName: String): String =
            if (fileName.startsWith(getRootDirectory())) fileName else addRootDirectory(fileName = fileName)

    private fun addRootDirectory(fileName: String): String {
        logger.error("No user home directory found for file $fileName! Adding directory root path ...")
        return "C:\\tests\\DDF\\$fileName"
    }

    private fun getRootDirectory(): String = "C:\\tests\\DDF\\"

    @Synchronized
    private fun write(fileName: String){
        val file = findFile(fileName = fileName)
        FileManipulator.write(fileName = fileName, text = file.getFileText()!!)
        logger.info("File '$fileName' saved")
    }

    @Synchronized
    private fun findFile(fileName: String): DDFFile {
        logger.info("Finding file '$fileName' in storage ...")
        val files = repository!!.findAll()

        for (file in files) {
            file

            if (file.getFileName() == fileName) {
                logger.info("File '$fileName' found in storage")
                return file
            }
        }

        throw Exception("File '$fileName' not found!")
    }

    fun saveInDatabase(fileName: String, ddf: Device){
        logger.info("Saving file '$fileName' with text '${gson.toJson(ddf)}' in database...")

        //save File
        /**
        val ddfFile = DDFFile()
        ddfFile.setFileName(fileName = fileName)
        ddfFile.setFileText(fileText = gson.toJson(ddf))
        logger.info(ddfFile.getFileName())
        logger.info(ddfFile.getFileText())
        repository!!.save(ddfFile)
        **/
        deviceRepository!!.save(ddf)
        logger.info("File '$fileName' saved")
    }

    fun saveInStorage(fileName: String,ddf: Device){
        FileManipulator.create(fileName = fileName)
        logger.info("File $fileName created successfully!")

        var formattedText = GsonBuilder().setPrettyPrinting().create().toJson(ddf)

        FileManipulator.write(fileName,formattedText)
    }

    fun saveFile(ddf: Device){
        logger.info("saving file")
        try{
            var checkedFileName = checkRootDirectory(ddf.getDdfFileName().toString())
            checkedFileName = ExtensionHandler.checkForFileExtension(checkedFileName)
            saveInDatabase(fileName = checkedFileName, ddf = ddf)
            saveInStorage(fileName = checkedFileName, ddf = ddf)
        }catch(ex: Exception){
            logger.error(ex.message)
            throw Exception()
        }
    }

    @Synchronized
    fun writeSingleFile(fileName: String) {
        try {
            logger.info("Saving all changes into file '$fileName' ...")
            write(fileName = fileName)
        } catch (ex: FileNotFoundException) {
            logger.error("Error while writing file!\n${ex.message}")
            throw FileNotFoundException()
        } catch (ex: Exception) {
            logger.error("Error while writing file!\n${ex.message}")
            throw Exception()
        }
    }

    @Synchronized
    fun replaceUnwantedChars(jsonArray: JSONArray): String = replaceUnwantedChars(stringObject = jsonArray.toString())

    @Synchronized
    fun replaceUnwantedChars(stringObject: String): String = stringObject
            .replace(" ", "")
            .replace("[\"{", "[{")
            .replace("}\"]", "}]")
            .replace("}\",", "},")
            .replace(",\"{", ",{")

}