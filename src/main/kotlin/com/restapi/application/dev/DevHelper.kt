package com.restapi.application.dev

import com.restapi.application.command.CommandHandler
import com.restapi.application.slave.Slave
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object DevHelper{

    private val logger: Logger = LoggerFactory.getLogger(CommandHandler::class.java)

    private var errorMessage: String? = null
    private var errorResponse: String? = null

    private var licenseMessage: String? = null
    private var licenseResponse: String? = null

    private var commandMessage: String? = null
    private var commandResponse: String? = null

    private var slaveCount: Int? = null
    private var slaveArray: String? = null

    private var slaveList = mutableListOf<Slave>()

    @Synchronized
    fun getMyErrorMessage(): String = errorMessage.toString()
    fun setMyErrorMessage(message: String){
        this.errorMessage = message
    }

    @Synchronized
    fun getMyErrorResponse(): String = errorResponse.toString()
    fun setMyErrorResponse(message: String){
        this.errorResponse = message
    }

    @Synchronized
    fun getMyLicenseMessage(): String = licenseMessage.toString()
    fun setMyLicenseMessage(message: String){
        this.licenseMessage = message
    }

    @Synchronized
    fun getMyLicenseResponse(): String = licenseResponse.toString()
    fun setMyLicenseResponse(message: String){
        this.licenseResponse = message
    }

    @Synchronized
    fun getMyCommandMessage(): String = commandMessage.toString()
    fun setMyCommandMessage(message: String){
        this.commandMessage = message
    }

    @Synchronized
    fun getMyCommandResponse(): String = commandResponse.toString()
    fun setMyCommandResponse(message: String){
        this.commandResponse = message
    }

    @Synchronized
    fun getMySlaveCount(): Int? = slaveCount
    fun setMySlaveCount(message: Int){
        this.slaveCount = message
    }

    @Synchronized
    fun getMySlaveArray(): String? = slaveArray
    fun setMySlaveArray(message: String){
        this.slaveArray = message
    }

    fun getSlaveList(): List<Slave> {
        return slaveList
    }

    fun createSlave(message: String){
        try {
            clearSlaveList()
            val slaveInfo = JSONArray(message)
            val slaveCount = getMySlaveCount()!!.toInt()
            for(i in 0 until slaveCount){
                val slave = Slave()
                val information = slaveInfo[i] as JSONObject

                slave.setSlaveInformation(information = information)

                slaveList.add(slave)
            }
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }

    fun clearSlaveList(){
        try {
            slaveList.clear()
            logger.info("slave list cleared")
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }

    fun showSlaves(){
        var count = 1
        try {
            for(s: Slave in slaveList){
                var output = String()
                output += "SsidA: = ${s.ssidA} "
                output += "SsidB: = ${s.ssidB} "
                output += "SsidC: = ${s.ssidC} "
                output += "SsidD: = ${s.ssidD} "
                output += "SsidE: = ${s.ssidE} "
                output += "Mac Address: = ${s.macAddress} "
                output += "IP Address: = ${s.ipAddress} "
                output += "Image Hash: = ${s.imagesHash} "
                output += "Status: = ${s.status} "
                output += "Device: = ${s.device} "
                output += "Manufacturer: = ${s.manufacturer} "
                output += "Universe: = ${s.univers} "
                output += "Start DMX Address: = ${s.startDmxAddress} "
                output += "End DMX Address: = ${s.endDmxAddress} "
                output += "DMX Channels: = ${s.dmxChannels} "
                output += "HLS: = ${s.hls} "
                output += "Timestamp: = ${s.timestamp} "
                logger.info("$count: " + " $output")
                count++
            }
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }

    fun determineOutput(message: String, type: String){
        try {
            when(type){
                "commandresponse" -> setMyCommandResponse(message = message)
                "commandmessage" -> setMyCommandMessage(message = message)
                "licensemessage" -> setMyLicenseMessage(message = message)
                "licenserepsonse" -> setMyLicenseResponse(message = message)
                "errormessage" -> setMyErrorMessage(message = message)
                "errorresponse" -> setMyErrorResponse(message = message)
                "slavecount" -> setMySlaveCount(message = message.toInt())
                "slavearray" -> createSlave(message = message)

            }
        }catch (ex: Exception){
            logger.error(ex.message)
        }
    }
}