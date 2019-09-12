package com.restapi.application.api.command


import com.restapi.application.command.CommandHandler
import com.restapi.application.dev.DevController
import com.restapi.application.dev.DevHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/command")
@CrossOrigin(origins = ["*"], allowCredentials = "true", allowedHeaders = arrayOf("*"))
class Command {

    @RequestMapping(value = ["/CommandRead"], method = [RequestMethod.GET])
    fun sendReadStage(model: Model) {
        CommandHandler.commandRead()
    }

    @RequestMapping(value = ["/CommandWrite"], method = [RequestMethod.GET])
    fun sendWriteStage(model: Model) {
        CommandHandler.commandWrite()
    }

    @RequestMapping(value = ["CommandChangeShow"], method = [RequestMethod.POST])
    fun sendChangeShow(@RequestParam("show") show: String) {
        CommandHandler.commandChangeShow(show = show)
    }


    @RequestMapping(value = ["/CommandNew"], method = [RequestMethod.GET])
    fun sendNew(model: Model) {
        CommandHandler.commandNew()
    }

    @RequestMapping(value = ["/CommandGetAllSlaves"], method = [RequestMethod.GET])
    fun sendGetAllSlaves(model: Model) {
        CommandHandler.commandGetAllSlaves()
    }

    /*
    @RequestMapping(value = ["CommandChangeLanguage"], method = [RequestMethod.POST])
    fun sendChangeLanguage(@RequestParam("language") language: String, model: Model): String {

        DevController.errorHandler.setLanguage(language = language)
        CommandHandler.commandChangeLanguage(language = DevController.errorHandler.getLanguage())

    }
    */

    @RequestMapping(value = ["CommandAddDatabase"], method = [RequestMethod.POST])
    fun sendAddDatabase(@RequestParam("database") name: String, model: Model) {
        CommandHandler.commandAddDatabase(name = name)
    }

    @RequestMapping(value = ["CommandRemoveDatabase"], method = [RequestMethod.POST])
    fun sendRemoveDatabase(@RequestParam("database") name: String, model: Model) {
        CommandHandler.commandRemoveDatabase(name = name)
    }
}