package com.restapi.application.mapper

import com.restapi.application.api.viewmodel.GoboViewModel
import com.restapi.application.api.viewmodel.ImageViewModel
import com.restapi.application.gobo.Gobo
import com.restapi.application.image.Image
import org.slf4j.LoggerFactory
import java.util.*
import javax.swing.text.html.ImageView
import org.apache.tomcat.util.codec.binary.Base64

class ImageMapper{
    var logger = LoggerFactory.getLogger(ImageMapper::class.java)

    fun convertToViewModel(entity: Image): ImageViewModel {
        var viewmodel = ImageViewModel()
        viewmodel.id = entity.id
        viewmodel.base64String = Base64.encodeBase64String(entity.getImage())
        viewmodel.imageHash = entity.getImageHash()
        viewmodel.tags = entity.getTags()

        return viewmodel
    }

    fun convertToEntity(viewmodel: ImageViewModel): Image {
        var entity = Image()
        entity.setImageHash(viewmodel.imageHash)
        entity.setTags(viewmodel.tags)

        try{
            var bytes: ByteArray = Base64.decodeBase64(viewmodel.base64String)
            //var imageData = String(bytes, Charsets.UTF_8)
            entity.setImage(bytes)

            return entity
        } catch(ex: Exception){
            logger.error(ex.toString())
        }
        return entity

    }
}