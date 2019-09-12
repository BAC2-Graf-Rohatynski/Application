package com.restapi.application.error

import com.restapi.application.core.ErrorPropertyHandler
import com.restapi.application.dev.DevHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class ErrorHandler {
    private val logger: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)
    private val errorSocket: ErrorSocket = ErrorSocket()
    private var language: Languages = Languages.EN


    init {
        try {
            val errorPort = errorSocket.getErrorPort()
            val errorAddress = errorSocket.getMasterAddress()
            errorSocket.connectToErrorHandler(address = errorAddress, port = errorPort)
            if (errorSocket.getConnectionStatus()) {
                //TODO
            }
        } catch (ex: Exception) {
            logger.error(ex.message)
        }
    }

    fun setLanguage(language: String) {
        try {
            language.toUpperCase()
            when (language) {
                Languages.EN.name -> this.language = Languages.EN
                Languages.DE.name -> this.language = Languages.DE
                else -> this.language = Languages.DE
            }
        } catch (ex: Exception) {
            logger.error(ex.message)
        }
    }

    fun getLanguage(): Languages {
        return this.language
    }

    fun sendMessage(code: Int, setError: Boolean) {
        try {
            if (this.language == null) {
                setLanguage(Languages.EN.name)
            }
            var message = ErrorPropertyHandler.getErrorMessage(code, language)
            message = "$code::$message::${setError.toString().toUpperCase()}"
            logger.info("Message '$message' sent to global error handler")
            errorSocket.sendMessage(message = message)
            DevHelper.determineOutput(message = message, type = "errormessage")
        }catch (ex: Exception){
            logger.error(ex.message)
        }

    }

    fun stopConnection() {
        errorSocket.stopConnection()
    }
}