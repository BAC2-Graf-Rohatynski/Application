package com.restapi.application.command

import com.restapi.application.core.Synchronized
import com.restapi.application.dev.DevHelper
import com.restapi.application.error.Languages
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory



object CommandHandler{
    private val logger: Logger = LoggerFactory.getLogger(CommandHandler::class.java)
    private val commandSocket: CommandSocket = CommandSocket()
    private var key: String? = null
    private var command: String? = null

    init{
        try {
            commandSocket.start()
            logger.info("Command Handler started")
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    @Synchronized
    private fun sendMessage(message: JSONObject){
        try {
            commandSocket.sendMessage(message = message)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }


    fun commandNew(){
        val json = JSONObject()
        try {
            key = getCommandKey()
            command = getCommandNew()
            json.put(key,command)
            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandChangeShow(show: String) {
        try {
            val json = JSONObject()
            key = getCommandKey()
            command = getCommandChangeShow()
            val activeShow = getCommandActiveShow()
            json.put(activeShow,show)
            json.put(key,command)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandRead(){
        try {
            val json = JSONObject()
            key = getCommandKey()
            command = getCommandRead()
            json.put(key,command)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandWrite(){
        try {
            val json = JSONObject()
            key = getCommandKey()
            command = getCommandWrite()
            json.put(key,command)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandGetAllSlaves(){
        try {
            val json = JSONObject()
            key = getCommandKey()
            command = getCommandGetAllSlaves()
            json.put(key, command)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandChangeLanguage(language: Languages) {
        try {
            val json = JSONObject()
            key = getCommandKey()
            command = getCommandChangeLanguage()
            val languageKey = getLanguageKey()
            json.put(languageKey, language.toString())
            json.put(key,command)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandAddDatabase(name: String){
        try {
            val json = JSONObject()
            key = getCommandKey()
            command = getCommandAddDatabase()
            val databaseKey = getDatabaseKey()
            json.put(key, command)
            json.put(databaseKey,name)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun commandRemoveDatabase(name: String){
        try {
            val json = JSONObject()
            key = getCommandKey()
            command = getCommandRemoveDatabase()
            val databaseKey = getDatabaseKey()
            json.put(key, command)
            json.put(databaseKey,name)

            sendMessage(message = json)
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun handleJsonArray(message: JSONArray){
        try {
            DevHelper.determineOutput(message = message.toString(),type = "slavearray")
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }


    fun slaveCount(slaves: Int) {
        try {
            logger.info("slaves $slaves")
            DevHelper.determineOutput(message = slaves.toString(),type = "slavecount")
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun checkSuccess(success: Boolean, command: String){
        try {
            when(success){
                true -> {
                    DevHelper.determineOutput(message = "Command $command successfully performed",type = "commandresponse")
                    logger.info("Command $command successfully performed")
                }

                false -> {
                    DevHelper.determineOutput(message = "Command $command failed",type = "commandresponse")
                    logger.info("command $command failed")
                }
            }
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun stopConnection(){
        try {
            commandSocket.stopConnection()
            commandSocket.join()
            logger.info("Thread successfully stopped")
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    private fun getCommandKey(): String = Commands.Command.name

    private fun getCommandNew(): String = Commands.New.name

    private fun getCommandRead(): String = Commands.ReadStage.name

    private fun getCommandWrite(): String = Commands.WriteStage.name

    private fun getCommandChangeShow(): String = Commands.ChangeShow.name

    private fun getCommandActiveShow(): String = Commands.ActiveShow.name

    private fun getCommandGetAllSlaves(): String = Commands.GetAllSlaves.name

    private fun getCommandChangeLanguage(): String = Commands.ChangeLanguage.name

    private fun getLanguageKey(): String = Commands.Language.name

    private fun getCommandAddDatabase(): String = Commands.NewDdfDatabase.name

    private fun getCommandRemoveDatabase(): String = Commands.DeleteDdfDatabase.name

    private fun getDatabaseKey(): String = Commands.Name.name
}