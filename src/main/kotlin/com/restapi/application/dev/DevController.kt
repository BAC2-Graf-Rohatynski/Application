package com.restapi.application.dev

import com.restapi.application.xml.XmlTester
import com.restapi.application.command.CommandHandler
import com.restapi.application.ddf.Device
import com.restapi.application.database.DatabaseHandler
import com.restapi.application.error.ErrorHandler
import com.restapi.application.license.LicenseHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.lang.Thread.sleep
import java.util.*

@Controller
object DevController{
    private val logger: Logger = LoggerFactory.getLogger(DevController::class.java)
    private val errorHandler = ErrorHandler()
    private val dbHandler = DatabaseHandler()


    private fun setErrorModel(model: Model, message: String, response: String){
        model.addAttribute("errormessage",message)
        model.addAttribute("errorresponse",response)
    }

    private fun errorFunction(model: Model){
        setErrorModel(model = model, message = DevHelper.getMyErrorMessage(), response = DevHelper.getMyErrorResponse())
        waitABit()
    }


    private fun setLicenseModel(model: Model, message: String, response: String){
        model.addAttribute("licensemessage",message)
        model.addAttribute("licensemessage",response)
    }

    private fun licenseFunction(model: Model) {
        waitABit()
        setLicenseModel(model = model, message = DevHelper.getMyLicenseMessage(), response = DevHelper.getMyLicenseResponse())
    }

    private fun setCommandModel(model: Model, message: String, response: String){
        model.addAttribute("commandmessage",message)
        model.addAttribute("commandresponse",response)
    }

    private fun commandFunction(model: Model){
        waitABit()
        setCommandModel(model = model, message = DevHelper.getMyCommandMessage(), response = DevHelper.getMyCommandResponse())

    }

    private fun setSlaveCounter(model: Model, message:String){
        model.addAttribute("slavecount", message)
    }

    private fun slaveOutput(model: Model){
       val slaveList = DevHelper.getSlaveList()
        DevHelper.showSlaves()
        model.addAttribute("slaves", slaveList)
    }

    private fun waitABit(){
        sleep(500)
    }

    //Error Handler Functions
    @RequestMapping(value = ["ErrorMessage"], method = [RequestMethod.POST])
    fun sendError(@RequestParam("code") code: Int,model: Model): String {
        errorHandler.sendMessage(code = code, setError = true)
        errorFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["/stopCommandThread"], method = [RequestMethod.GET])
    fun stopCommandThread(): String {
        CommandHandler.stopConnection()
        errorHandler.stopConnection()
        LicenseHandler.stopConnection()
        return "/devpage"
    }

    @RequestMapping(value = ["/TestXML"], method = [RequestMethod.GET])
    fun testXML(): String{
        var device = Device()
        XmlTester.testXMLMaker()

        logger.info("XML written")
        return "devpage"
    }

    @RequestMapping(value = ["/TestJson"], method = [RequestMethod.GET])
    fun testJson(): String{
        var device = Device()
        //JsonTester.testJsonMaker()


        return "devpage"
    }


    @RequestMapping(value = ["/TestDB"], method = [RequestMethod.GET])
    fun testDBConnection(): String{
        dbHandler.connect()
        return "/devpage"
    }

    //CommandHandler send commands
    @RequestMapping(value = ["/CommandRead"], method = [RequestMethod.GET])
    fun sendRead(model: Model): String {
        CommandHandler.commandRead()
        waitABit()
        commandFunction(model)
        return "/devpage"
    }

    @RequestMapping(value = ["/CommandWrite"], method = [RequestMethod.GET])
    fun sendWrite(model: Model): String {
        CommandHandler.commandWrite()
        commandFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["CommandChangeShow"], method = [RequestMethod.POST])
    fun sendChangeShow(@RequestParam("show") show: String, model: Model): String {
        CommandHandler.commandChangeShow(show = show)
        commandFunction(model = model)
        return "/devpage"
    }


