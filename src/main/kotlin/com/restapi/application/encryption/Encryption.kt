package com.restapi.application.encryption

import com.restapi.application.core.Synchronized
import sun.misc.BASE64Decoder


object Encryption{

    @Synchronized
    fun decoder(base64Str: String): String{
        val decodedStr = String(BASE64Decoder().decodeBuffer(base64Str))
        return decodedStr
    }

}