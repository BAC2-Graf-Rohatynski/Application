package com.restapi.application.slave

import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Slave {
    private val logger: Logger = LoggerFactory.getLogger(Slave::class.java)

    var ssidA = -1
    var ssidB = -1
    var ssidC = -1
    var ssidD = -1
    var ssidE = -1
    var groups = listOf<String>()
    var activeShow = String()
    var macAddress = String()
    var ipAddress = String()
    var ddfHash = String()
    var imagesHash = String()
    var status = String()
    var type = String()
    var device = String()
    var manufacturer = String()
    var univers = 0 //String bei Markus
    var position = String()
    var hls = String()
    var dmxChannels = 0
    var startDmxAddress = 0
    var endDmxAddress = 0
    var port = 0
    var timestamp: Long = 0

    fun setSlaveInformation(information: JSONObject){
        try {
            ssidA = information.getInt(SlaveInfo.SsidA.name)
            ssidB = information.getInt(SlaveInfo.SsidB.name)
            ssidC = information.getInt(SlaveInfo.SsidC.name)
            ssidD = information.getInt(SlaveInfo.SsidD.name)
            ssidE = information.getInt(SlaveInfo.SsidE.name)
            macAddress = information.getString(SlaveInfo.MacAddress.name)
            ipAddress = information.getString(SlaveInfo.IpAddress.name)
            imagesHash = information.getString(SlaveInfo.ImagesHash.name)
            status = information.getString(SlaveInfo.Status.name)
            type = information.getString(SlaveInfo.Type.name)
            device = information.getString(SlaveInfo.Device.name)
            univers = information.getInt(SlaveInfo.Univers.name)
            startDmxAddress = information.getInt(SlaveInfo.StartDmxAddress.name)
            endDmxAddress = information.getInt(SlaveInfo.EndDmxAddress.name)
            hls = information.getString(SlaveInfo.Hls.name)
            dmxChannels = information.getInt(SlaveInfo.DmxChannels.name)
            timestamp = information.getLong(SlaveInfo.Timestamp.name)
            manufacturer = information.getString(SlaveInfo.Manufacturer.name)
        }catch(ex: Exception){
            logger.error(ex.message)
        }

    }


    /*
    fun getSsidA(): Int = ssidA
    fun setSsidA(ssidA: Int){
        this.ssidA = ssidA
    }

    fun getSsidB(): Int = ssidB
    fun setSsidB(ssidB: Int){
        this.ssidB = ssidB
    }

    fun getSsidC(): Int = ssidC
    fun setSsidC(ssidC: Int){
        this.ssidC = ssidC
    }

    fun getSsidD(): Int = ssidD
    fun setSsidD(ssidD: Int){
        this.ssidD = ssidD
    }

    fun getSsidE(): Int = ssidE
    fun setSsidE(ssidE: Int){
        this.ssidE = ssidE
    }

    fun getMacAddress(): String = macAddress
    fun setMacAddress(macAddress: String){
        this.macAddress = macAddress
    }

    fun getIpAddress(): String = ipAddress
    fun setIpAddress(ipAddress: String){
        this.ipAddress = ipAddress
    }

    fun getImageHash(): String = imagesHash
    fun setImageHash(imagesHash: String){
        this.imagesHash = imagesHash
    }

    fun getStatus(): String = status
    fun setMacAddress(status: String){
        this.status = status
    }
*/

}