package com.restapi.application.dmxmodels

import javax.persistence.*

@Entity
class Model{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    fun getId(): Long? = id

    private var type: String? = null
    fun getType(): String? = type
    fun setType(type: String?): Model{
        this.type = type
        return this
    }

    private var model: String? = null
    fun getModel(): String? = model
    fun setModel(model: String?): Model{
        this.model = model
        return this
    }

    private var comment: String? = null
    fun getComment(): String? = comment
    fun seComment(comment: String?): Model{
        this.comment = comment
        return this
    }

    private var author: String? = null
    fun getAuthor(): String? = author
    fun setAuthor(author: String?): Model{
        this.author = author
        return this
    }

    private var manufacturer: String? = null
    fun getManufacturer(): String? = manufacturer
    fun setManufacturer(manufacturer: String?): Model{
        this.manufacturer = manufacturer
        return this
    }

    private var version: String? = null
    fun getVersion(): String? = version
    fun setVersion(version: String?): Model{
        this.version = version
        return this
    }

    private var powerConsumption: String? = null
    fun getPowerConsumption(): String? = powerConsumption
    fun setPowerConsumption(powerConsumption: String?): Model{
        this.powerConsumption = powerConsumption
        return this
    }

    private var ressourceConsumption: String? = null
    fun getRessourceConsumption(): String? = ressourceConsumption
    fun setRessourceConsumption(ressourceConsumption: String?): Model{
        this.ressourceConsumption = ressourceConsumption
        return this
    }
}