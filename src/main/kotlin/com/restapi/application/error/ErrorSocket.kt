package com.restapi.application.error


import com.restapi.application.core.SocketPropertyHandler
import com.restapi.application.encryption.Encryption
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ErrorSocket{
    private var clientSocket: Socket? = null
    private var printWriter: PrintWriter? = null
    private var bufferedReader : BufferedReader? = null
    private val logger:Logger = LoggerFactory.getLogger(ErrorSocket::class.java)
    private var connected: Boolean = false

    fun connectToErrorHandler(address: String, port:Int){
        try{
            logger.info("Connecting to ErrorManager at $address on port $port.....")
            clientSocket = Socket(address,port)
            bufferedReader = BufferedReader(InputStreamReader(clientSocket!!.inputStream))
            printWriter = PrintWriter(clientSocket!!.getOutputStream(),true)
            logger.info("Connection to ErrorManager on $address:$port established....")
            setConnectionStatus(connected = true)
        } catch(ex:Exception) {
            logger.error("ErrorManager Connection failed ${ex.message}")
        }
    }

    fun sendMessage(message:String){
        if(printWriter  != null && connected){
            printWriter!!.println(message)
        }
    }

    fun receiveMessage(){
        val inputLine = bufferedReader!!.readLine()
        if(inputLine != null){
            try {
                //logger.info("Message $inputLine received")
                val decodedMessage = Encryption.decoder(base64Str = inputLine)
                logger.info(decodedMessage)
            } catch (ex: java.lang.Exception) {
                logger.error("Error occurred\n${ex.message}")
            }
        }else{
            logger.info("Error Message $inputLine received")
        }
    }

    fun getErrorPort(): Int = SocketPropertyHandler.getErrorPort()

    fun getMasterAddress(): String = SocketPropertyHandler.getMasterAddress()

    fun getConnectionStatus(): Boolean = connected

    fun setConnectionStatus(connected: Boolean){
        this.connected = connected
    }

    fun stopConnection(){
        try{
            if(printWriter != null && clientSocket != null){
                printWriter!!.close()
                clientSocket!!.close()
                setConnectionStatus(connected = false)
            }
        } catch(ex:Exception) {
            logger.error("ErrorManager Connection failed ${ex.message}")
        }
    }
}