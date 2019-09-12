package com.restapi.application.mapper

import com.restapi.application.api.viewmodel.ModelViewModel
import com.restapi.application.dmxmodels.Model

class ModelMapper{


    fun convertToViewModel(entity: Model):ModelViewModel{
        var viewmodel = ModelViewModel()
        viewmodel.id = entity.getId()
        viewmodel.author = entity.getAuthor()
        viewmodel.comment = entity.getComment()
        viewmodel.type = entity.getType()
        viewmodel.powerConsumption = entity.getPowerConsumption()
        viewmodel.ressourceConsumption = entity.getRessourceConsumption()
        viewmodel.version = entity.getVersion()
        viewmodel.model = entity.getModel()
        viewmodel.manufacturer = entity.getManufacturer()

        return viewmodel
    }

    fun convertToEntity(viewmodel: ModelViewModel): Model {
        var entity = Model()
        entity.seComment(viewmodel.comment)
        entity.setAuthor(viewmodel.author)
        entity.setManufacturer(viewmodel.manufacturer)
        entity.setModel(viewmodel.model)
        entity.setPowerConsumption(viewmodel.powerConsumption)
        entity.setRessourceConsumption(viewmodel.ressourceConsumption)
        entity.setType(viewmodel.type)
        entity.setVersion(viewmodel.version)

        return entity
    }
}