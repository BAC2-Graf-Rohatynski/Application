package com.restapi.application.api.ddf

import com.google.gson.Gson
import com.restapi.application.xml.JsonMaker
import com.restapi.application.database.DDFRepository
import com.restapi.application.database.DeviceRepository
import com.restapi.application.ddf.Device
import com.restapi.application.ddf.DeviceHandler
import com.restapi.application.file.FileHandler
import com.restapi.application.hash.Hash
import com.restapi.application.mapper.ModelMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ddf")
@CrossOrigin(origins = ["*"], allowCredentials = "true", allowedHeaders = arrayOf("*"))
class DdfController {
    //var repository: NLCRepository
    var logger = LoggerFactory.getLogger(DdfController::class.java)
    var mapper = ModelMapper()
    var repository: DDFRepository
    var deviceRepository: DeviceRepository
    var jsonMaker: JsonMaker = JsonMaker()

    @Autowired
    var fileHandler: FileHandler? = null

    @Autowired
    var deviceHandler: DeviceHandler? = null


    constructor(repository: DDFRepository, deviceRepository: DeviceRepository) {
       // this.repository = repository
        this.mapper = mapper
        this.repository = repository
        this.deviceRepository = deviceRepository
    }

    @PostMapping
    fun createDDF(@RequestBody  viewmodel: Device, bindingResult: BindingResult) {
        if(bindingResult.hasErrors()){
            logger.error("Binding Error")
        }

        var gson = Gson()
        logger.info("DDFCreator")
        logger.info(gson.toJson(viewmodel))

        fileHandler!!.createFile(viewmodel)
    }

    @GetMapping(value = ["/getAll"])
    fun getAllDevices(): Any = deviceHandler!!.getAllDevices()

    @PostMapping(value = ["/findDevice"])
    fun findDevice(@RequestBody device: Device, bindingResult: BindingResult) {
       // deviceHandler!!.findDeviceByAttributes(device)
    }

    @PostMapping(value = ["/delete"])
    fun deleteDevice(@RequestBody device: Device, bindingResult: BindingResult) {
        deviceHandler!!.deleteDevice(device.getId()!!.toLong())
    }

}