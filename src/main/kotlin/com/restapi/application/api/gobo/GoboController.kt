package com.restapi.application.api.gobo

import com.restapi.application.api.viewmodel.GoboViewModel
import com.restapi.application.database.GoboRepository
import com.restapi.application.gobo.Gobo
import com.restapi.application.gobo.GoboHandler
import com.restapi.application.mapper.GoboMapper
import org.slf4j.LoggerFactory
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/gobo")
@CrossOrigin(origins = ["*"], allowCredentials = "true", allowedHeaders = ["*"])
class GoboController {

    var repository: GoboRepository
    var logger = LoggerFactory.getLogger(GoboController::class.java)
    var goboHandler: GoboHandler? = null
    var mapper = GoboMapper()

    constructor(repository: GoboRepository) {
        this.repository = repository
        goboHandler = GoboHandler(repository)
    }

    @GetMapping(value = ["/getAll"])
    fun getAllGobos(): Any = goboHandler!!.getAllGobos()


    @PostMapping(value = ["/delete"])
    fun deleteGobo(@RequestBody gobo: Gobo, bindingResult: BindingResult) {
        goboHandler!!.deleteGobo(gobo.id!!.toLong())
    }

    @PostMapping
    fun createGobo(@RequestBody viewmodel: GoboViewModel, bindingResult: BindingResult): Gobo {
        if (bindingResult.hasErrors()) {
            logger.error("Binding Error")
        }
        logger.info("Gobo ${viewmodel.id} ${viewmodel.color} ${viewmodel.imageHash} ${viewmodel.base64String}")

        var gobo: Gobo = this.mapper.convertToEntity(viewmodel)
        if (viewmodel.id != null) {
            goboHandler!!.updateGobo(gobo, viewmodel.id!!.toLong())
        } else {

            //logger.info(gobo.getImage()!!.size.toString())
            this.repository.save(gobo)
            return gobo
        }

        return gobo

    }
}


