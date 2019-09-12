package com.restapi.application.command


import com.restapi.application.core.SocketPropertyHandler
import com.restapi.application.core.Synchronized
import com.restapi.application.dev.DevHelper
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class CommandSocket : Thread() {
    private var clientSocket: Socket? = null
    private var printWriter: PrintWriter? = null
    private var bufferedReader: BufferedReader? = null
    private val logger: Logger = LoggerFactory.getLogger(CommandSocket::class.java)
    private var connected: Boolean = false

    override fun run() {
        try {
            val address: String = getMasterAddress()
            val port: Int = getCommandPort()
            connectToCommandHandler(address = address, port = port)
            while (connected) {
                receiveMessage()
            }
        } catch (ex: Exception) {
            logger.error(ex.message)
        } finally {
            stopConnection()
        }
    }

    private fun connectToCommandHandler(address: String, port: Int) {
        try {
            logger.info("Connecting to CommandManager at $address on port $port.....")
            clientSocket = Socket(address, port)
            bufferedReader = BufferedReader(InputStreamReader(clientSocket!!.inputStream))
            printWriter = PrintWriter(clientSocket!!.getOutputStream(), true)
            logger.info("Connection to CommandManager on $address:$port established....")
            connected = true
        } catch (ex: Exception) {
            logger.error("CommandManager Connection failed ${ex.message}")
        }
    }

    @Synchronized
    fun sendMessage(message: JSONObject) {
        try {
            if (printWriter != null && connected) {
                sendMessage(message = message.toString())
            }
        } catch (ex: Exception) {
            logger.error("CommandManager Connection failed ${ex.message}")
        }
    }

    @Synchronized
    fun sendMessage(message: JSONArray) {
        try {
            if (printWriter != null && connected) {
                sendMessage(message = message.toString())
            }
        } catch (ex: Exception) {
            logger.error("CommandManager Connection failed ${ex.message}")
        }
    }

    @Synchronized
    private fun sendMessage(message: String) {
        try {
            if (printWriter != null && connected) {
                printWriter!!.println(message)
                logger.info("Message '$message' sent to command handler")
                DevHelper.determineOutput(message = "Message '$message' sent to command handler", type = "commandmessage")
            }
        } catch (ex: Exception) {
            logger.error("CommandManager Connection failed ${ex.message}")
        }
    }

    @Synchronized
    private fun receiveMessage() {
        val inputLine = bufferedReader!!.readLine()
        if (inputLine != null) {
            try {
                val message: String = inputLine
                val jsonObj = JSONTokener(message).nextValue()

                when (jsonObj) {
                    is JSONObject -> {
                        if (jsonObj.has("command".toLowerCase())) {
                            val command: String = jsonObj.getString("command").toLowerCase()
                            val success: Boolean
                            when (command) {
                                Commands.GetAllSlaves.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.ReadStage.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.WriteStage.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.New.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.ChangeShow.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.ChangeLanguage.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.NewMaster.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.NewDdfDatabase.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                                Commands.DeleteDdfDatabase.name.toLowerCase() -> {
                                    success = jsonObj.getBoolean("success")
                                    CommandHandler.checkSuccess(success = success, command = command)
                                }

                            }
                        } else if (jsonObj.has(Commands.SlavesCount.name)) {
                            if (jsonObj.getInt(Commands.SlavesCount.name) == null) {
                                val count = 0
                                CommandHandler.slaveCount(count)
                            } else {
                                CommandHandler.slaveCount(slaves = jsonObj.getInt(Commands.SlavesCount.name))
                            }

                        }
                    }

                    is JSONArray -> {
                        logger.info( "$jsonObj")
                        var jsonArr: JSONArray = jsonObj
                        CommandHandler.handleJsonArray(message = jsonArr)
                    }
                }
            } catch (ex: Exception) {
                logger.error(ex.message)
            }
        }
    }

    private fun getCommandPort(): Int = SocketPropertyHandler.getCommandPort()

    private fun getMasterAddress(): String = SocketPropertyHandler.getMasterAddress()

    fun stopConnection() {
        try {
            if (printWriter != null) {
                printWriter!!.close()
            }
            if (clientSocket != null) {
                clientSocket!!.close()
                connected = false
            }
        }catch(ex: Exception){
            logger.error(ex.message)
        }
    }
}