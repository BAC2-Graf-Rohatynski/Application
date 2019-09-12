package com.restapi.application.database

import com.restapi.application.ddf.DDFFile
import org.springframework.data.repository.CrudRepository

interface DDFRepository : CrudRepository<DDFFile, Int>