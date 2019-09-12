package com.restapi.application.ddf

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "device")
class Device : Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null
    fun getId(): Long? = id

    private var version: Int = 0
    fun getVersion(): Int = version
    fun setVersion(version: Int){
        this.version = version
    }

    private var type: String? = null
    fun getType(): String? = type
    fun setType(type: String?){
        this.type = type
    }
/*
    private var lastModification: String? = null
    fun getLastModification(): String? = lastModification
    fun setLastModification(lastModification: String?){
        this.lastModification = lastModification
    }


*/
    private var category: String? = null
    fun getCategory(): String? = category
    fun setCategory(category: String?){
        this.category = category
    }

    private var imageHash: String? = null
    fun getImageHash(): String? = imageHash
    fun setImageHash(imageHash: String?){
        this.imageHash = imageHash
    }

    private var manufacturer: String? = null
    fun getManufacturer(): String? = manufacturer
    fun setManufacturer(manufacturer: String?) {
        this.manufacturer = manufacturer
    }

    private var model: String? = null
    fun getModel(): String? = model
    fun setModel(model: String?){
        this.model = model
    }

    private var author: String? = null
    fun getAuthor(): String? = author
    fun setAuthor(author: String?){
        this.author = author
    }

    private var createDate: String? = null
    fun getCreateDate(): String? = createDate
    fun setCreateDate(createDate: String?){
        this.createDate = createDate
    }

    private var powerConsumption: String? = null
    fun getPowerConsumption(): String? = powerConsumption
    fun setPowerConsumption(powerConsumption: String?){
        this.powerConsumption = powerConsumption
    }

    private var ressourceConsumption: String? = null
    fun getRessourceConsumption(): String? = ressourceConsumption
    fun setRessourceConsumption(ressourceConsumption: String?){
        this.ressourceConsumption = ressourceConsumption
    }

    private var comment: String? = null
    fun getComment(): String? = comment
    fun setComment(comment: String?){
        this.comment = comment
    }

    private var power: String? = null
    fun getPower(): String? = power
    fun setPower(power: String?){
        this.power = power
    }

    private var dmxSum: Int? = null
    fun getDmxSum(): Int? = dmxSum
    fun setDmxSum(dmxSum: Int?){
        this.dmxSum = dmxSum
    }

    private var dmxUsedSum: Int? = null
    fun getDmxUsedSum(): Int? = dmxUsedSum
    fun setDmxUsedSum(dmxUsedSum: Int?){
        this.dmxUsedSum = dmxUsedSum
    }

    private var panTiltInvert: Boolean? = null
    fun getPanTiltInvert(): Boolean? = panTiltInvert
    fun setPanTiltInvert(panTiltInvert: Boolean?){
        this.panTiltInvert = panTiltInvert
    }

    private var standardDevice: String? = null
    fun getStandardDevice(): String? = standardDevice
    fun setStandardDevice(standardDevice: String?){
        this.standardDevice = standardDevice
    }

    @Column(name = "effects", columnDefinition = "LONGBLOB")
    private var effects: Array<Effect>? = null
    fun getEffects(): Array<Effect>? = effects
    fun setEffect(effects: Array<Effect>?){
        this.effects = effects
    }

    private var ddfFileName: String? = null
    fun getDdfFileName(): String? = ddfFileName
    fun setDdfFileName(ddfFileName: String?){
        this.ddfFileName = ddfFileName;
    }

    /*
    private var ddfFileText: String? = null
    fun getDdfFileText(): String? = ddfFileText
    fun setDdfFileText(dddfFileText: String?){
        this.ddfFileText = ddfFileText;
    }

    private var mode: String? = null
    fun getMode(): String? = null
    fun setMode(mode: String?){
        this.mode = mode
    }

    private var channels: Int? = null
    fun getChannels(): Int? = null
    fun setChannels(channels: Int?){
        this.channels = channels
    }


    private var checksum: Int? = null
    fun getChecksum(): Int? = null
    fun setChecksum(checksum: Int?){
        this.checksum = checksum
    }
*/
}