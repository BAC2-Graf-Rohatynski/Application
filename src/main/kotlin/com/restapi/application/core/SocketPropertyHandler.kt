package com.restapi.application.core

import java.util.*

object SocketPropertyHandler{
    private val properties = Properties()
    private val socketPresets = SocketPropertyHandler::class.java.classLoader.getResourceAsStream("socket.properties")

    init{
        loadProperties()
    }

    @Synchronized
    private fun loadProperties(){
        properties.load(socketPresets)
    }

    @Synchronized
    fun getErrorPort(): Int{
        return properties.getProperty("socket.error.port").toInt()
    }

    @Synchronized
    fun getLicensePort(): Int{
        return properties.getProperty("socket.license.port").toInt()
    }

    @Synchronized
    fun getCommandPort(): Int{

        return properties.getProperty("socket.command.port").toInt()
    }
    @Synchronized
    fun getMasterAddress(): String{
        return properties.getProperty("socket.address")
    }



}


