package com.restapi.application.api.model

import com.restapi.application.api.viewmodel.ModelViewModel
import com.restapi.application.database.ModelRepository
import com.restapi.application.dmxmodels.Model
import com.restapi.application.file.ModelHandler
import com.restapi.application.mapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/model")
@CrossOrigin(origins = ["*"], allowCredentials = "true", allowedHeaders = ["*"])
class ModelController{

    var repository: ModelRepository
    var logger = LoggerFactory.getLogger(ModelController::class.java)
    var mapper = ModelMapper()
    var modelHandler: ModelHandler? = null

    constructor(repository: ModelRepository) {
        this.repository = repository
        this.mapper = mapper
        modelHandler = ModelHandler(repository)
    }

    @GetMapping(value = ["/getAll"])
    fun getAllManufacturers(): Any = modelHandler!!.getAllModels()

    @PostMapping(value = ["/delete"])
    fun deleteManufacturer(@RequestBody viewModel: ModelViewModel, bindingResult: BindingResult){
        modelHandler!!.deleteModel(viewModel.id!!.toLong())
    }

    @PostMapping
    fun createModel(@RequestBody viewModel: ModelViewModel, bindingResult: BindingResult): Model {
        if(bindingResult.hasErrors()){
            logger.error("Binding Error")
        }
        logger.info("Received Model with following properties: Type: ${viewModel.type} Model: ${viewModel.model} Manufacturer: ${viewModel.manufacturer}  Author: ${viewModel.author} Version: ${viewModel.version} Power Consumption: ${viewModel.powerConsumption} Ressource Consumption: ${viewModel.ressourceConsumption} Comment: ${viewModel.comment}")

        var model: Model = this.mapper.convertToEntity(viewModel)
        if(viewModel.id != null){
            modelHandler!!.updateModel(model, viewModel.id!!.toLong())
        } else{
            this.repository.save(model)
            return model
        }

        return model
    }

}