package com.restapi.application.xml

import com.restapi.application.ddf.Device


object XmlTester{

    fun testXMLMaker(){
        val device = Device()
       // device.setDeviceClass("abcde")
        //device.setID(1)
        device.setVersion(2)
        device.setType("Testtyp")
       // var date = LocalDate.parse("2019/02/01").toString()
        //device.setLastModification("yolo")
        XmlMaker(device = device)


    }
}