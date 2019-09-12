package com.restapi.application.manufacturer

import com.restapi.application.api.viewmodel.ManufacturerViewModel
import com.restapi.application.database.ManufacturerRepository
import com.restapi.application.mapper.ManufacturerMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.FileNotFoundException
import java.lang.Exception

class ManufacturerHandler(repository: ManufacturerRepository){
    private val logger: Logger = LoggerFactory.getLogger(ManufacturerHandler::class.java)

    @Autowired
    private var repository: ManufacturerRepository? = repository
    var mapper = ManufacturerMapper()


    @Synchronized
    fun getAllManufacturers(): Any {
        return try {
            logger.info("Getting all Manufacturers ...")
            val manufacturerViewmodel: ArrayList<ManufacturerViewModel> = ArrayList()
                val manufacturerEntities = repository!!.findAll()
                for(manufacturer in manufacturerEntities){
                    val manufacturerView = mapper.convertToViewModel(manufacturer)
                    manufacturerViewmodel.add(manufacturerView)
                }
            logger.info("Getting all manufacturers $manufacturerViewmodel")
            ResponseEntity(manufacturerViewmodel, HttpStatus.OK)
        } catch (ex: FileNotFoundException) {
            "error404"
        } catch (ex: Exception) {
            "error500"
        }
    }

    @Synchronized
    fun findManufacturerById(id: Long): ManufacturerEntity {
        val manufacturers = repository!!.findAll()

        for (manufacturer in manufacturers) {


            if (manufacturer.getId() == id) {
                logger.info("Manufacturer '${manufacturer.getName()}' found in database")
                return manufacturer
            }
        }
        throw Exception("Manufacturer not found!")
    }

    private fun delete(manufacturer: ManufacturerEntity){
        try {
            repository!!.delete(manufacturer)
            logger.info("Manufacturer ${manufacturer.getName()} with ID ${manufacturer.getId()} deleted")
            ResponseEntity.ok(HttpStatus.OK)
        } catch(ex: Exception){
            logger.error("Couldn't delete manufacturer ${manufacturer.getName()} with ID ${manufacturer.getId()}")
        }
    }

    @Synchronized
    fun deleteManufacturer(id: Long) {
        var manufacturer = findManufacturerById(id)
        try{
            delete(manufacturer)
        }catch (ex: Exception){
            logger.error("Couldn't delete manufacturer $ex.message")
        }
    }

    private fun update(manufacturer: ManufacturerEntity ){
        try{
            repository!!.save(manufacturer)
            ResponseEntity.ok(HttpStatus.OK)
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }

    fun updateManufacturer(modelanufacturer: ManufacturerEntity, id: Long){
        try {
            var manufacturerToUpdate = findManufacturerById(id)
            manufacturerToUpdate.setName(modelanufacturer.getName())
            logger.info("updating manufacturer ${modelanufacturer.getName() }with ID ${modelanufacturer.getId()}")
            update(manufacturerToUpdate)
        }catch(ex: Exception){
            logger.error("Couldn't update manufacturer ${modelanufacturer.getName() }with ID ${modelanufacturer.getId()}")
        }
    }
}