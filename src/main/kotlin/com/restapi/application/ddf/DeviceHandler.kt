package com.restapi.application.ddf

import com.restapi.application.database.DeviceRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.FileNotFoundException

class DeviceHandler(repository: DeviceRepository) {

    @Autowired
    private var repository: DeviceRepository? = repository

    private val logger: Logger = LoggerFactory.getLogger(DeviceHandler::class.java)

    @Synchronized
    fun getAllDevices(): Any {
        return try {
            logger.info("Getting all Devices ...")
            val devices = repository!!.findAll()

            logger.info("Getting all devies $devices")
            ResponseEntity(devices, HttpStatus.OK)
        } catch (ex: FileNotFoundException) {
            "error404"
        } catch (ex: Exception) {
            "error500"
        }
    }

    @Synchronized
    fun findDeviceById(id: Long): Device {
        val devices = repository!!.findAll()

        for (device in devices) {
            if (device.getId() == id) {
                logger.info("Device found in database")
                return device
            }
        }
        throw Exception("device not found!")
    }


    private fun delete(device: Device) {
        try {
            repository!!.delete(device)
            logger.info("Device with ID ${device.getId()} deleted")
            ResponseEntity.ok(HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error("Couldn't delete Device with ID ${device.getId()}")
        }
    }

    @Synchronized
    fun deleteDevice(id: Long) {
        var device = findDeviceById(id)
        try {
            delete(device)
        } catch (ex: Exception) {
            logger.error("Couldn't delete device $ex.message")
        }
    }

    private fun update(device: Device) {
        try {
            repository!!.save(device)
            ResponseEntity.ok(HttpStatus.OK)
        } catch (ex: Exception) {
            logger.error(ex.message)
        }
    }

    fun updateDevice(device: Device, id: Long) {
        try {
            var deviceToUpdate = findDeviceById(id)
            deviceToUpdate.setVersion(device.getVersion())
            deviceToUpdate.setType(device.getType())
            deviceToUpdate.setCategory(device.getCategory())
            deviceToUpdate.setManufacturer(device.getManufacturer())
            deviceToUpdate.setModel(device.getModel())
            deviceToUpdate.setAuthor(device.getAuthor())
            deviceToUpdate.setCreateDate(device.getCreateDate())
            deviceToUpdate.setPowerConsumption(device.getPowerConsumption())
            deviceToUpdate.setRessourceConsumption(device.getRessourceConsumption())
            deviceToUpdate.setComment(device.getComment())
            deviceToUpdate.setPower(device.getPower())
            deviceToUpdate.setDmxSum(device.getDmxSum())
            deviceToUpdate.setDmxUsedSum(device.getDmxUsedSum())
            deviceToUpdate.setPanTiltInvert(device.getPanTiltInvert())
            deviceToUpdate.setStandardDevice(device.getStandardDevice())
            deviceToUpdate.setEffect(device.getEffects())
            deviceToUpdate.setDdfFileName(device.getDdfFileName())

            logger.info("updating device  with ID ${device.getId()}")
            update(deviceToUpdate)
        } catch (ex: Exception) {
            logger.error("Couldn't update device  with ID ${device.getId()}")
        }
    }

}