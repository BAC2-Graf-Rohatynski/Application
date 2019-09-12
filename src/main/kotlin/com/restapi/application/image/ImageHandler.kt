package com.restapi.application.image

import com.restapi.application.api.viewmodel.ImageViewModel
import com.restapi.application.database.ImageRepository
import com.restapi.application.mapper.ImageMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.FileNotFoundException

class ImageHandler(repository: ImageRepository){

    @Autowired
    private var repository: ImageRepository? = repository
    private val logger: Logger = LoggerFactory.getLogger(ImageHandler::class.java)
    private var mapper = ImageMapper()

    @Synchronized
    fun getAllImages(): Any {
        return try {
            logger.info("Getting all Images ...")
            val imageViewModel: ArrayList<ImageViewModel> = ArrayList()
            val images = repository!!.findAll()

            for(image in images){
                val imageView = mapper.convertToViewModel(image)
                imageViewModel.add(imageView)
            }
            logger.info("Getting all Images")
            ResponseEntity(imageViewModel, HttpStatus.OK)
        } catch (ex: FileNotFoundException) {
            "error404"
        } catch (ex: Exception) {
            "error500"
        }
    }

    @Synchronized
    fun findImageById(id: Long): Image {
        val images = repository!!.findAll()

        for (image in images) {

            if (image.id == id) {
                logger.info("Image found in database")
                return image
            }
        }
        throw Exception("image not found!")
    }

    private fun deleteImage(image: Image){
        try {
            repository!!.delete(image)
            logger.info("Image with ID ${image.id} deleted")
            ResponseEntity.ok(HttpStatus.OK)
        } catch(ex: Exception){
            logger.error("Couldn't delete image with ID ${image.id}")
        }
    }

    @Synchronized
    fun deleteImage(id: Long) {
        var image = findImageById(id)
        try{
            deleteImage(image)
        }catch (ex: Exception){
            logger.error("Couldn't delete image  ${image.id} $ex.message")
        }
    }

    private fun updateImage(image: Image ){
        try{
            repository!!.save(image)
            ResponseEntity.ok(HttpStatus.OK)
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }

    fun updateImage(image: Image, id: Long){
        try {
            var imageToUpdate = findImageById(id)
            logger.info("updating image with ID ${image.id}")
            updateImage(imageToUpdate)
        }catch(ex: Exception){
            logger.error("Couldn't update image  with ID ${image.id}")
        }
    }

}