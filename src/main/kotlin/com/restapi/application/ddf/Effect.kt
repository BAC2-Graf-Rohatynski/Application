package com.restapi.application.ddf

import java.io.Serializable

class Effect : Serializable{
    var mainGroup: String? = null
    var subgroup: String? = null
    var mode: String? = null
    var dmxChannels: String? = null
    var type: String? = null
    var unit: String? = null
    var dmxVal: Int? = null
    var dmxMin: Int? = null
    var dmxMax: Int? = null
    var dmxReal: String? = null
    var realMin: Int? = null
    var realMax: Int? = null
    var speed: Int? = null
    var hlVal: Int? = null
    var hlValSpot: Int? = null
    var energyVal: Int? = null
    var fuelVal: Int? = null
    var color: String? = null
    var gobo: String? = null
}