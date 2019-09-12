package com.restapi.application.mapper

import com.restapi.application.api.gobo.GoboController
import com.restapi.application.api.viewmodel.GoboViewModel
import com.restapi.application.database.GoboRepository
import com.restapi.application.gobo.Gobo
import org.apache.tomcat.util.codec.binary.Base64
import org.slf4j.LoggerFactory

class GoboMapper{

    var logger = LoggerFactory.getLogger(GoboMapper::class.java)

    fun convertToViewModel(entity: Gobo): GoboViewModel {
        var viewmodel = GoboViewModel()
        viewmodel.color = entity.getColor()
        viewmodel.id = entity.id
        viewmodel.base64String = Base64.encodeBase64String(entity.getImage())
        viewmodel.hexCode = entity.gethexCode()

        return viewmodel
    }

    fun convertToEntity(viewmodel: GoboViewModel): Gobo {
        var entity = Gobo()
        entity.setColor(viewmodel.color)
        entity.sethexCode(viewmodel.hexCode)
        entity.setImageHash(viewmodel.imageHash)

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