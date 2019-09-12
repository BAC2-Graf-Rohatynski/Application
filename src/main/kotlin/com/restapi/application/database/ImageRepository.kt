package com.restapi.application.database

import com.restapi.application.image.Image
import org.springframework.data.repository.CrudRepository

interface ImageRepository : CrudRepository<Image, Long>