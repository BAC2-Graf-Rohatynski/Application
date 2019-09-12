package com.restapi.application.gobo

import com.sun.xml.internal.ws.developer.Serialization
import java.awt.Image
import javax.persistence.*

@Entity
@Serialization
@Table(name = "Gobo")
class Gobo{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    @Column(length = 50)
    private var color: String? = null
    fun getColor(): String? = color
    fun setColor(color: String?){
        this.color = color
    }

    @Column(length = 50)
    private var hexCode: String? = null
    fun gethexCode(): String? = hexCode
    fun sethexCode(hexCode: String?){
        this.hexCode = hexCode
    }

    @Column(length = 50)
    private var imageHash: String? = null
    fun getImageHash(): String? = imageHash
    fun setImageHash(imageHash: String?){
        this.imageHash = imageHash
    }

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private var image: ByteArray? = null
    fun getImage(): ByteArray? = image
    fun setImage(image: ByteArray?){
        this.image = image
    }

}