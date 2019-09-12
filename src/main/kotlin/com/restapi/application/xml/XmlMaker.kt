package com.restapi.application.xml

import com.restapi.application.ddf.Device
import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.FileOutputStream
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter

class XmlMaker(val device: Device) {

    private val logger: Logger = LoggerFactory.getLogger(XmlMaker::class.java)

    init {
        val out = FileOutputStream("C:/temp/test.xml")
        val writer = IndentingXMLStreamWriter(XMLOutputFactory.newFactory().createXMLStreamWriter(out, "UTF-8"))
        serialize04(writer)
    }

    private fun serialize04(writer: IndentingXMLStreamWriter) {

        fun XMLStreamWriter.document(init: XMLStreamWriter.() -> Unit): XMLStreamWriter {
            this.writeStartDocument()
            this.init()
            this.writeEndDocument()
            return this
        }

        fun XMLStreamWriter.element(name: String, init: XMLStreamWriter.() -> Unit): XMLStreamWriter {
            this.writeStartElement(name)
            this.init()
            this.writeEndElement()
            return this
        }

        fun XMLStreamWriter.element(name: String, content: String) {
            element(name) {
                writeCharacters(content)
            }
        }

        fun XMLStreamWriter.attribute(name: String, value: String) = writeAttribute(name, value)


        writer.document {
            element("device") {
                attribute("xmlSample", "TestXML")
                element("meta") {
                    element("id", device.getId().toString())
                    element("version", device.getVersion().toString())
                    element("last-modification","")
                    element("device-class","")
                    element("type", device.getType().toString())
                    element("image-id","")
                    element("image","")
                    element("vendor", "")
                    element("model", device.getModel().toString())
                    element("mode","")
                    element("channels","")
                    element("power-consumption","")
                    element("author","")
                    element("comment","")
                    element("chceksum","")
                    }
                element("functions"){
                    element("channel_mod","abc123")
                    element("color"){
                        element("colorwheel"){
                            attribute("index","1")
                            attribute("dmxchannel","")
                        }
                    }
                }
            }
        }
        writer.flush()
        logger.info("XML written")
    }
}

