package com.restapi.application.ddf

import javax.persistence.*

@Entity
@Table(name = "ddf")
class DDFFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Int? = null

    private var fileName: String? = null
    fun getFileName(): String? = fileName
    fun setFileName(fileName: String) {
        this.fileName = fileName
    }

    @Column(length = 65535, columnDefinition = "text")
    private var fileText: String? = null
    fun getFileText(): String? = fileText
    fun setFileText(fileText: String) {
        this.fileText = fileText
    }
}