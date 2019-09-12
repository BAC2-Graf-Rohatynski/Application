package com.restapi.application.mapper

import com.restapi.application.api.viewmodel.ManufacturerViewModel
import com.restapi.application.manufacturer.ManufacturerEntity

class ManufacturerMapper{

    fun convertToViewModel(entity: ManufacturerEntity): ManufacturerViewModel {
        var viewmodel = ManufacturerViewModel()
        viewmodel.name = entity.getName()
        viewmodel.id = entity.getId()

        return viewmodel
    }

    fun convertToEntity(viewmodel: ManufacturerViewModel): ManufacturerEntity {
        var entity = ManufacturerEntity()
        entity.setName(viewmodel.name)

        return entity
    }
}