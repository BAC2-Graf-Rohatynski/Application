package com.restapi.application.startup

import com.restapi.application.command.CommandHandler

import com.restapi.application.error.ErrorSocket
import com.restapi.application.license.LicenseHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.awt.Desktop
import java.net.URI
import javax.annotation.PostConstruct

@Component
class StartupHandler{
    private val logger: Logger = LoggerFactory.getLogger(ErrorSocket::class.java)

    fun openLoginpage(){
        try{
           val uri = URI("http://localhost:8080/")
            if(Desktop.isDesktopSupported()){
                Desktop.getDesktop().browse(uri)
            } else{
                logger.info("Desktop not supported, please use http://localhost:8080/ instead")
            }
        } catch(ex: Exception){
            logger.error("cannot open page ${ex.message}")
        }
    }

    @PostConstruct
    fun initStartup(){
        CommandHandler
        LicenseHandler
        /*

        //TODO prüfen ob Module alle vorhanden sind und dann login page launchen
        this.openLoginpage()
        */
    }

}