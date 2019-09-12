package com.restapi.application.image

import javax.persistence.*

@Entity
class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

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

    @Column(length = 50)
    private var tags: Array<String>? = null
    fun getTags(): Array<String>? = tags
    fun setTags(tags: Array<String>?){
        this.tags = tags
    }

}