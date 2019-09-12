package com.restapi.application.gobo

import com.restapi.application.api.viewmodel.GoboViewModel
import com.restapi.application.database.GoboRepository
import com.restapi.application.mapper.GoboMapper
import com.restapi.application.mapper.ManufacturerMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.FileNotFoundException

class GoboHandler(repository: GoboRepository){

    @Autowired
    private var repository: GoboRepository? = repository
    var mapper = GoboMapper()

    private val logger: Logger = LoggerFactory.getLogger(GoboHandler::class.java)

    @Synchronized
    fun getAllGobos(): Any {
        return try {
            logger.info("Getting all Gobos ...")
            val goboViewModel: ArrayList<GoboViewModel> = ArrayList()
            val gobos = repository!!.findAll()
            for(gobo in gobos){
                val goboView = mapper.convertToViewModel(gobo)
                goboViewModel.add(goboView)
            }
            logger.info("Getting all Gobos $goboViewModel")
            ResponseEntity(goboViewModel, HttpStatus.OK)
        } catch (ex: FileNotFoundException) {
            "error404"
        } catch (ex: Exception) {
            "error500"
        }
    }

    @Synchronized
    fun findGoboById(id: Long): Gobo {
        val gobos = repository!!.findAll()

        for (gobo in gobos) {

            if (gobo.id == id) {
                logger.info("Gobo '${gobo.getColor()}'  ${gobo.gethexCode()} found in database")
                return gobo
            }
        }
        throw Exception("gobo not found!")
    }

    private fun delete(gobo: Gobo){
        try {
            repository!!.delete(gobo)
            logger.info("Gobo ${gobo.getColor()}  ${gobo.gethexCode()} with ID ${gobo.id} deleted")
            ResponseEntity.ok(HttpStatus.OK)
        } catch(ex: Exception){
            logger.error("Couldn't delete gobo ${gobo.getColor()}  ${gobo.gethexCode()} with ID ${gobo.id}")
        }
    }

    @Synchronized
    fun deleteGobo(id: Long) {
        var gobo = findGoboById(id)
        try{
            delete(gobo)
        }catch (ex: Exception){
            logger.error("Couldn't delete gobo  ${gobo.getColor()} $ex.message")
        }
    }

    private fun update(gobo: Gobo ){
        try{
            repository!!.save(gobo)
            ResponseEntity.ok(HttpStatus.OK)
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }

    fun updateGobo(gobo: Gobo, id: Long){
        try {
            var goboToUpdate = findGoboById(id)
            goboToUpdate.setColor(gobo.getColor())
            goboToUpdate.sethexCode(gobo.gethexCode())
            goboToUpdate.setImageHash(gobo.getImageHash())
            goboToUpdate.setImage((gobo.getImage()))
            logger.info("updating gobo ${gobo.getColor()} ${gobo.gethexCode()} with ID ${gobo.id}")
            update(goboToUpdate)
        }catch(ex: Exception){
            logger.error("Couldn't update gobo ${gobo.getColor()}  ${gobo.gethexCode()} with ID ${gobo.id}")
        }
    }

}