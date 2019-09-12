package com.restapi.application.license

import com.restapi.application.error.Languages
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.util.*

object LicenseHandler {
    private val logger: Logger = LoggerFactory.getLogger(LicenseHandler::class.java)
    private val licenseSocket: LicenseSocket = LicenseSocket()
    private var commandKey: String? = getCommandKey()
    private var command: String = String()
    private var idKey: String = getIdKey()


    init {
        licenseSocket.start()
        logger.info("License Handler started")
    }

    private fun sendMessage(message: JSONObject) {
        try {
            licenseSocket.sendMessage(message = message.toString())
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    private fun sendMessage(message: JSONArray) {
        try {
            licenseSocket.sendMessage(message = message.toString())
        }catch (ex: Exception){
            logger.error(ex.message)
        }
        licenseSocket.sendMessage(message = message.toString())
    }

    @Synchronized
    fun sendLanguage(language: Languages) {
        try {
            val json = JSONObject()
            command = LicenseCommands.ChangeLanguage.name
            json.put(commandKey, command)

            val languageKey = getLanguageKey()
            json.put(languageKey, language)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }

    }

    @Synchronized
    fun commandGetAll() {
        try {
            val json = JSONObject()
            command = LicenseCommands.GetAllLicenses.name
            json.put(commandKey, command)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }

    }

    @Synchronized
    fun commandAddLicense(id: String, type: String, createDate: String, expirationDate: String) {
        try {
            val json = JSONObject()
            command = LicenseCommands.AddLicense.name
            json.put(commandKey, command)
            json.put(idKey, id)

            val typeKey = getTypeKey()
            json.put(typeKey, type)

            val createDateKey = getCreateDateKey()
            json.put(createDateKey, createDate)

            val expirationDateKey = getExpirationDateKey()
            json.put(expirationDateKey, expirationDate)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }

    }

    @Synchronized
    fun commandActivate(id: String) {
        try {
            val json = JSONObject()
            command = LicenseCommands.Activate.name
            json.put(commandKey, command)
            json.put(idKey, id)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandDeactivate(id: String) {
        try {
            val json = JSONObject()
            command = LicenseCommands.Deactivate.name
            json.put(commandKey, command)
            json.put(idKey, id)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    @Synchronized
    fun lockLicense(id: String) {
        try {
            val json = JSONObject()
            command = LicenseCommands.Lock.name
            json.put(commandKey, command)
            json.put(idKey, id)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun unlockLicense(id: String) {
        try {
            val json = JSONObject()
            command = LicenseCommands.Unlock.name
            json.put(commandKey, command)
            json.put(idKey, id)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    @Synchronized
    fun deleteLicense(id: String) {
        try {
            val json = JSONObject()
            command = LicenseCommands.Delete.name
            json.put(commandKey, command)
            json.put(idKey, id)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    @Synchronized
    fun getActiveLicense() {
        try {
            val json = JSONObject()
            command = LicenseCommands.GetActiveLicense.name
            json.put(commandKey, command)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    @Synchronized
    fun extendExpirationDate(id: String, expirationDate: String) {
        try {
            val json = JSONObject()
            command = LicenseCommands.ExtendExpirationDate.name
            json.put(commandKey, command)
            json.put(idKey, id)

            val expirationDateKey: String = getExpirationDateKey()
            json.put(expirationDateKey, expirationDate)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun importLicense(filepath: String) {
        val file = File(filepath)
        val jsonString = StringBuilder(String())

        try {
            val scanner = Scanner(file)

            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                jsonString.append(line).append("\n")
            }

            scanner.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        logger.info("License information: $jsonString")
        sendMessage(message = JSONObject(jsonString.toString()))
    }


    private fun getIdKey(): String = LicenseInfo.ID.name.toLowerCase()

    private fun getCommandKey(): String = LicenseCommands.Command.name.toLowerCase()

    private fun getCreateDateKey(): String = LicenseInfo.CreatedAt.name.toLowerCase()

    private fun getExpirationDateKey(): String = LicenseInfo.ExpiresAt.name.toLowerCase()

    private fun getTypeKey(): String = LicenseInfo.Type.name.toLowerCase()

    private fun getLanguageKey(): String = LicenseInfo.Language.name.toLowerCase()

    private fun getFullLicense(): String = LicenseInfo.Full.name.toLowerCase()

    private fun getExtendeLicense(): String = LicenseInfo.Extended.name.toLowerCase()

    private fun getAdvancedLicense(): String = LicenseInfo.Advanced.name.toLowerCase()

    fun stopConnection() {
        try {
            licenseSocket.stopConnection()
            licenseSocket.join()
            logger.info("Thread successfully stopped")
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }
}