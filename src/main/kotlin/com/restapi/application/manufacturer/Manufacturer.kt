package com.restapi.application.manufacturer

import com.sun.xml.internal.ws.developer.Serialization
import javax.persistence.*

@Entity
@Serialization
@Table(name = "Manufacturer")
class ManufacturerEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null
    fun getId(): Long? = id

    @Column(length = 50)
    private var name: String? = null
    fun getName(): String? = name
    fun setName(name: String?){
        this.name = name
    }

}