package com.restapi.application.core
import com.restapi.application.error.Languages
import java.util.*

object ErrorPropertyHandler{
    private val properties = Properties()
    private val errorPresets = ErrorPropertyHandler::class.java.classLoader.getResourceAsStream("error.properties")

    init{
        loadProperties()
    }

    @Synchronized
    private fun loadProperties(){
        properties.load(errorPresets)
    }

    @Synchronized
    fun getErrorMessage(id: Int, language: Languages): String{
        return properties.getProperty("$id.$language").toString()
    }
}

annotation class Synchronized
