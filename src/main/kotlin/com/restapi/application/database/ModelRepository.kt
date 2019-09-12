package com.restapi.application.database

import com.restapi.application.dmxmodels.Model
import org.springframework.data.repository.CrudRepository


interface ModelRepository : CrudRepository<Model, Long>