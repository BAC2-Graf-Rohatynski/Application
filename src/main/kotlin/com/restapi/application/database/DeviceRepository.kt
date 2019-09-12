package com.restapi.application.database

import com.restapi.application.ddf.Device
import org.springframework.data.repository.CrudRepository

interface DeviceRepository : CrudRepository<Device, Long>