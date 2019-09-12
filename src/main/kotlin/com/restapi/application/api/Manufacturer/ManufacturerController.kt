package com.restapi.application.api.Manufacturer

import com.restapi.application.api.viewmodel.ManufacturerViewModel
import com.restapi.application.database.ManufacturerRepository
import com.restapi.application.manufacturer.ManufacturerEntity
import com.restapi.application.manufacturer.ManufacturerHandler
import com.restapi.application.mapper.ManufacturerMapper
import org.slf4j.LoggerFactory
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/manufacturer")
@CrossOrigin(origins = ["*"], allowCredentials = "true", allowedHeaders = ["*"])
class ManufacturerController{

    var repository: ManufacturerRepository
    var logger = LoggerFactory.getLogger(ManufacturerController::class.java)
    var mapper = ManufacturerMapper()
    var manufacturerHandler: ManufacturerHandler? = null

    constructor(repository: ManufacturerRepository) {
        this.repository = repository
        this.mapper = mapper
        manufacturerHandler = ManufacturerHandler(repository)
    }

    @GetMapping(value = ["/getAll"])
    fun getAllManufacturers(): Any = manufacturerHandler!!.getAllManufacturers()


    @PostMapping(value = ["/delete"])
    fun deleteManufacturer(@RequestBody viewModel: ManufacturerViewModel, bindingResult: BindingResult){
        manufacturerHandler!!.deleteManufacturer(viewModel.id!!.toLong())
    }

    @PostMapping
    fun createManufacturer(@RequestBody viewModel: ManufacturerViewModel, bindingResult: BindingResult): ManufacturerEntity {
        if(bindingResult.hasErrors()){
            logger.error("Binding Error")
        }
        logger.info("Manufacturer ${viewModel.name}")

        var entity: ManufacturerEntity = this.mapper.convertToEntity(viewModel)
        if(viewModel.id != null){
            manufacturerHandler!!.updateManufacturer(entity, viewModel.id!!.toLong())
        } else{

            this.repository.save(entity)
            return entity
        }

        return entity
    }



}