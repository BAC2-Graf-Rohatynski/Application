package com.restapi.application.file


import com.restapi.application.api.viewmodel.ModelViewModel
import com.restapi.application.database.ModelRepository
import com.restapi.application.dmxmodels.Model
import com.restapi.application.mapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.FileNotFoundException
import java.lang.Exception

class ModelHandler(repository: ModelRepository){
    private val logger: Logger = LoggerFactory.getLogger(ModelHandler::class.java)

    @Autowired
    private var repository: ModelRepository? = repository
    var mapper = ModelMapper()


    @Synchronized
    fun getAllModels(): Any {
        return try {
            logger.info("Getting all models ...")
            val modelViewModel: ArrayList<ModelViewModel> = ArrayList()
            val models = repository!!.findAll()
            for(model in models){
                val modelView = mapper.convertToViewModel(model)
                modelViewModel.add(modelView)
            }
            logger.info("Getting all models $modelViewModel")
            ResponseEntity(modelViewModel, HttpStatus.OK)
        } catch (ex: FileNotFoundException) {
            "error404"
        } catch (ex: Exception) {
            "error500"
        }
    }

    @Synchronized
    fun findModelById(id: Long): Model {
        val models = repository!!.findAll()

        for (model in models) {
            model

            if (model.getId() == id) {
                logger.info("Model '${model.getModel()}' found in database")
                return model
            }
        }
        throw Exception("Model not found!")
    }

    private fun delete(model: Model){
        try {
            repository!!.delete(model)
            logger.info("Model ${model.getModel()} with ID ${model.getId()} deleted")
            ResponseEntity.ok(HttpStatus.OK)
        } catch(ex: Exception){
            logger.error("Couldn't delete model ${model.getModel()} with ID ${model.getId()}")
        }
    }

    @Synchronized
    fun deleteModel(id: Long) {
        var model = findModelById(id)
        try{
            delete(model)
        }catch (ex: Exception){
            logger.error("Couldn't delete model $ex.message")
        }
    }

    private fun update(model: Model){
        try{
            repository!!.save(model)
            ResponseEntity.ok(HttpStatus.OK)
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }

    fun updateModel(model: Model, id: Long){
        try {
            val modelToUpdate = findModelById(id)
                    .setModel(model.getModel())
                    .setType(model.getType())
                    .setManufacturer(model.getManufacturer())
                    .setAuthor(model.getAuthor())
                    .setVersion(model.getVersion())
                    .setPowerConsumption(model.getPowerConsumption())
                    .setRessourceConsumption(model.getRessourceConsumption())
                    .seComment(model.getComment())

            logger.info("updating model ${model.getModel() }with ID $id")
            update(modelToUpdate)
        }catch(ex: Exception){
            logger.error("Couldn't update model ${model.getModel() }with ID $id")
        }
    }

}