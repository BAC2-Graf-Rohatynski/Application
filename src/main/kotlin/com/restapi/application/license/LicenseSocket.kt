package com.restapi.application.license

import com.restapi.application.core.SocketPropertyHandler
import com.restapi.application.encryption.Encryption
import com.restapi.application.dev.DevHelper
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class LicenseSocket: Thread() {
    private var clientSocket: Socket? = null
    private var printWriter: PrintWriter? = null
    private var bufferedReader : BufferedReader? = null
    private val logger:Logger = LoggerFactory.getLogger(LicenseSocket::class.java)
    private var connected: Boolean = false

    override fun run(){
        try{
            val address: String = getMasterAddress()
            val port: Int = getLicensePort()
            connectToLicenseHandler(address = address, port = port)
            while(connected){
                receiveMessage()
            }
        }catch(ex: Exception){
            logger.error(ex.message)
        }finally{
            stopConnection()
        }
    }

    private fun connectToLicenseHandler(address: String, port:Int){
        try{
            logger.info("Connecting to LicenseManager at $address on port $port.....")
            clientSocket = Socket(address,port)
            bufferedReader = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
            printWriter = PrintWriter(clientSocket!!.getOutputStream(),true)
            logger.info("Connection to LicenseManager on $address:$port established....")
            connected = true
        } catch(ex:Exception) {
            logger.error("LicenseManager Connection failed ${ex.message}")
        }
    }


    fun sendMessage(message: String){
        if(printWriter  != null && connected){
            printWriter!!.println(message)
            logger.info("Message $message sent to license server")
            DevHelper.determineOutput(message = message, type = "licensemessage")
        }
    }


   private fun receiveMessage(){

       val inputLine = bufferedReader!!.readLine()
       if(inputLine != null){
           try {
               //logger.info("Message $inputLine received")
               val decodedMessage = Encryption.decoder(inputLine)
               logger.info(decodedMessage)
               DevHelper.determineOutput(message = decodedMessage,type = "licenseresponse")
           } catch (ex: java.lang.Exception) {
               logger.error("Error occurred\n${ex.message}")
           }
       }else{
           logger.info("Error Message $inputLine received")
       }
   }

    private fun getLicensePort(): Int = SocketPropertyHandler.getLicensePort()

    private fun getMasterAddress(): String = SocketPropertyHandler.getMasterAddress()

    fun stopConnection(){
        if(printWriter != null){
            printWriter!!.close()
        }
        if( clientSocket != null){
            clientSocket!!.close()
            connected = false
        }
    }
}