package com.restapi.application.error

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

@Controller
class MyErrorController: ErrorController{

    fun myErrorController() {}

    @GetMapping(value = ["/error"])
    fun handleError(request: HttpServletRequest): String {
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)

        if(status != null){

            val statusCode = Integer.valueOf(status.toString())

            return when(statusCode){
                HttpStatus.BAD_REQUEST.value() -> "error400"
                HttpStatus.UNAUTHORIZED.value() -> "error401"
                HttpStatus.NOT_FOUND.value() -> "error404"
                HttpStatus.REQUEST_TIMEOUT.value() -> "error408"
                HttpStatus.INTERNAL_SERVER_ERROR.value() -> "error500"
                HttpStatus.SERVICE_UNAVAILABLE.value() -> "error503"
                else -> "error"
            }
        }
        else{
            return "error"
        }
    }

    override fun getErrorPath(): String = "/error"
}