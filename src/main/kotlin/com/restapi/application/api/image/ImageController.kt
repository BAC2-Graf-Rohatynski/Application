package com.restapi.application.api.image

import com.restapi.application.api.viewmodel.ImageViewModel
import com.restapi.application.database.ImageRepository
import com.restapi.application.image.Image
import com.restapi.application.image.ImageHandler
import com.restapi.application.mapper.ImageMapper
import org.slf4j.LoggerFactory
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = ["*"], allowCredentials = "true", allowedHeaders = ["*"])
class ImageController {


    var repository: ImageRepository
    var logger = LoggerFactory.getLogger(ImageController::class.java)
    var imageHandler: ImageHandler? = null
    var mapper = ImageMapper()

    constructor(repository: ImageRepository) {
        this.repository = repository
        imageHandler = ImageHandler(repository)
    }

    @GetMapping(value = ["/getAll"])
    fun getAllImages(): Any = imageHandler!!.getAllImages()


    @PostMapping(value = ["/delete"])
    fun deleteImage(@RequestBody image: ImageViewModel, bindingResult: BindingResult) {
        imageHandler!!.deleteImage(image.id!!.toLong())
    }

    @PostMapping
    fun createImage(@RequestBody viewmodel: ImageViewModel, bindingResult: BindingResult): Image {
        if (bindingResult.hasErrors()) {
            logger.error("Binding Error")
        }
        logger.info("Image ${viewmodel.id}")

        var image: Image = this.mapper.convertToEntity(viewmodel)
        if (viewmodel.id != null) {
            imageHandler!!.updateImage(image, viewmodel.id!!.toLong())
        } else {

            //logger.info(gobo.getImage()!!.size.toString())
            this.repository.save(image)
            return image
        }

        return image

    }
}