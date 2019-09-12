package com.restapi.application.xml


import com.restapi.application.dmxmodels.Model
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JsonMaker {

    private val logger: Logger = LoggerFactory.getLogger(JsonMaker::class.java)

    fun createJson(device: Model): String {

        val ddf = JSONArray()
        val jsonObject = JSONObject()
        //jsonObject.put(DDFProps.Device.name, device.getDeviceClass())
        //jsonObject.put(DDFProps.ID.name, device.getId())
        jsonObject.put(DDFProps.Version.name, device.getVersion())
        //jsonObject.put(DDFProps.LastModification.name, device.getLastModification())
        //jsonObject.put(DDFProps.Type.name, device.getType())
        //jsonObject.put(DDFProps.ImageId.name, device.getImageID())
        jsonObject.put(DDFProps.Image.name, " ")
        //jsonObject.put(DDFProps.Manufacturer.name, device.getVendor())
        jsonObject.put(DDFProps.Model.name, device.getModel())
        //jsonObject.put(DDFProps.Mode.name, device.getMode())
        //jsonObject.put(DDFProps.Channels.name, device.getChannels())
        jsonObject.put(DDFProps.PowerConsumption.name, device.getPowerConsumption())
        jsonObject.put(DDFProps.Author.name, device.getAuthor())
        jsonObject.put(DDFProps.Comment.name, device.getComment())
        //jsonObject.put(DDFProps.Checksum.name, device.getChecksum())
        /**if(device.getFunctions() != null || device.getFunctions() != emptyList<String>()){

        }*/
        ddf.put(jsonObject)
        //var ddfFile = saveFile(jsonString = ddf.toString())

        return ddf.toString()
    }


/*
    private fun saveFile(jsonString: String){
        val file = File()
        val output: Writer
        output = BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()
        logger.info("Json written")
    }

*/
}



