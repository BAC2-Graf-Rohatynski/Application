package com.restapi.application.startup

import com.restapi.application.license.LicenseHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@Controller
class StartupController{

    private val logger: Logger = LoggerFactory.getLogger(LicenseHandler::class.java)

    @RequestMapping(path=["/"])
    fun showLoginPage(): String{
        return "login"
    }

    /*
    @Throws(IOException::class)
    @RequestMapping(value = ["/login/{pin}}"], method = [RequestMethod.GET])
    fun checkPin(request: HttpServletRequest, response: HttpServletResponse, @PathVariable("pin") pin: Int) {

        try {

            }
        } catch (e: Exception) {
            logger.error(e.message.toString())
        }

        return response.outputStream.write(media)
    }
*/
    @RequestMapping(path=["login/{changedPin}"], method = [RequestMethod.GET])
    fun changePin(){
        //TODO
    }

    fun checkPin(){
        //TODO
    }


}