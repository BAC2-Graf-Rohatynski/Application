

package com.restapi.application.database

import com.restapi.application.manufacturer.ManufacturerEntity
import org.springframework.data.repository.CrudRepository

interface ManufacturerRepository : CrudRepository<ManufacturerEntity, Long>
