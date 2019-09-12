package com.restapi.application.database

import com.restapi.application.gobo.Gobo
import org.springframework.data.repository.CrudRepository

interface GoboRepository : CrudRepository<Gobo, Long>