package com.restapi.application.api.license

import com.restapi.application.license.LicenseHandler
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@RequestMapping(value = ["/getAllLicenses"], method = [RequestMethod.GET])
fun getAllLicenses(model: Model){
    LicenseHandler.commandGetAll()
}

@RequestMapping(value = ["deactivateLicense"], method = [RequestMethod.POST])
fun deactivateLicense(@RequestParam("id")id: String, model: Model){
    LicenseHandler.commandDeactivate(id)

}

@RequestMapping(value = ["activateLicense"], method = [RequestMethod.POST])
fun activateLicense(@RequestParam("id")id: String, model: Model){
    LicenseHandler.commandActivate(id = id)
}

@RequestMapping(value = ["lockLicense"], method = [RequestMethod.POST])
fun lockLicense(@RequestParam("id")id: String, model: Model){
    LicenseHandler.lockLicense(id = id)
}

@RequestMapping(value = ["unlockLicense"], method = [RequestMethod.POST])
fun unlockLicense(@RequestParam("id")id: String, model: Model){
    LicenseHandler.unlockLicense(id = id)
}

@RequestMapping(value = ["deleteLicense"], method = [RequestMethod.POST])
fun deleteLicense(@RequestParam("id")id: String, model: Model){
    LicenseHandler.deleteLicense(id = id)
}

@RequestMapping(value = ["/getActiveLicense"], method = [RequestMethod.GET])
fun getActiveLicense(model: Model){
    LicenseHandler.getActiveLicense()
}

/*
@RequestMapping(value = ["sendLanguage"], method = [RequestMethod.POST])
fun sendLanguage(@RequestParam("language")language: String, model: Model): String{
    DevController.errorHandler.setLanguage(language = language)
    val lang = DevController.errorHandler.getLanguage()
    LicenseHandler.sendLanguage(language = lang)
    "
}
*/

@RequestMapping(value = ["extendExpirationDate"], method = [RequestMethod.POST])
fun extendExpirationDate(@RequestParam("id")id: String,
                         @RequestParam("expirationDate")expirationDate: String,
                         model: Model){
    LicenseHandler.extendExpirationDate(id = id,expirationDate = expirationDate)
}

fun ClosedRange<Int>.random() = Random().nextInt((endInclusive + 1) - start) +  start

@RequestMapping(value = ["addLicense"], method = [RequestMethod.POST])
fun addLicense(@RequestParam("id") id: String,
               @RequestParam("type")type: String,
               @RequestParam("creationDate")creationDate: String,
               @RequestParam("expirationDate")expirationDate: String,
               model: Model ){

    LicenseHandler.commandAddLicense(id = id,type = type, createDate = creationDate,expirationDate = expirationDate)
}

@RequestMapping(value = ["LicenseImport"], method = [RequestMethod.POST])
fun importLicense(@RequestParam("filepath") filepath: String, model: Model) {
    LicenseHandler.importLicense(filepath = filepath)
}