    @RequestMapping(value = ["/CommandNew"], method = [RequestMethod.GET])
    fun sendNew(model: Model): String {
        CommandHandler.commandNew()
        commandFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["/CommandGetAllSlaves"], method = [RequestMethod.GET])
    fun sendGetAllSlaves(model: Model): String {
        CommandHandler.commandGetAllSlaves()
        commandFunction(model = model)
        setSlaveCounter(model = model, message = DevHelper.getMySlaveCount().toString())
        slaveOutput(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["CommandChangeLanguage"], method = [RequestMethod.POST])
    fun sendChangeLanguage(@RequestParam("language") language: String, model: Model): String {
        errorHandler.setLanguage(language = language)
        CommandHandler.commandChangeLanguage(language = errorHandler.getLanguage())
        commandFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["CommandAddDatabase"], method = [RequestMethod.POST])
    fun sendAddDatabase(@RequestParam("database") name: String, model: Model): String {
        CommandHandler.commandAddDatabase(name = name)
        commandFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["CommandRemoveDatabase"], method = [RequestMethod.POST])
    fun sendRemoveDatabase(@RequestParam("database") name: String, model: Model): String {
        CommandHandler.commandRemoveDatabase(name = name)
        commandFunction(model = model)
        return "/devpage"
    }

    //License Handler
    @RequestMapping(value = ["/getAllLicenses"], method = [RequestMethod.GET])
    fun getAllLicenses(model: Model): String{
        LicenseHandler.commandGetAll()
        licenseFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["deactivateLicense"], method = [RequestMethod.POST])
    fun deactivateLicense(@RequestParam("id")id: String, model: Model): String{
        LicenseHandler.commandDeactivate(id)

        return "/devpage"
    }

    @RequestMapping(value = ["activateLicense"], method = [RequestMethod.POST])
    fun activateLicense(@RequestParam("id")id: String, model: Model): String{
        LicenseHandler.commandActivate(id = id)
        licenseFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["lockLicense"], method = [RequestMethod.POST])
    fun lockLicense(@RequestParam("id")id: String, model: Model): String{
        LicenseHandler.lockLicense(id = id)
        licenseFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["unlockLicense"], method = [RequestMethod.POST])
    fun unlockLicense(@RequestParam("id")id: String, model: Model): String{
        LicenseHandler.unlockLicense(id = id)
        licenseFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["deleteLicense"], method = [RequestMethod.POST])
    fun deleteLicense(@RequestParam("id")id: String, model: Model): String{
        LicenseHandler.deleteLicense(id = id)
        licenseFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["/getActiveLicense"], method = [RequestMethod.GET])
    fun getActiveLicense(model: Model): String{
        LicenseHandler.getActiveLicense()
        licenseFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["sendLanguage"], method = [RequestMethod.POST])
    fun sendLanguage(@RequestParam("language")language: String, model: Model): String{
        errorHandler.setLanguage(language = language)
        val lang = errorHandler.getLanguage()
        LicenseHandler.sendLanguage(language = lang)
        licenseFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(value = ["extendExpirationDate"], method = [RequestMethod.POST])
    fun extendExpirationDate(@RequestParam("id")id: String,
                             @RequestParam("expirationDate")expirationDate: String,
                             model: Model): String{
        LicenseHandler.extendExpirationDate(id = id,expirationDate = expirationDate)
        licenseFunction(model = model)
        return "/devpage"
    }

    fun ClosedRange<Int>.random() = Random().nextInt((endInclusive + 1) - start) +  start

    @RequestMapping(value = ["addLicense"], method = [RequestMethod.POST])
    fun addLicense(@RequestParam("id") id: String,
                   @RequestParam("type")type: String,
                   @RequestParam("creationDate")creationDate: String,
                   @RequestParam("expirationDate")expirationDate: String,
                   model: Model
    ): String{

        LicenseHandler.commandAddLicense(id = id,type = type, createDate = creationDate,expirationDate = expirationDate)
        licenseFunction(model)
        return "/devpage"
    }

    @RequestMapping(value = ["LicenseImport"], method = [RequestMethod.POST])
    fun importLicense(@RequestParam("filepath") filepath: String, model: Model): String {
        LicenseHandler.importLicense(filepath = filepath)
        errorFunction(model = model)
        return "/devpage"
    }

    @RequestMapping(path=["/dev"])
    fun getDevPage(model: Model): String {
        setErrorModel(model, "Error Messages","")
        setLicenseModel(model, "License Messages","")
        setCommandModel(model, "Command Messages","")
        return "devpage"
    }

}